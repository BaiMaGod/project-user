package com.form.user;

import com.utils.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class LoginForm {


    @Data
    public static class directForm {
        @ApiModelProperty(value = "账号",required=true,example = "123456")
        private String number;  // 账号
        @ApiModelProperty(value = "密码",required=true,example = "123456")
        private String password;  // 密码



        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(!CheckUtil.checkNumber(number)){
                errorInfos.put("number","账号长度必须在6-16之间，且不能包含非法字符*，&，#,/");
            }
            if(!CheckUtil.checkPassword(password)){
                errorInfos.put("password","密码错误");
            }

            return errorInfos;
        }
    }

    @Data
    public static class emailForm {
        @ApiModelProperty(value = "账号",required=true,example = "123456")
        private String number;  // 账号
        @ApiModelProperty(value = "验证码",required=true,example = "1024")
        private String code;  // 验证码

        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(!CheckUtil.isEmail(number)){
                errorInfos.put("email","邮箱格式不正确");
            }

            return errorInfos;
        }
    }

    @Data
    public static class qqForm {
        @ApiModelProperty(value = "账号",required=true,example = "123456")
        private String number;  // 账号
    }


    @Data
    public static class weiXinForm {

    }
}
