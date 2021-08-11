package com.example.chh.wx.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 实现认证与授权
 * @author apple
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {



    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 判断token是否为OAuth2Token
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权 验证权限时使用
     * @param principalCollection
     * @return info
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        //TODO 查询用户的权限列表
        //TODo 把权限列表添加到info里面
        return null;
    }

    /**
     * 认证 登陆时候调用
     * @param authenticationToken 令牌对象
     * @return info
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //TODO 从令牌中获得userId，检查用户是否冻结
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        //TODO 往info中添加用户信息，Token字符串
        return info;
    }
}
