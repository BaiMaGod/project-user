package com.service.user;

import com.form.user.LoginForm;
import com.result.Result;

import javax.servlet.http.HttpSession;

public interface LoginService {
    Result direct(LoginForm.directForm form);

    Result email(LoginForm.emailForm form, HttpSession session);

    Result qq(LoginForm.qqForm form);

    Result weiXin(LoginForm.weiXinForm form);
}
