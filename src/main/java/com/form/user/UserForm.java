package com.form.user;

import com.form.PageForm;
import com.utils.CheckUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
        @ApiModelProperty(value = "性别")
        private String sex;
        @ApiModelProperty(value = "真实名字")
        private String readName;

        private String headImg;

        private String birthday;

        private String introduce;
    }

    @Data
    public static class deleteForm {
        @ApiModelProperty(value = "用户ID",required = true,example = "123456789")
        private int userId;
    }

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
        @ApiModelProperty(value = "性别")
        private String sex;
        @ApiModelProperty(value = "真实名字")
        private String readName;
        @ApiModelProperty(value = "起始-生日")
        private String startBirthday;
        @ApiModelProperty(value = "结束-生日")
        private String endBirthday;
        @ApiModelProperty(value = "起始-创建时间")
        private Date startCreateTime;
        @ApiModelProperty(value = "结束-创建时间")
        private Date endCreateTime;
        @ApiModelProperty(value = "起始-修改时间")
        private Date startUpdateTime;
        @ApiModelProperty(value = "结束-修改时间")
        private Date endUpdateTime;
    }

    @Data
    public static class getUserByIdForm {
        @ApiModelProperty(value = "用户ID",required = true,example = "123456789")
        private int userId;
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

            if(userId == null || roleId ==null) {
                errorInfos.put("null", "用户ID和角色ID都不能为空");
            }

            return errorInfos;
        }
    }
}
