package com.service.user;

import cn.hutool.crypto.SecureUtil;
import com.form.user.UserManageForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.user.RoleMapper;
import com.mapper.user.UserExtMapper;
import com.mapper.user.UserMapper;
import com.model.user.Role;
import com.model.user.User;
import com.model.user.UserExample;
import com.model.user.UserExt;
import com.result.Page;
import com.result.Result;
import com.result.ResultStatus;
import com.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserManageServiceImpl implements UserManageService {
    @Resource
    UserMapper userMapper;
    @Resource
    RoleMapper roleMapper;
    @Resource
    UserExtMapper userExtMapper;




    /**
     * 查询用户列表，根据多个筛选条件查询,需管理员权限
     * @param form
     * @return
     */
    @Override
    public Result list(UserManageForm.listForm form) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();

        if(!StringUtils.isEmpty(form.getUserId())){
            criteria.andUserIdEqualTo(form.getUserId());
        }
        if(!StringUtils.isEmpty(form.getNumber())){
            criteria.andNumberLike("%"+form.getNumber()+"%");
        }
        if(!StringUtils.isEmpty(form.getNickName())){
            criteria.andNickNameLike("%"+form.getNickName()+"%");
        }
        if(!StringUtils.isEmpty(form.getTelPhone())){
            criteria.andTelPhoneLike("%"+form.getTelPhone()+"%");
        }
        if(!StringUtils.isEmpty(form.getEmail())){
            criteria.andEmailLike("%"+form.getEmail()+"%");
        }
        if(!StringUtils.isEmpty(form.getQq())){
            criteria.andQqLike("%"+form.getQq()+"%");
        }
        if(!StringUtils.isEmpty(form.getWeiXin())){
            criteria.andWeiXinLike("%"+form.getWeiXin()+"%");
        }
        if(!StringUtils.isEmpty(form.getSex())){
            criteria.andSexEqualTo(form.getSex());
        }
        if(!StringUtils.isEmpty(form.getReadName())){
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

        // 排序
        if(!StringUtils.isEmpty(form.getOrderByClause())){
            example.setOrderByClause(form.getOrderByClause());
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

        return Result.success(users,page);
    }

    /**
     * 添加员工
     * @param form
     * @return
     */
    @Override
    public Result add(UserManageForm.addForm form) {
        // 首先检查入参信息的合法性，如有非法输入，返回错误信息
        Map<String, String> errorInfo = form.getErrorInfo();
        if(!errorInfo.isEmpty()){
            return Result.fail(errorInfo, ResultStatus.ERROR_Parameter);
        }

        User user = (User) ConvertUtil.convert(form, new User());

        user.setCreateTime(new Date());
        user.setPassword(SecureUtil.md5("123abc"));

        userMapper.insertSelective(user);

        return Result.success(1);
    }


    /**
     * 修改用户信息,根据ID修改
     * @param form
     * @return
     */
    @Override
    public Result update(UserManageForm.updateForm form) {
        // 首先检查入参信息的合法性，如有非法输入，返回错误信息
        Map<String, String> errorInfo = form.getErrorInfo();
        if(!errorInfo.isEmpty()){
            return Result.fail(errorInfo, ResultStatus.ERROR_Parameter);
        }

        User user = (User) ConvertUtil.convert(form,new User());

        user.setUpdateTime(new Date());

        int i = userMapper.updateByPrimaryKeySelective(user);

        if(i>0){
            return Result.success(i);
        }

        return Result.fail(0, ResultStatus.ERROR_User_Update);
    }


    /**
     * 根据id查询用户详情信息,需管理员权限
     * @param userId
     * @return
     */
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
    public Result setRole(UserManageForm.setRoleForm form,UserExt loginUser) {
        // 首先检查入参信息的合法性，如有非法输入，返回错误信息
        Map<String, String> errorInfo = form.getErrorInfo();
        if(!errorInfo.isEmpty()){
            return Result.fail(errorInfo, ResultStatus.ERROR_Parameter);
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
     * 重置密码
     * @return
     */
    @Override
    public Result resetPassword(Integer userId) {
        User user = new User();
        user.setUserId(userId);
        user.setUpdateTime(new Date());
        user.setPassword(SecureUtil.md5("123abc"));

        int i = userMapper.updateByPrimaryKeySelective(user);

        if(i>0){
            return Result.success(i);
        }

        return Result.fail("未知错误", ResultStatus.ERROR_Update);
    }




    /**
     * 删除用户，根据ID删除
     * @param form
     * @return
     */
    @Override
    public Result delete(UserManageForm.deleteForm form) {

        int i = userMapper.deleteByPrimaryKey(form.getUserId());

        if(i>0){
            return Result.success(i);
        }

        return Result.fail(0, ResultStatus.ERROR_User_Delete);
    }

}
