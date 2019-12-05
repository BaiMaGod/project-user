package com.service.user;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.mail.MailUtil;
import com.form.user.RegisterForm;
import com.mapper.user.UserMapper;
import com.model.user.User;
import com.model.user.UserExample;
import com.result.Result;
import com.result.ResultStatus;
import com.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    /**
     * 直接输入账号等信息注册，只需验证账号密码是否已存在，长度、字符是否合法
     * @param form
     * @return
     */
    @Override
    public Result direct(RegisterForm.directForm form) {
        // 首先检查用户注册信息的合法性，如有非法输入，返回错误信息
        if(!form.getErrorInfo().isEmpty()){
            return Result.fail(form.getErrorInfo(), ResultStatus.ERROR_Register_Number_Password);
        }

        // 如果通过字符检查，则检查账号是否存在
        UserExample example = new UserExample();
        example.createCriteria().andNumberEqualTo(form.getNumber());
        List<User> users = userMapper.selectByExample(example);
        // 如果查出数据库中已存在此账号，说明注册过，返回提示信息
        if(!users.isEmpty()){
            return Result.fail(0,ResultStatus.ERROR_Register_Number_Exist);
        }
        // 如果未注册过，则插入数据库
        User user = (User) ConvertUtil.convert(form, new User());
        user.setCreateTime(new Date());
        user.setPassword(SecureUtil.md5(form.getPassword()));   // md5加密 密码
        int num = userMapper.insertSelective(user);

        // 插入数据库成功，返回注册成功
        if(num>0){
            return Result.success(num);
        }

        return Result.fail(num,ResultStatus.ERROR_Register);
    }



    /**
     * 简单邮箱注册，只需验证账号是否已存在，是否符合邮件格式
     * @param form
     * @return
     */
    @Override
    public Result simpleEmail(RegisterForm.simpleEmailForm form) {
        // 首先检查用户注册信息的合法性，如有非法输入，返回错误信息
        if(!form.getErrorInfo().isEmpty()){
            return Result.fail(form.getErrorInfo(), ResultStatus.ERROR_Register_Number_Password);
        }

        // 如果通过字符检查，则检查账号是否存在
        UserExample example = new UserExample();
        example.createCriteria().andNumberEqualTo(form.getNumber());
        List<User> users = userMapper.selectByExample(example);
        // 如果查出数据库中已存在此账号，说明注册过，返回提示信息
        if(!users.isEmpty()){
            return Result.fail(0,ResultStatus.ERROR_Register_Number_Exist);
        }
        // 如果未注册过，则插入数据库
        User user = (User) ConvertUtil.convert(form, new User());
        user.setCreateTime(new Date());
        user.setEmail(form.getNumber());
        user.setPassword(SecureUtil.md5(form.getPassword()));   // md5加密 密码

        int num = userMapper.insertSelective(user);

        // 插入数据库成功，返回注册成功
        if(num>0){
            return Result.success(num);
        }

        return Result.fail(num,ResultStatus.ERROR_Register);
    }


    /**
     * 高级邮箱注册，验证账号是否已存在，是否符合邮件格式，以及发送邮箱验证码
     * @param form
     * @return
     */
    @Override
    public Result seniorEmail(RegisterForm.seniorEmailForm form,HttpSession session) {
        String numberCodeKey = form.getNumber() + "+" + form.getCode();
        // 获取验证码的截止时间
        LocalDateTime endTime = (LocalDateTime) session.getAttribute(numberCodeKey);
        if(endTime==null){
            return Result.fail(0,ResultStatus.ERROR_V_Code);
        }
        // 移除存储的验证码
        session.removeAttribute(numberCodeKey);
//        System.out.println("截止时间:"+endTime.getHour()+":"+endTime.getMinute()+":"+endTime.getSecond());
        LocalDateTime time = LocalDateTime.now();
//        System.out.println("当前时间:"+time.getHour()+":"+time.getMinute()+":"+time.getSecond());
        // 如果当前时间在截止时间之前，则注册
        if(time.isBefore(endTime)){
            // 首先检查用户注册信息的合法性，如有非法输入，返回错误信息
            if(!form.getErrorInfo().isEmpty()){
                return Result.fail(form.getErrorInfo(), ResultStatus.ERROR_Register_Number_Password);
            }

            // 如果通过字符检查，则检查账号是否存在
            UserExample example = new UserExample();
            example.createCriteria().andNumberEqualTo(form.getNumber());
            List<User> users = userMapper.selectByExample(example);
            // 如果查出数据库中已存在此账号，说明注册过，返回提示信息
            if(!users.isEmpty()){
                return Result.fail(0,ResultStatus.ERROR_Register_Number_Exist);
            }
            // 如果未注册过，则插入数据库
            User user = (User) ConvertUtil.convert(form, new User());
            user.setCreateTime(new Date());
            user.setEmail(form.getNumber());
            if(form.getPassword()!=null){
                user.setPassword(SecureUtil.md5(form.getPassword()));   // md5加密 密码
            }

            int num = userMapper.insertSelective(user);

            // 插入数据库成功，返回注册成功
            if(num>0){
                return Result.success(num);
            }

            return Result.fail(num,ResultStatus.ERROR_Register);

        }

        return Result.fail(0,ResultStatus.ERROR_V_Code_OutTime);
    }

    /**
     * 获取邮箱验证码
     * @param form
     * @return
     */
    @Override
    public Result getEmailCode(RegisterForm.getEmailCode form, HttpSession session) {
        // 首先检查用户注册信息的合法性，如有非法输入，返回错误信息
        if(!form.getErrorInfo().isEmpty()){
            return Result.fail(form.getErrorInfo(), ResultStatus.ERROR_Email);
        }

        // 生成验证码
        int vCode = RandomUtil.randomInt(1000, 10000);

        // 发送验证码
        MailUtil.send(form.getEmail(), "验证码", vCode+"", false);

        LocalDateTime time = LocalDateTime.now().plusSeconds(180);// 设置超时时间为 180 秒

        // 存储验证码，以及验证码的截止时间
        session.setAttribute(form.getEmail()+"+"+vCode,time);

        return Result.success(180);
    }

    /**
     * QQ 授权注册
     * @param form
     * @return
     */
    @Override
    public Result qq(RegisterForm.qqForm form) {
        return null;
    }


    /**
     * 微信 授权注册
     * @param form
     * @return
     */
    @Override
    public Result weiXin(RegisterForm.weiXinForm form) {
        return null;
    }


}
