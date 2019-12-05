package com.service.user;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
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

        return Result.success(userMapper.selectByExample(example));
    }


    /**
     * 查询用户列表，根据多个筛选条件查询,需管理员权限
     * @param form
     * @return
     */
    @Override
    public Result list(UserForm.listForm form) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if(form.getUserId()!=null){
            criteria.andUserIdEqualTo(form.getUserId());
        }
        if(form.getNumber()!=null){
            criteria.andNumberLike("%"+form.getNumber()+"%");
        }
        if(form.getNickName()!=null){
            criteria.andNickNameLike("%"+form.getNickName()+"%");
        }
        if(form.getTelPhone()!=null){
            criteria.andTelPhoneLike("%"+form.getTelPhone()+"%");
        }
        if(form.getEmail()!=null){
            criteria.andEmailLike("%"+form.getEmail()+"%");
        }
        if(form.getQq()!=null){
            criteria.andQqLike("%"+form.getQq()+"%");
        }
        if(form.getWeiXin()!=null){
            criteria.andWeiXinLike("%"+form.getWeiXin()+"%");
        }
        if(form.getSex()!=null){
            criteria.andSexEqualTo(form.getSex());
        }
        if(form.getReadName()!=null){
            criteria.andReadNameLike(form.getReadName()+"%");
        }

        if(form.getStartBirthday()!=null && form.getEndBirthday()==null){
            criteria.andBirthdayGreaterThan(form.getStartBirthday());
        }else if(form.getStartBirthday()==null && form.getEndBirthday()!=null){
            criteria.andBirthdayLessThan(form.getEndBirthday());
        }else if(form.getStartBirthday()!=null && form.getEndBirthday()!=null){
            criteria.andBirthdayBetween(form.getStartBirthday(),form.getEndBirthday());
        }

        if(form.getStartCreateTime()!=null && form.getEndCreateTime()==null){
            criteria.andCreateTimeGreaterThan(form.getStartCreateTime());
        }else if(form.getStartCreateTime()==null && form.getEndCreateTime()!=null){
            criteria.andCreateTimeLessThan(form.getEndCreateTime());
        }else if(form.getStartCreateTime()!=null && form.getEndCreateTime()!=null){
            criteria.andCreateTimeBetween(form.getStartCreateTime(),form.getEndCreateTime());
        }

        if(form.getStartUpdateTime()!=null && form.getEndUpdateTime()==null){
            criteria.andUpdateTimeGreaterThan(form.getStartUpdateTime());
        }else if(form.getStartUpdateTime()==null && form.getEndUpdateTime()!=null){
            criteria.andUpdateTimeLessThan(form.getEndUpdateTime());
        }else if(form.getStartUpdateTime()!=null && form.getEndUpdateTime()!=null){
            criteria.andUpdateTimeBetween(form.getStartUpdateTime(),form.getEndUpdateTime());
        }

        // 使用PageHelper插件分页
        PageHelper.startPage(form.getPage(),form.getLimit());
        List<User> users = userMapper.selectByExample(example);

        PageInfo<User> pageInfo = new PageInfo<>(users);
        Page page = form.pageHelperResult(pageInfo);

        List<UserExt> userList = new ArrayList<>();
        for (User user : users) {
            userList.add(getUserById(user.getUserId()));
        }

        return Result.success(page.getTotalRows(),users,page);
    }

    /**
     * 根据id查询用户详情信息,需管理员权限
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
     * 根据id设置用户角色,需管理员权限
     * @param form
     * @return
     */
    @Override
    public Result setRole(UserForm.setRoleForm form,UserExt loginUser) {
        // 首先检查用户注册信息的合法性，如有非法输入，返回错误信息
        if(!form.getErrorInfo().isEmpty()){
            return Result.fail(form.getErrorInfo(), ResultStatus.ERROR_Role_Set);
        }

        UserExt userExt = getUserById(form.getUserId());
        if(userExt==null){
            return Result.fail(0,ResultStatus.ERROR_User_No_Exist);
        }
        int targetLevel = 1;
        if(userExt.getRole()!=null && userExt.getRole().getLevel()!=null){
            targetLevel = userExt.getRole().getLevel();
        }

        // 如果目标用户权限等级不低于当前用户权限等级，则无权限修改
        if(targetLevel >= loginUser.getRole().getLevel()){
            return Result.fail(0,ResultStatus.ERROR_No_Permission);
        }

        // 如果要赋予的角色权限等级高于当前用户权限等级，则不被允许
        Role role = roleMapper.selectByPrimaryKey(form.getRoleId());
        if(role.getLevel() > loginUser.getRole().getLevel()){
            return Result.fail(0,ResultStatus.ERROR_No_Permission);
        }

        User user = (User) ConvertUtil.convert(form,new User());
        user.setUpdateTime(new Date());

        int num = userMapper.updateByPrimaryKeySelective(user);
        // 更新数据库成功，返回成功
        if(num>0){
            return Result.success(num);
        }

        return Result.fail(num,ResultStatus.ERROR_Role_Set);
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
     * 删除用户，根据ID删除
     * @param form
     * @return
     */
    @Override
    public Result delete(UserForm.deleteForm form) {

        int i = userMapper.deleteByPrimaryKey(form.getUserId());

        if(i>0){
            return Result.success(i);
        }

        return Result.fail(0, ResultStatus.ERROR_User_Delete);
    }

}
