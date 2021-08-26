package com.example.chh.wx.db.dao;

import com.example.chh.wx.db.pojo.TbUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.crypto.hash.Hash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 查找名字和等级
     * @param userId
     * @return
     */
    public HashMap searchNameAndDept(int userId);

    /**
     * 根据用户id查询入职日期
     * @param userId
     * @return
     */
    public String searchUserHiredate(int userId);

    /**
     * 查询我的界面中的用户信息，部门
     * @param userId
     * @return
     */
    public HashMap searchUserSummary(int userId);

    /**
     * 查询员工在哪个部门
     * @param keyword
     * @return
     */
    public ArrayList<HashMap> searchUserGroupByDept(String keyword);

    /**
     * 查找成员
     * @param param
     * @return
     */
    public ArrayList<HashMap> searchMembers(List param);

    public HashMap searchUserInfo(int userId);

    /**
     * 查找部门id
     * @param id
     * @return
     */
    public int searchDeptManagerId(int id);

    /**
     * 总经理id
     * @return
     */
    public int searchGmId();

    /**
     * 查询名字和号码
     * @param param
     * @return
     */
    public List<HashMap> selectUserPhotoAndName(List param);
}