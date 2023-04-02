package com.github.admin.serveice.server.impl;

import com.github.admin.common.domain.User;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.UserDao;
import com.github.admin.serveice.server.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
