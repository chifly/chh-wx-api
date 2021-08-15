package com.example.chh.wx.db.dao;

import com.example.chh.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.Set;

@Mapper
public interface TbUserDao {

    /**
     * 判断管理员是否已经存在
     * @return 是或者否
     */
    public boolean haveRootUser();

    /**
     * 插入用户数据
     * @param param
     * @return 插入成功条数
     */

    public int insert(HashMap param);

    /**
     * 查询用户id
     * @param openId
     * @return 返回一个用户id
     */
    public Integer searchIdByOpenId(String openId);

    /**
     * 用户权限查询
     * @param userId
     * @return
     */
    public Set<String> searchUserPermissions(int userId);

    /**
     * 查询用户表，返回一个用户
     * @param userId
     * @return
     */
    public TbUser searchById(int userId);
}