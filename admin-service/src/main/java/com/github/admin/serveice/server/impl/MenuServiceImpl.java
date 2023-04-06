package com.github.admin.serveice.server.impl;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.MenuDao;
import com.github.admin.serveice.server.MenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Resource
    private MenuDao menuDao;

    @Override
    public Result<List<Menu>> findAll() {
        List<Menu> list = menuDao.findAll();
        if(CollectionUtils.isEmpty(list)){
            LOGGER.error("查询菜单数据为空！");
            return Result.fail("404","查询菜单数据为空");
        }
        LOGGER.info("查询菜单集合返回大小:{}",list.size());
        return Result.ok(list);
    }
}
