package com.example.chh.wx.service;

import com.example.chh.wx.db.pojo.TbUser;

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

    public TbUser searchById(int userId);

    public String searchUserHiredate(int userId);
}
