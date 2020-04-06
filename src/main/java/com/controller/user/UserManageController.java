package com.controller.user;

import com.form.user.UserManageForm;
import com.model.user.UserExt;
import com.result.Result;
import com.service.user.UserManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Api(value = "用户管理相关操作 接口控制器",tags = {"用户管理相关操作 接口"})
@RestController
@RequestMapping("json/userManage")
public class UserManageController {
    @Autowired
    UserManageService userManageService;



    /**
     * 查询用户列表，根据多个筛选条件查询
     * @param form
     * @return
     */
    @ApiOperation(value = "查询用户列表",notes = "根据多个筛选条件查询")
    @GetMapping("/list")
    public Result list(UserManageForm.listForm form){

        return userManageService.list(form);
    }

    /**
     * 添加用户
     * @param form
     * @return
     */
    @ApiOperation(value = "添加用户",notes = "添加用户,默认初始密码：123abc")
    @PostMapping("/add")
    public Result add(UserManageForm.addForm form){

        return userManageService.add(form);
    }


    /**
     * 修改用户信息,根据ID修改
     * @param form
     * @return
     */
    @ApiOperation(value = "修改用户信息",notes = "根据ID修改")
    @PutMapping("/update")
    public Result update(UserManageForm.updateForm form,HttpSession session){

        return userManageService.update(form);
    }

    /**
     * 根据id设置用户角色,需管理员权限,且只能设置权限等级更低的角色
     * @param form
     * @return
     */
    @ApiOperation(value = "根据id设置用户角色",notes = "根据id设置用户角色,只能设置权限等级更低的角色")
    @PutMapping("/setRole")
    public Result setRole(UserManageForm.setRoleForm form,HttpSession session){
        UserExt loginUser = (UserExt) session.getAttribute("loginUser");

        return userManageService.setRole(form,loginUser);
    }


    /**
     * 重置密码
     * @return
     */
    @ApiOperation(value = "重置密码",notes = "重置后的密码为：123abc")
    @PutMapping("/resetPassword")
    public Result resetPassword(int userId){

        return userManageService.resetPassword(userId);
    }



    /**
     * 删除用户，根据ID删除,需管理员才能删除
     * @param form
     * @return
     */
    @ApiOperation(value = "删除用户",notes = "根据ID删除")
    @DeleteMapping("/delete")
    public Result search(UserManageForm.deleteForm form){

        return userManageService.delete(form);
    }
}
