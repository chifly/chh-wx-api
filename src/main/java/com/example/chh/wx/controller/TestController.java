package com.example.chh.wx.controller;

import com.example.chh.wx.common.util.R;
import com.example.chh.wx.controller.Form.TestSayHelloForm;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author apple
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/sayHello")
    @ApiOperation("测试方法")
    public R sayHello(@Valid @RequestBody TestSayHelloForm sayHelloForm) {
        return R.ok().put("message", "HelloWorld" + sayHelloForm.getName());
    }
}
