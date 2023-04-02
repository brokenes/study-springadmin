package com.github.admin;

import com.github.admin.common.domain.User;
import com.github.admin.serveice.dao.UserDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class SrpingbootApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SrpingbootApplicationTest.class);

    @Resource
    UserDao userDao;

    @Test
    public void _测试用户根据名称查询接口(){
        User user = userDao.findByUserName("admin");
        LOGGER.info("当前查询用户对象数据:{}",user);
    }
}
