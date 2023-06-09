package com.github.admin.service;

import com.alibaba.fastjson.JSON;
import com.github.admin.common.domain.Role;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.RoleService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootTest
public class RoleServiceTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceTest.class);

    @Resource
    private RoleService roleServiceImpl;

    @Test
    public void _测试根据用户id查询角色是否存在(){
        Result<Boolean> result = roleServiceImpl.existsUserOk(11L);
        LOGGER.info("查询用户对应角色返回结果:{}", JSON.toJSONString(result));
    }

    @Test
    public void _测试根据用户id查询用户对应的角色集合(){
        Result<Set<Role>> result = roleServiceImpl.getUserOkRoleList(2L);
        LOGGER.info("_测试根据用户id查询用户对应的角色集合:{}", JSON.toJSONString(result));
    }
}
