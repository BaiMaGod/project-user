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

public class UserManageForm {


    @Data
    public static class listForm extends PageForm {
        @ApiModelProperty(value = "用户ID")
        private Integer userId;
        @ApiModelProperty(value = "账号")
        private String number;
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
        @ApiModelProperty(value = "起始-生日，yyyy-MM-dd")
        private String startBirthday;
        @ApiModelProperty(value = "结束-生日，yyyy-MM-dd")
        private String endBirthday;
        @ApiModelProperty(value = "起始-创建时间，yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startCreateTime;
        @ApiModelProperty(value = "结束-创建时间，yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endCreateTime;
        @ApiModelProperty(value = "起始-修改时间，yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date startUpdateTime;
        @ApiModelProperty(value = "结束-修改时间，yyyy-MM-dd")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private Date endUpdateTime;

    }

    @Data
    public static class addForm {
        @ApiModelProperty(value = "登录账号",required=true)
        private String number;
        @ApiModelProperty(value = "真实名字",required = true)
        private String readName;

        @ApiModelProperty(value = "手机号码",required = true)
        private String telPhone;
        @ApiModelProperty(value = "性别",required = true)
        private String sex;

        @ApiModelProperty(value = "头像")
        private String headImg;


        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(!CheckUtil.checkNumber(number)) {
                errorInfos.put("number", "登录账号长度为4-16个字符,且不能包含非法字符*，&，#，@");
            }
            if(StringUtils.isEmpty(readName)) {
                errorInfos.put("readName", "真实姓名,不能为空");
            }
            if(StringUtils.isEmpty(telPhone)) {
                errorInfos.put("telPhone", "手机号码,不能为空");
            }
            if(StringUtils.isEmpty(sex)) {
                errorInfos.put("sex", "性别,不能为空");
            }

            return errorInfos;
        }
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


        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(StringUtils.isEmpty(userId)) {
                errorInfos.put("userId", "用户ID不能为空");
            }

            return errorInfos;
        }
    }


    @Data
    public static class setRoleForm {
        @ApiModelProperty(value = "用户ID",required = true)
        private Integer userId;
        @ApiModelProperty(value = "角色ID",required = true)
        private Integer roleId;

        public setRoleForm(){}

        public setRoleForm(Integer userId, Integer roleId) {
            this.userId = userId;
            this.roleId = roleId;
        }

        public Map<String,String> getErrorInfo(){
            Map<String,String> errorInfos = new HashMap<>();

            if(StringUtils.isEmpty(userId)) {
                errorInfos.put("userId", "用户ID不能为空");
            }
            if(StringUtils.isEmpty(roleId)) {
                errorInfos.put("roleId", "角色ID不能为空");
            }

            return errorInfos;
        }
    }


    @Data
    public static class deleteForm {
        @ApiModelProperty(value = "用户ID",required = true,example = "123456789")
        private int userId;

    }

}
