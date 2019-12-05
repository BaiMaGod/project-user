package com.controller.user;

import com.form.user.UserForm;
import com.model.user.UserExt;
import com.result.Result;
import com.result.ResultStatus;
import com.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Api(value = "用户相关操作 接口控制器",tags = {"用户相关操作 接口"})
@RestController
@RequestMapping("json/user")
public class UserController {
    @Autowired
    UserService userService;


    /**
     * 搜索用户,可输入账号、昵称模糊搜索
     * @param form
     * @return
     */
    @ApiOperation(value = "搜索用户",notes = "可输入账号、昵称模糊搜索")
    @GetMapping("/search")
    public Result search(UserForm.searchForm form){

        return userService.search(form);
    }

    /**
     * 修改用户信息,根据ID修改,需管理员或自己才能修改
     * @param form
     * @return
     */
    @ApiOperation(value = "修改用户信息",notes = "根据ID修改,需管理员或自己才能修改")
    @PutMapping("/update")
    public Result update(UserForm.updateForm form,HttpSession session){
        UserExt loginUser = (UserExt) session.getAttribute("loginUser");


        if(loginUser != null && (loginUser.getUserId().equals(form.getUserId()) || (loginUser.getRole()!=null && loginUser.getRole().getLevel()>=100))){
            return userService.update(form);
        }

        return Result.fail(0,ResultStatus.ERROR_No_Permission);
    }

    /**
     * 根据id设置用户角色,需管理员权限,且只能设置权限等级更低的角色
     * @param form
     * @return
     */
    @ApiOperation(value = "根据id设置用户角色",notes = "根据id设置用户角色,需管理员权限")
    @GetMapping("/setRole")
    public Result setRole(UserForm.setRoleForm form,HttpSession session){
        UserExt loginUser = (UserExt) session.getAttribute("loginUser");

        return userService.setRole(form,loginUser);
    }

    /**
     * 根据id查询用户详情信息,需管理员权限
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据id查询用户详情信息",notes = "根据多个筛选条件查询,需管理员权限")
    @GetMapping("/getUserById")
    public Result getUserById(int userId){

        return Result.success(userService.getUserById(userId));
    }

    /**
     * 查询用户列表，根据多个筛选条件查询,需管理员权限
     * @param form
     * @return
     */
    @ApiOperation(value = "查询用户列表",notes = "根据多个筛选条件查询,需管理员权限")
    @GetMapping("/list")
    public Result list(UserForm.listForm form){

        return userService.list(form);
    }



    /**
     * 删除用户，根据ID删除,需管理员才能删除
     * @param form
     * @return
     */
    @ApiOperation(value = "删除用户",notes = "根据ID删除")
    @DeleteMapping("/delete")
    public Result search(UserForm.deleteForm form){

        return userService.delete(form);
    }
}
