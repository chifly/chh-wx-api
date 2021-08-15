package com.example.chh.wx.config.shiro;

import com.example.chh.wx.db.pojo.TbUser;
import com.example.chh.wx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 实现认证与授权
 * @author apple
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {



    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

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
     * @param collection
     * @return info
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection collection) {
        TbUser user = (TbUser) collection.getPrimaryPrincipal();
        int userId = user.getId();
        //查询用户权限列表
        Set<String> permsSet = userService.searchUserPermissions(userId);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证 验证登陆时候调用
     * @param token 令牌对象
     * @return info
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        int userId = jwtUtil.getUserId(accessToken);
        TbUser user = userService.searchById(userId);
        //员工已经离职
        if (user == null) {
            throw new LockedAccountException("账号已经锁定，请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());

        return info;
    }
}
