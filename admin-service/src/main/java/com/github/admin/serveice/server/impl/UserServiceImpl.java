package com.github.admin.serveice.server.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.User;
import com.github.admin.common.domain.UserRole;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.RoleDao;
import com.github.admin.serveice.dao.UserDao;
import com.github.admin.serveice.dao.UserRoleDao;
import com.github.admin.serveice.server.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private  UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleDao roleDao;

    @Override
    public Result<User> findByUserName(String userName) {
        LOGGER.info("查询用户信息userName:{}",userName);
        User user = userDao.findByUserName(userName);
        if(user == null){
            LOGGER.error("当前用户名称userName:{}查询用户信息不存在",userName);
            return Result.fail("404","当前用户不存在");
        }
        return Result.ok(user);
    }

    @Override
    public Result<DataPage<User>> getPageList(UserRequest userRequest) {
        Integer pageNo =  userRequest.getPageNo();
        Integer pageSize = userRequest.getPageSize();
        Map<String,Object> map = BeanUtil.beanToMap(userRequest);
        DataPage<User> dataPage = new DataPage<User>(pageNo,pageSize);
        int startIndex = dataPage.getStartIndex();
        int endIndex = dataPage.getPageSize();
        map.put("start",startIndex);
        map.put("offset",endIndex);
        LOGGER.info("用户分页查询当前第:{}页,每页数据显示:{}条数据,mysql查询startIndex:{},endIndex:{}", pageNo,pageSize,startIndex,endIndex);
        long totalCount = userDao.getPageCount(map);
        List<User> list = userDao.getPageList(map);
        LOGGER.info("用户查询数据totalCount:{},查询请求参数:{}",totalCount,userRequest);
        dataPage.setTotalCount(totalCount);
        dataPage.setDataList(list);
        return Result.ok(dataPage);

    }

    @Transactional
    @Override
    public Result<Integer> updateUserStatus(List<User> list) {
        if(CollectionUtils.isEmpty(list)){
            LOGGER.error("修改用户列表状态参数为空！");
            return Result.fail("405","请求参数为空");
        }
        Integer updateStatus = 0;
        for(User user:list){
            if(user.getId() == null){
                LOGGER.error("修改用户启用/停用状态id为空！");
                throw new ResultException(ResultEnum.UPDATE_ADMIN_STATUS_ERROR);
            }
            updateStatus = userDao.updateUser(user);
            if(updateStatus != 1){
                LOGGER.error("修改用户启用/停用状态异常,id:{},status:{}",user.getId(),user.getStatus());
                throw new ResultException(ResultEnum.UPDATE_ADMIN_STATUS_ERROR);
            }
        }
        return Result.ok(updateStatus);
    }

    @Override
    public Result<Integer> saveUser(User user) {
        String password = user.getPassword();
        String confirm = user.getConfirm();
        String userName = user.getUserName();
        Date date = new Date();
        if(StringUtils.isBlank(password) || StringUtils.isBlank(confirm)){
            LOGGER.error("用户密码或者确认密码为空");
            return Result.fail("405","请求参数为空!");
        }
        if(!StringUtils.equals(password,confirm)){
            LOGGER.error("用户密码和确认密码不一致,password:{},confirm:{}",password,confirm);
            return Result.fail("405","输入密码和确认密码不一致");
        }
        user.setCreateDate(date);
        user.setUpdateDate(date);
        int existUserCount = userDao.findUserCountByUserName(userName);
        if(existUserCount > 0){
            LOGGER.error("当前用户已经存在,userName:{}",userName);
            return Result.fail("405","当前用户已经存在!");
        }

        Integer status = userDao.saveUser(user);
        if(status != 1){
            LOGGER.error("添加用户失败,status:{}",status);
            return Result.fail("405","添加用户失败!");
        }

        return Result.ok(status);
    }

    @Override
    public Result<User> findUserById(Long id) {
        if(id == null){
            LOGGER.error("请求参数id为空");
            return Result.fail("405","请求参数为空!");
        }
        User user = userDao.findUserById(id);
        if(user == null){
            LOGGER.error("查询用户id:{}对应数据为空",id);
            return Result.fail("405","查询用户数据为空!");
        }
        return Result.ok(user);
    }

    @Override
    public Result<Integer> updateUserPwd(User user) {
        if(user.getId() == null){
            LOGGER.error("修改用户密码请求参数id为空");
            return Result.fail("405","请求参数为空!");
        }
        String password = user.getPassword();
        String confirm = user.getConfirm();
        if(StringUtils.isBlank(password) || StringUtils.isBlank(confirm)){
            LOGGER.error("用户密码或者确认密码为空");
            return Result.fail("405","请求参数为空!");
        }
        if(!StringUtils.equals(password,confirm)){
            LOGGER.error("用户密码和确认密码不一致,password:{},confirm:{}",password,confirm);
            return Result.fail("405","输入密码和确认密码不一致");
        }
        Integer status = userDao.updateUser(user);
        if(status != 1){
            LOGGER.error("修改用户密码失败,status:{}",status);
            return Result.fail("405","修改用户密码失败!");
        }
        return Result.ok(status);
    }

    @Override
    public Result<Integer> updateUser(User user) {
        if(user == null || user.getId() == null){
            LOGGER.error("修改用户请求参数数据或者id为空");
            return Result.fail("405","请求参数为空!");
        }
        user.setUpdateDate(new Date());
        String userName = user.getUserName();
        Long id = user.getId();
        int existUserCount = userDao.findUserCountByUserNameAndNotId(userName,id);
        if(existUserCount > 0){
            LOGGER.error("当前用户已经存在,userName:{},id",userName,id);
            return Result.fail("405","当前用户已经存在!");
        }
        Integer status = userDao.updateUser(user);
        if(status != 1){
            LOGGER.error("修改用户失败,status:{}",status);
            return Result.fail("405","修改用户失败!");
        }
        return Result.ok(status);
    }

    @Override
    public Result<User> findUserAndRoleById(Long id) {
        if(id == null){
            LOGGER.error("请求参数id为空");
            return Result.fail("405","请求参数为空!");
        }
        User user = userDao.findUserById(id);
        if(user == null){
            LOGGER.error("查询用户id:{}对应数据为空",id);
            return Result.fail("405","查询用户数据为空!");
        }
        Set<Role> set = new HashSet<Role>();
        List<UserRole> userRoleList = userRoleDao.findByUserId(id);
        for(UserRole userRole:userRoleList){
            Long roleId = userRole.getRoleId();
            Role role = roleDao.findByRoleId(roleId);
            LOGGER.info("查询用户对应的角色,roleId:{},结果:{}",roleId,role);
            if(role != null){
                set.add(role);
            }
        }
        user.setRoles(set);
        return Result.ok(user);
    }

    @Transactional
    @Override
    public Result<Integer> userAuth(User user) {
        if(user == null || user.getId() == null || CollectionUtils.isEmpty(user.getRoles())){
            LOGGER.error("用户授权请求参数为空,user:{}",user);
            return Result.fail("405","请求参数为空");
        }
        Long id = user.getId();
        Set<Role> set = user.getRoles();
        Integer userRoleStatus = userRoleDao.deleteByUserId(id);
//        if(userRoleStatus != 1){
//            LOGGER.error("用户授权异常,userId:{},status:{}",id,userRoleStatus);
//            throw new ResultException(ResultEnum.AUTH_USER_ERROR);
//        }
        for(Role role:set){
            UserRole userRole = new UserRole();
            Long roleId = role.getId();
            userRole.setUserId(id);
            userRole.setRoleId(roleId);
            Integer status = userRoleDao.save(userRole);
            if(status != 1){
                LOGGER.error("用户保存用户角色异常,userId:{},roleId:{},status:{}",id,roleId,status);
                throw new ResultException(ResultEnum.AUTH_USER_ERROR);
            }
        }
        return Result.ok(userRoleStatus);
    }
}
