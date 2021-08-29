package com.example.chh.wx.controller;

import cn.hutool.json.JSONUtil;
import com.example.chh.wx.common.util.R;
import com.example.chh.wx.config.shiro.JwtUtil;
import com.example.chh.wx.config.tencent.TLSSigAPIv2;
import com.example.chh.wx.controller.Form.*;
import com.example.chh.wx.exception.EmosException;
import com.example.chh.wx.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${emos.jwt.cache-expire}")
    private int cacheExpire;

    @Value("${trtc.appid}")
    private Integer appId;

    @Value("${trtc.key}")
    private String key;

    @Value("${trtc.expire}")
    private Integer expire;

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public R register(@Valid @RequestBody RegisterForm form){
        int id = userService.registerUser(form.getRegisterCode(), form.getCode(), form.getNickname(), form.getPhoto());
        String token = jwtUtil.createToken(id);
        Set<String> permsSet = userService.searchUserPermissions(id);
        saveCacheToken(token, id);
        return R.ok("注册成功").put("token", token).put("permission", permsSet);
    }
    @PostMapping("/login")
    @ApiOperation("登陆系统")
    public R login(@Valid @RequestBody LoginForm form) {
        int id = userService.login(form.getCode());
        String token = jwtUtil.createToken(id);
        saveCacheToken(token, id);
        Set<String> permsSet = userService.searchUserPermissions(id);
        return R.ok("登陆成功").put("token", token).put("permission", permsSet);
    }
    private void saveCacheToken(String token, int userId) {
        redisTemplate.opsForValue().set(token, userId + "", cacheExpire, TimeUnit.DAYS);
    }


    @GetMapping("/searchUserSummary")
    @ApiOperation("查询用户摘要")
    public R searchUserSummary(@RequestHeader("token") String token){
        int userId = jwtUtil.getUserId(token);
        HashMap map = userService.searchUserSummary(userId);
        return R.ok().put("result", map);
    }

    @PostMapping("/searchUserGroupByDept")
    @ApiOperation("查询员工列表，按照部门分组排列")
    @RequiresPermissions(value = {"ROOT","EMPLOYEE:SELECT"},logical = Logical.OR) //要求用户满足某些权限，Root OR SELECT
    public R searchUserGroupByDept(@Valid @RequestBody SearchUserGroupByDeptForm form){
        ArrayList<HashMap> list=userService.searchUserGroupByDept(form.getKeyword());
        return R.ok().put("result",list);
    }

    @PostMapping("/searchMembers")
    @ApiOperation("查询成员")
    @RequiresPermissions(value = {"ROOT", "MEETING:INSERT", "MEETING:UPDATE"},logical = Logical.OR) //拥有三个权限的其中一个
    public R searchMembers(@Valid @RequestBody SearchMembersForm form){
        if(!JSONUtil.isJsonArray(form.getMembers())){
            throw new EmosException("members不是JSON数组");
        }
        List param=JSONUtil.parseArray(form.getMembers()).toList(Integer.class);
        ArrayList list=userService.searchMembers(param);
        return R.ok().put("result",list);
    }

    @PostMapping("/selectUserPhotoAndName")
    @ApiOperation("查询用户姓名和头像")
    @RequiresPermissions(value = {"WORKFLOW:APPROVAL"})
    public R selectUserPhotoAndName(@Valid @RequestBody SelectUserPhotoAndNameForm form){
        //验证这个数组是否是json
        if(!JSONUtil.isJsonArray(form.getIds())){
            throw new EmosException("参数不是JSON数组");
        }
        //把JSON数组转化为list
        List<Integer> param=JSONUtil.parseArray(form.getIds()).toList(Integer.class);
        //调用一下业务层的方法
        List<HashMap> list=userService.selectUserPhotoAndName(param);
        //返回数据
        return R.ok().put("result",list);
    }

    @GetMapping("/genUserSig")
    @ApiOperation("生成用户签名")
    public R genUserSig(@RequestHeader("token") String token){
        int id=jwtUtil.getUserId(token);
        //得到用户email
        String email=userService.searchMemberEmail(id);
        //得到一个腾讯的 传入appID 和key
        TLSSigAPIv2 api=new TLSSigAPIv2(appId, key);
        //通过api获得用户签名
        String userSig=api.genUserSig(email, expire);
        //把签名和email放入返回对象
        return R.ok().put("userSig",userSig).put("email",email);
    }

}
