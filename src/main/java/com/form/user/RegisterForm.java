package com.form.user;


import com.model.user.User;
import com.utils.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册相关的form对象，用于接收前端参数
 */
public class RegisterForm {

    @Data
    public static class directForm {
        @ApiModelProperty(value = "账号",required=true,example = "123456")
        private String number;  // 账号
        @ApiModelProperty(value = "密码",required=true,example = "123456")
        private String password;  // 密码


        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(!CheckUtil.checkNumber(number)){
                errorInfos.put("number","账号长度必须在6-16之间，且不能包含非法字符*，&，#，@");
            }
            if(!CheckUtil.checkPassword(password)){
                errorInfos.put("password","密码长度必须在6-16之间，且必须包含数字和英文");
            }

            return errorInfos;
        }
    }

    @Data
    public static class simpleEmailForm {
        @ApiModelProperty(value = "邮箱账号",required=true,example = "123456@qq.com")
        private String number;  // 邮箱账号
        @ApiModelProperty(value = "密码",required=true,example = "123456")
        private String password;  // 密码


        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(!CheckUtil.isEmail(number)){
                errorInfos.put("number","邮箱格式不正确");
            }
            if(!CheckUtil.checkPassword(password)){
                errorInfos.put("password","密码长度必须在6-16之间，且必须包含数字和英文");
            }

            return errorInfos;
        }
    }

    @Data
    public static class seniorEmailForm {
        @ApiModelProperty(value = "邮箱账号",required=true,example = "123456@qq.com")
        private String number;  // 邮箱账号
        @ApiModelProperty(value = "密码",example = "123456")
        private String password;  // 密码
        @ApiModelProperty(value = "验证码",required=true,example = "1024")
        private String code;  // 验证码


        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(!CheckUtil.isEmail(number)){
                errorInfos.put("email","邮箱格式不正确");
            }
            if(password!=null && !CheckUtil.checkPassword(password)){
                errorInfos.put("password","密码长度必须在6-16之间，且必须包含数字和英文");
            }


            return errorInfos;
        }
    }

    @Data
    public static class getEmailCode {
        @ApiModelProperty(value = "邮箱账号",required=true,example = "123456@qq.com")
        private String email;  // 邮箱账号

        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(!CheckUtil.isEmail(email)){
                errorInfos.put("email","邮箱格式不正确");
            }

            return errorInfos;
        }
    }

    @Data
    public static class qqForm extends User {

    }

    @Data
    public static class weiXinForm extends User {

    }

}
