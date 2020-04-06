package com.service.user;

import cn.hutool.crypto.SecureUtil;
import com.form.user.UserForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.user.RoleMapper;
import com.mapper.user.UserExtMapper;
import com.mapper.user.UserMapper;
import com.model.user.*;
import com.result.Page;
import com.result.Result;
import com.result.ResultStatus;
import com.utils.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserExtMapper userExtMapper;



    /**
     * 搜索用户,可输入账号、昵称模糊搜索
     * @param form
     * @return
     */
    @Override
    public Result search(UserForm.searchForm form) {
        UserExample example = new UserExample();

        example.or().andNickNameLike("%"+form.getSearchStr()+"%");
        example.or().andNumberLike("%"+form.getSearchStr()+"%");

        // 排序
        if(!StringUtils.isEmpty(form.getOrderByClause())){
            example.setOrderByClause(form.getOrderByClause());
        }

        // 使用PageHelper插件分页
        PageHelper.startPage(form.getPage(),form.getLimit());
        List<User> users = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        Page page = form.pageHelperResult(pageInfo);


        return Result.success(users,page);
    }


    /**
     * 根据id查询用户详情信息
     * @param userId
     * @return
     */
    @Override
    public UserExt getUserById(int userId) {
        UserExt userExt = userExtMapper.selectUserExtById(userId);
        if(userExt!=null){
            userExt.setPassword("");
        }

        return userExt;
    }


    /**
     * 修改用户信息,根据ID修改
     * @param form
     * @return
     */
    @Override
    public Result update(UserForm.updateForm form) {
        User user = (User) ConvertUtil.convert(form,new User());

        user.setUpdateTime(new Date());

        int i = userMapper.updateByPrimaryKeySelective(user);

        if(i>0){
            return Result.success(i);
        }

        return Result.fail(0, ResultStatus.ERROR_User_Update);
    }



    /**
     * 修改密码,根据ID修改,只能修改自己的密码
     * @param form
     * @return
     */
    @Override
    public Result updatePassword(UserForm.updatePasswordForm form) {
        // 首先检查入参信息的合法性，如有非法输入，返回错误信息
        Map<String, String> errorInfo = form.getErrorInfo();
        if(!errorInfo.isEmpty()){
            return Result.fail(errorInfo, ResultStatus.ERROR_Parameter);
        }

        User user = userMapper.selectByPrimaryKey(form.getUserId());

        // 如果原密码输入正确，则允许修改密码
        if(user.getPassword().equals(SecureUtil.md5(form.getOldPassword()))){
            user.setPassword(SecureUtil.md5(form.getNewPassword()));
            user.setUpdateTime(new Date());
            int i = userMapper.updateByPrimaryKeySelective(user);

            if(i>0){
                return Result.success(i);
            }

            return Result.fail("未知错误", ResultStatus.ERROR_Update);
        }

        return Result.fail("原密码错误",ResultStatus.ERROR_Update);
    }


}
