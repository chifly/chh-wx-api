package com.example.chh.wx.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 封装Token对象
 * @author apple
 */
public class OAuth2Token implements AuthenticationToken {
    public String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
