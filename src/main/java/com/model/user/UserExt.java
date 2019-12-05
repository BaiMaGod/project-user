package com.model.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserExt{
    private Integer userId;

    private Integer roleId;

    private String number;

    private String password;

    private String nickName;

    private String headImg;

    private String telPhone;

    private String email;

    private String qq;

    private String weiXin;

    private String sex;

    private String readName;

    private String birthday;

    private String introduce;

    private Date createTime;

    private Date updateTime;

    protected Role role;
}
