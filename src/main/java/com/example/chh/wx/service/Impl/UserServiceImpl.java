package com.example.chh.wx.service.Impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.chh.wx.db.dao.TbDeptDao;
import com.example.chh.wx.db.dao.TbUserDao;
import com.example.chh.wx.db.pojo.MessageEntity;
import com.example.chh.wx.db.pojo.TbUser;
import com.example.chh.wx.exception.EmosException;
import com.example.chh.wx.service.UserService;
import com.example.chh.wx.task.MessageTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * @author apple
 */
@Service
@Slf4j
@Scope("prototype")
public class UserServiceImpl implements UserService {
    @Value("${wx.app-id}")
    private String appId;

    @Value("${wx.app-secret}")
    private String appSecret;

    @Autowired
    private TbUserDao userDao;

    @Autowired
    private MessageTask messageTask;

    @Autowired
    private TbDeptDao deptDao;
    /**
     * 获得临时用户凭证
     * @param code
     * @return
     */
    private String getOpenId(String code) {
        String url="https://api.weixin.qq.com/sns/jscode2session";
        HashMap map=new HashMap();
        map.put("appid", appId);
        map.put("secret", appSecret);
        map.put("js_code", code);
        map.put("grant_type", "authorization_code");
        String response=HttpUtil.post(url,map);
        JSONObject json=JSONUtil.parseObj(response);
        String openId=json.getStr("openid");
        if(openId==null||openId.length()==0){
            throw new RuntimeException("临时登陆凭证错误");
        }
        return openId;
    }

    @Override
    public int registerUser(String registerCode, String code, String nickname, String photo) {
        String isRoot = "000000";
        if (isRoot.equals(registerCode)) {
            boolean b = userDao.haveRootUser();
            if (!b) {
                String openId = getOpenId(code);
                HashMap param = new HashMap();
                param.put("openId", openId);
                param.put("nickname", nickname);
                param.put("photo", photo);
                param.put("role", "[0]");
                param.put("status", 1);
                param.put("createTime", new Date());
                param.put("root", true);
                userDao.insert(param);
                int id = userDao.searchIdByOpenId(openId);

                MessageEntity entity = new MessageEntity();
                entity.setSenderId(0);
                entity.setSenderName("系统消息");
                entity.setUuid(IdUtil.simpleUUID());
                entity.setMsg("你已经成功注册，请更新信息");
                entity.setSendTime(new Date());
                messageTask.sendAsync(id + "", entity);
                return id;
            } else {
                throw new EmosException("无法绑定管理账号");
            }
        } else {

        }
        return 0;
    }

    @Override
    public Set<String> searchUserPermissions(int userId) {
        Set<String> permissions = userDao.searchUserPermissions(userId);
        return permissions;
    }

    @Override
    public Integer login(String code) {
        String openId = getOpenId(code) ;
        Integer id = userDao.searchIdByOpenId(openId);
        if (id == null) {
            throw new EmosException("用户不存在");
        }
        //TODO 从消息队列中接收数据，转移到消息表
        messageTask.receiveAsync(id + "");
        return id;
    }

    @Override
    public TbUser searchById(int userId) {
        TbUser tbUser = userDao.searchById(userId);
        return tbUser;
    }

    @Override
    public String searchUserHiredate(int userId) {
        String hiredate = userDao.searchUserHiredate(userId);
        return hiredate;
    }

    @Override
    public HashMap searchUserSummary(int userId) {
        HashMap map = userDao.searchUserSummary(userId);
        return map;
    }

    @Override
    public ArrayList<HashMap> searchUserGroupByDept(String keyword) {
        // list1保存部门数据， list2保存用户数据
        ArrayList<HashMap> list_1=deptDao.searchDeptMembers(keyword);
        ArrayList<HashMap> list_2=userDao.searchUserGroupByDept(keyword);
        //把两个合并
        for(HashMap map_1:list_1){
            //得到部门ID
            long deptId=(Long)map_1.get("id");
            //部门里面的员工记录
            ArrayList members=new ArrayList();
            for(HashMap map_2:list_2){
                //遍历每一个员工记录
                long id=(Long) map_2.get("deptId");
                //判断员工ID和部门id是否相同
                if(deptId==id){
                    members.add(map_2);
                }
            }
            map_1.put("members",members);
        }
        return list_1;
    }

    @Override
    public ArrayList<HashMap> searchMembers(List param) {
        ArrayList<HashMap> results = userDao.searchMembers(param);
        return results;
    }

    @Override
    public List<HashMap> selectUserPhotoAndName(List param) {
        List<HashMap> results = userDao.selectUserPhotoAndName(param);
        return results;
    }

    @Override
    public String searchMemberEmail(int id) {
        String result = userDao.searchMemberEmail(id);
        return result;
    }
}
