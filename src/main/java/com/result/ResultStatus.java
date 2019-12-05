package com.result;

/**
 * 返回状态码枚举类
 */
public enum ResultStatus {
    SUCCESS(0,"请求成功"),
    ERROR_SYS(1,"系统错误"),
    ERROR_UPDATE(2,"修改错误"),
    ERROR_No_Permission(3,"无权限"),
    ERROR_Role_Set(4,"角色设置失败"),
    ERROR_No_Login(5,"未登录"),
    ERROR_Email(10,"邮箱格式错误"),
    ERROR_Register(11,"注册失败，请稍后再试"),
    ERROR_Register_Number_Password(12,"注册失败，请输入合法的账号和密码"),
    ERROR_Register_Number_Exist(13,"注册失败，账号已存在"),
    ERROR_Login_Direct(21,"登录失败，请输入合法的账号和密码"),
    ERROR_Login_Number_Password(22,"登录失败，账号或密码错误"),
    ERROR_Login_Number_No_Exist(23,"登录失败，账号不存在"),
    ERROR_User_Update(31,"修改用户信息失败"),
    ERROR_User_Delete(32,"删除用户信息失败"),
    ERROR_User_No_Exist(33,"用户不存在"),
    ERROR_V_Code(41,"验证码错误"),
    ERROR_V_Code_OutTime(42,"验证码超时失效,请重发");

    private int code;
    private String msg;

    ResultStatus(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}
