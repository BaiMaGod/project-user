package com.service.user;

import com.form.user.UserManageForm;
import com.model.user.UserExt;
import com.result.Result;

public interface UserManageService {

    Result list(UserManageForm.listForm form);

    Result add(UserManageForm.addForm form);

    Result update(UserManageForm.updateForm form);

    Result setRole(UserManageForm.setRoleForm form, UserExt loginUser);

    Result resetPassword(Integer userId);

    Result delete(UserManageForm.deleteForm form);


}
