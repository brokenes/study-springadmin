package com.github.admin.serveice.server.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.UserDao;
import com.github.admin.serveice.server.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private  UserDao userDao;

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
        Integer pageNo = userRequest.getPageNo() == null ? 1 : userRequest.getPageNo();
        Integer pageSize = userRequest.getPageSize() == null ? 1 : userRequest.getPageSize();
        Map<String,Object> map = BeanUtil.beanToMap(userRequest);
        DataPage<User> dataPage = new DataPage<User>(pageNo,pageSize);
        int startIndex = dataPage.getStartIndex();
        int endIndex = dataPage.getEndIndex();
        map.put("startIndex",startIndex);
        map.put("endIndex",endIndex);
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
}
