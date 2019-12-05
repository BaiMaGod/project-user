package com.controller.user;

import com.form.user.LoginForm;
import com.result.Result;
import com.result.ResultStatus;
import com.service.user.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@Api(value = "登录操作 接口控制器",tags = {"登录操作 接口"})
@RestController
@RequestMapping("json/login")
public class LoginController {
    @Autowired
    LoginService loginService;


    /**
     * 直接输入账号密码登录，只需验证账号密码是否正确
     * @param form
     * @return
     */
    @ApiOperation(value = "直接输入账号密码登录")
    @GetMapping("/direct")
    public Result direct(LoginForm.directForm form, HttpSession session){
        Result result = loginService.direct(form);
        if(result.getCode() == ResultStatus.SUCCESS.getCode()){
            session.setAttribute("loginUser",result.getData());
        }

        return result;
    }

    /**
     * 邮箱验证码登录
     * @param form
     * @return
     */
    @ApiOperation(value = "邮箱验证码登录")
    @GetMapping("/email")
    public Result email(LoginForm.emailForm form, HttpSession session){
        Result result = loginService.email(form,session);
        if(result.getCode() == ResultStatus.SUCCESS.getCode()){
            session.setAttribute("loginUser",result.getData());
        }

        return result;
    }

    /**
     * QQ 授权登录
     * @param form
     * @return
     */
    @ApiOperation(value = "QQ 授权登录",hidden = true)
    @GetMapping("/qq")
    public Result qq(LoginForm.qqForm form, HttpSession session){
        Result result = loginService.qq(form);
        if(result.getCode() == ResultStatus.SUCCESS.getCode()){
            session.setAttribute("loginUser",result.getData());
        }

        return result;
    }

    /**
     * 微信 授权注册
     * @param form
     * @return
     */
    @ApiOperation(value = "微信 授权注册",hidden = true)
    @GetMapping("/weiXin")
    public Result weiXin(LoginForm.weiXinForm form, HttpSession session){
        Result result = loginService.weiXin(form);
        if(result.getCode() == ResultStatus.SUCCESS.getCode()){
            session.setAttribute("loginUser",result.getData());
        }

        return result;
    }


    /**
     * 退出登录
     * @return
     */
    @ApiOperation(value = "退出登录")
    @GetMapping("/out")
    public Result out(HttpSession session){
        session.removeAttribute("loginUser");

        return Result.success(1);
    }
}
