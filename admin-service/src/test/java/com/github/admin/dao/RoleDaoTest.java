package com.github.admin.dao;

import com.github.admin.common.domain.Role;
import com.github.admin.serveice.dao.RoleDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RoleDaoTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoTest.class);
    @Resource
    private RoleDao roleDao;


    @Test
    public void _测试角色是否存在用户(){
        Role role = roleDao.findByRoleId(1L);
        LOGGER.info("当前用户userId:【{}】,查询用户角色是否存在role:【{}】",1L,role);
    }
}
