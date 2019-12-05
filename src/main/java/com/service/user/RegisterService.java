package com.service.user;

import com.form.user.RegisterForm;
import com.result.Result;

import javax.servlet.http.HttpSession;

public interface RegisterService {
    Result direct(RegisterForm.directForm form);

    Result simpleEmail(RegisterForm.simpleEmailForm form);

    Result seniorEmail(RegisterForm.seniorEmailForm form, HttpSession session);

    Result getEmailCode(RegisterForm.getEmailCode form,HttpSession session);

    Result qq(RegisterForm.qqForm form);

    Result weiXin(RegisterForm.weiXinForm form);
}
