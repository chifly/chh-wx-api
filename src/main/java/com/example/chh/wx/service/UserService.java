package com.example.chh.wx.service;

import java.util.Set;

/**
 *
 * @author apple
 */
public interface UserService {
    public int registerUser(String registerCode, String code, String nickname, String photo);

    public Set<String> searchUserPermissions(int userId);

    /**
     * 登陆
     * @param code
     * @return
     */
    public Integer login(String code);
}
