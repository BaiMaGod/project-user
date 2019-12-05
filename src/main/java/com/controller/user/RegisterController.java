package com.controller.user;

import com.form.user.RegisterForm;
import com.result.Result;
import com.service.user.RegisterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@Api(value = "注册操作 接口控制器",tags = {"注册操作 接口"})
@RestController
@RequestMapping("json/register")
public class RegisterController {
    @Autowired
    RegisterService registerService;

    /**
     * 直接输入账号密码注册，只需验证账号是否已存在，长度、字符是否合法
     * @param form
     * @return
     */
    @ApiOperation(value = "直接输入账号密码注册",notes = "只需验证账号是否已存在，长度、字符是否合法")
    @PostMapping("/direct")
    public Result direct(RegisterForm.directForm form){

        return registerService.direct(form);
    }

    /**
     * 简单邮箱注册，只需验证账号是否已存在，是否符合邮件格式
     * @param form
     * @return
     */
    @ApiOperation(value = "简单邮箱注册",notes = "只需验证账号是否已存在，是否符合邮件格式")
    @PostMapping("/simpleEmail")
    public Result simpleEmail(RegisterForm.simpleEmailForm form){

        return registerService.simpleEmail(form);
    }

    /**
     * 高级邮箱注册，验证账号是否已存在，是否符合邮件格式，以及发送邮箱验证码
     * @param form
     * @return
     */
    @ApiOperation(value = "高级邮箱注册",notes = "验证账号是否已存在，是否符合邮件格式，以及发送邮箱验证码")
    @PostMapping("/seniorEmail")
    public Result seniorEmail(RegisterForm.seniorEmailForm form,HttpSession session){

        return registerService.seniorEmail(form,session);
    }

    /**
     * 获取邮箱验证码
     * @param form
     * @return
     */
    @ApiOperation(value = "获取邮箱验证码",notes = "获取验证码")
    @PostMapping("/getEmailCode")
    public Result getEmailCode(RegisterForm.getEmailCode form, HttpSession session){

        return registerService.getEmailCode(form,session);
    }


    /**
     * QQ 授权注册
     * @param form
     * @return
     */
    @ApiOperation(value = "QQ 授权注册",hidden = true)
    @PostMapping("/qq")
    public Result qq(RegisterForm.qqForm form){

        return registerService.qq(form);
    }

    /**
     * 微信 授权注册
     * @param form
     * @return
     */
    @ApiOperation(value = "微信 授权注册",hidden = true)
    @PostMapping("/weiXin")
    public Result weiXin(RegisterForm.weiXinForm form){

        return registerService.weiXin(form);
    }
}
