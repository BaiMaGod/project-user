package com.form.user;

import com.form.PageForm;
import com.utils.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserForm {


    @Data
    public static class searchForm extends PageForm{
        @ApiModelProperty(value = "搜索词",required = true,example = "张三")
        private String searchStr;

    }


    @Data
    public static class getUserByIdForm {
        @ApiModelProperty(value = "用户ID",required = true,example = "123456789")
        private int userId;
    }


    @Data
    public static class updateForm {
        @ApiModelProperty(value = "用户ID",required = true,example = "123456789")
        private Integer userId;
        @ApiModelProperty(value = "昵称")
        private String nickName;
        @ApiModelProperty(value = "手机号码")
        private String telPhone;
        @ApiModelProperty(value = "邮箱号")
        private String email;
        @ApiModelProperty(value = "QQ号")
        private String qq;
        @ApiModelProperty(value = "微信号")
        private String weiXin;
        @ApiModelProperty(value = "性别：男 / 女")
        private String sex;
        @ApiModelProperty(value = "真实名字")
        private String readName;
        @ApiModelProperty(value = "头像url")
        private String headImg;
        @ApiModelProperty(value = "生日，yyyy-MM-dd")
        private String birthday;
        @ApiModelProperty(value = "个人简介")
        private String introduce;

    }

    @Data
    public static class updatePasswordForm {
        @ApiModelProperty(value = "用户ID",hidden = true)
        private Integer userId;
        @ApiModelProperty(value = "原密码",required = true)
        private String oldPassword;
        @ApiModelProperty(value = "新密码",required = true)
        private String newPassword;


        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(StringUtils.isEmpty(oldPassword)) {
                errorInfos.put("oldPassword", "原密码,不能为空");
            }
            if(!CheckUtil.checkPassword(newPassword)){
                errorInfos.put("newPassword","密码长度必须在6-16之间，且必须包含数字和英文");
            }

            return errorInfos;
        }
    }


}
