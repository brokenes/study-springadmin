package com.github.admin.dao;

import com.github.admin.common.domain.Menu;
import com.github.admin.serveice.dao.MenuDao;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MenuDaoTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuDaoTest.class);
    @Resource
    private MenuDao menuDao;

    @Test
    public void _测试菜单查询(){
        List<Menu> list = menuDao.findAll();
        LOGGER.info("查询菜单返回数据结果:{}",list);
    }

    @Test
    public void _测试菜单排序查询(){
        List<Menu> list = menuDao.findListByPidAndId(0L,2L);
        LOGGER.info("_测试菜单排序查询:{}",list);
    }
}
