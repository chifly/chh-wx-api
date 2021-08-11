package com.example.chh.wx.controller.Form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author apple
 * 后端验证测试
 */
@ApiModel
@Data
public class TestSayHelloForm {
//    @NotBlank
//    @Pattern(regexp = "^[\\u4e00-\\u9fa5]{2,15}$")
    @ApiModelProperty("姓名")
    private String name;

}
