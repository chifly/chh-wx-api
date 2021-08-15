package com.example.chh.wx.controller;

import com.example.chh.wx.common.util.R;
import com.example.chh.wx.controller.Form.TestSayHelloForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author apple
 */
@RestController
@RequestMapping("/test")
@Api("测试Web接口")
public class TestController {

    @PostMapping("/sayHello")
    @ApiOperation("测试方法")
    public R sayHello(@Valid @RequestBody TestSayHelloForm sayHelloForm) {
        return R.ok().put("message", "HelloWorld" + sayHelloForm.getName());
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    @RequiresPermissions(value = {"A","B"},logical = Logical.OR)
    public R addUser(){
        return R.ok("用户添加成功");
    }
}
