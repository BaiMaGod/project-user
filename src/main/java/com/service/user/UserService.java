package com.service.user;

import com.form.user.UserForm;
import com.model.user.UserExt;
import com.result.Result;

public interface UserService {
    Result search(UserForm.searchForm form);

    Result update(UserForm.updateForm form);

    Result delete(UserForm.deleteForm form);

    Result list(UserForm.listForm form);

    UserExt getUserById(int userId);

    Result setRole(UserForm.setRoleForm form,UserExt loginUser);
}
