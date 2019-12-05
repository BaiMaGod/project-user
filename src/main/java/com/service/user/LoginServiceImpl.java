package com.service.user;

import cn.hutool.crypto.SecureUtil;
import com.form.user.LoginForm;
import com.mapper.user.UserMapper;
import com.model.user.User;
import com.model.user.UserExample;
import com.model.user.UserExt;
import com.result.Result;
import com.result.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    /**
     * 直接输入账号密码登录，只需验证账号密码是否正确
     * @param form
     * @return
     */
    @Override
    public Result direct(LoginForm.directForm form) {
        // 首先检查用户注册信息的合法性，如有非法输入，返回错误信息
        if(!form.getErrorInfo().isEmpty()){
            return Result.fail(form.getErrorInfo(), ResultStatus.ERROR_Login_Direct);
        }

        // 如果通过字符检查，则查找指定账号和密码的用户
        UserExample example = new UserExample();
        example.createCriteria().andNumberEqualTo(form.getNumber()).andPasswordEqualTo(SecureUtil.md5(form.getPassword()));
        List<User> users = userMapper.selectByExample(example);
        // 如果查不出任何用户，则说明账号或密码错误
        if(users.isEmpty()){
            return Result.fail(0,ResultStatus.ERROR_Login_Number_Password);
        }

        // 查询用户全部信息
        UserExt loginUser = userService.getUserById(users.get(0).getUserId());

        return Result.success(loginUser);
    }

    /**
     * 邮箱验证码登录
     * @param form
     * @return
     */
    @Override
    public Result email(LoginForm.emailForm form, HttpSession session) {
        String numberCodeKey = form.getNumber() + "+" + form.getCode();
        LocalDateTime endTime = (LocalDateTime) session.getAttribute(numberCodeKey);
        if(endTime==null){
            return Result.fail(0,ResultStatus.ERROR_V_Code);
        }
        // 移除存储的验证码
        session.removeAttribute(numberCodeKey);

        LocalDateTime time = LocalDateTime.now();
        // 如果当前时间在截止时间之前，则登录
        if(time.isBefore(endTime)){
            // 首先检查用户登录信息的合法性，如有非法输入，返回错误信息
            if(!form.getErrorInfo().isEmpty()){
                return Result.fail(form.getErrorInfo(), ResultStatus.ERROR_Email);
            }

            // 如果通过字符检查，则查找指定账号用户
            UserExample example = new UserExample();
            example.createCriteria().andNumberEqualTo(form.getNumber());
            List<User> users = userMapper.selectByExample(example);
            // 如果查不出任何用户，则说明账号不存在
            if(users.isEmpty()){
                return Result.fail(0,ResultStatus.ERROR_Login_Number_No_Exist);
            }

            // 查询用户全部信息
            UserExt loginUser = userService.getUserById(users.get(0).getUserId());

            return Result.success(loginUser);
        }

        return Result.fail(0,ResultStatus.ERROR_V_Code_OutTime);
    }

    /**
     * QQ 授权登录
     * @param form
     * @return
     */
    @Override
    public Result qq(LoginForm.qqForm form) {
        return null;
    }

    /**
     * 微信 授权注册
     * @param form
     * @return
     */
    @Override
    public Result weiXin(LoginForm.weiXinForm form) {
        return null;
    }
}
