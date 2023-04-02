package com.github.admin.api;

import com.github.admin.client.UserServiceCient;
import com.github.admin.common.domain.User;
import com.github.admin.common.util.Result;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@ComponentScan("com.github.admin.client")
public class UserServiceClientTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceClientTest.class);
    @Autowired
    private UserServiceCient userServiceCient;

    @Test
    public void _测试服务端接口(){
        Result<User> result = userServiceCient.findByUserName("admin");
        LOGGER.info("查询结果:{}",result);
    }
}
