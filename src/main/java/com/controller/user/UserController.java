package com.controller.user;

import com.form.user.UserForm;
import com.model.user.User;
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
     * 根据id查询用户详情信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据id查询用户详情信息",notes = "根据id查询用户详情信息")
    @GetMapping("/getUserById")
    public Result getUserById(int userId){

        return Result.success(userService.getUserById(userId));
    }

    /**
     * 修改用户信息,根据ID修改
     * @param form
     * @return
     */
    @ApiOperation(value = "修改用户信息",notes = "根据ID修改,登录用户只能修改自己的信息")
    @PutMapping("/update")
    public Result update(UserForm.updateForm form,HttpSession session){
        UserExt loginUser = (UserExt) session.getAttribute("loginUser");
        form.setUserId(loginUser.getUserId());

        return userService.update(form);
    }

    /**
     * 修改密码,当前登录用户只能修改自己的密码
     * @param form
     * @return
     */
    @ApiOperation(value = "修改密码",notes = "修改密码,当前登录用户只能修改自己的密码")
    @PutMapping("/updatePassword")
    public Result updatePassword(UserForm.updatePasswordForm form,HttpSession session){
        User loginUser = (User) session.getAttribute("loginUser");
        form.setUserId(loginUser.getUserId());

        return userService.updatePassword(form);
    }



    /**
     * 获取当前登录用户的信息
     * @return
     */
    @ApiOperation(value = "获取当前登录用户的信息",notes = "获取当前登录用户的信息")
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpSession session){
        UserExt loginUser = (UserExt) session.getAttribute("loginUser");
        loginUser = userService.getUserById(loginUser.getUserId());

        session.setAttribute("loginUser",loginUser);

        return Result.success(loginUser);
    }
}
