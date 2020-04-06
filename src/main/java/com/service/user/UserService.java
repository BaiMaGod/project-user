package com.service.user;

import com.form.user.UserForm;
import com.model.user.UserExt;
import com.result.Result;

public interface UserService {
    Result search(UserForm.searchForm form);

    UserExt getUserById(int userId);

    Result update(UserForm.updateForm form);

    Result updatePassword(UserForm.updatePasswordForm form);

}
