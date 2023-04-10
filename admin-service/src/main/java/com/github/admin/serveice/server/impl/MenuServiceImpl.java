package com.github.admin.serveice.server.impl;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.MenuDao;
import com.github.admin.serveice.server.MenuService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Override
    public Result<List<Menu>> findAll(MenuRequest menuRequest){
        try {
            Map<String,String> map = BeanUtils.describe(menuRequest);
//            if(menuRequest.getStatus() == null){
//                map.put("status","1");
//            }
            List<Menu> list = menuDao.findByCondition(map);
            if(CollectionUtils.isEmpty(list)){
                LOGGER.error("查询菜单集合数据为空,查询参数为map:{}",map);
                return Result.fail("404","查询菜单集合数据为空");
            }
            return Result.ok(list);
        }catch(Exception e){
            LOGGER.error("查询菜单集合异常:",e);
            return Result.fail("500","查询菜单集合异常");
        }

    }

    @Override
    public Result<List<Menu>> findListByPidAndId(Long pid, Long id) {
        LOGGER.info("查询菜单集合排序pid:{},id:{}",pid,id);
        List<Menu>  list = menuDao.findListByPidAndId(pid,id);
        if(CollectionUtils.isEmpty(list)){
            LOGGER.error("pid:{},id:{}查询对应的排序数据集合为空",pid,id);
            return Result.fail("404","查询数据为空");
        }
        LOGGER.info("根据pid:{},id:{}查询集合大小为:{}",pid,id,list.size());
        return Result.ok(list);
    }

}
