package com.github.admin.service;

import com.alibaba.fastjson2.JSON;
import com.github.admin.common.domain.User;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.UserService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);
    @Resource
    private UserService userServiceImpl;


    @Test
    public void _测试用户查询接口(){
        Result<User> result = userServiceImpl.findByUserName("admin");
        LOGGER.info("查询结果:{}", JSON.toJSON(result));
    }
}
