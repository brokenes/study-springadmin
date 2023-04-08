package com.github.admin.service;

import com.alibaba.fastjson.JSON;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.MenuService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MenuServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceTest.class);

    @Resource
    private MenuService menuServiceImpl;


    @Test
    public  void _测试根据菜单_状态_url查询(){
        MenuRequest menuRequest = new MenuRequest();
//        menuRequest.setTitle("主");
        Result<List<Menu>> result =  menuServiceImpl.findAll(menuRequest);
        LOGGER.info("查询菜单结果:{}", JSON.toJSONString(result));
    }
}
