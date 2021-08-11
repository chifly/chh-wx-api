package com.example.chh.wx.config.shiro;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Value("${emos.jwt.secret}")
    private String secret;

    @Value("${emos.jwt.expire}")
    private String expire;



    /**
     * 创建一个令牌
     * 首先计算过期日期，然后把密钥封装成一个加密算法，然后创建一个内部类对象，通过内部类来绑定token的消息
     * @param userId 用户id
     * @return token
     */
    public String createToken(int userId) {
        Date date = DateUtil.offset(new Date(), DateField.DAY_OF_YEAR, 5);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create();
        String token = builder.withClaim("userId", userId).withExpiresAt(date).sign(algorithm);
        return token;
    }

    /**
     * 通过token解码获得用户id
     * @param token 用户令牌
     * @return userid
     */
    public int getUserId(String token) {
        DecodedJWT jwt = JWT.decode(token);
        int userId = jwt.getClaim("userId").asInt();
        return userId;
    }

    /**
     * 验证这个token是否有效
     * @param token 一个用户令牌
     */
    public void verifierToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        verifier.verify(token); //可以抛出RuntimeException
    }
}
