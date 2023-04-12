package com.github.admin.serveice.server.impl;

import com.alibaba.fastjson.JSON;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.MenuDao;
import com.github.admin.serveice.dao.UserDao;
import com.github.admin.serveice.server.MenuService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
    @Resource
    private MenuDao menuDao;

    @Resource
    private UserDao userDao;

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
        if(pid == null || id == null){
            return Result.fail("405","参数pid或id为空!");
        }
        List<Menu>  list = menuDao.findListByPidAndId(pid,id);
        if(CollectionUtils.isEmpty(list)){
            LOGGER.error("pid:{},id:{}查询对应的排序数据集合为空",pid,id);
            return Result.fail("404","查询数据为空");
        }
        LOGGER.info("根据参数pid:{},id:{} 查询集合大小为:{}",pid,id,list.size());
        return Result.ok(list);
    }

    @Override
    public Result<Integer> getSortMax(Long pid) {
        LOGGER.info("查询菜单排序参数pid:{}",pid);
        if(pid == null){
            return Result.fail("405","参数pid为空!");
        }
        Integer maxSort = menuDao.getSortMax(pid);
        LOGGER.info("查询菜单排序结果,参数pid:{},排序结果maxSort:{}",pid,maxSort);
        return Result.ok(maxSort);
    }

    @Override
    public Result<Menu> findMenuByPid(Long pid) {
        LOGGER.info("查询菜单参数pid:{}",pid);
        if(pid == null){
            return Result.fail("405","参数pid为空!");
        }
        Menu menu = menuDao.findById(pid);
        if(menu == null){
            return Result.fail("404","查询菜单数据为空!");
        }
        LOGGER.info("查询菜单结果,参数pid:{},结果menu:{}",pid,menu);
        return Result.ok(menu);
    }

    @Override
    @Transactional
    public Result<Integer> saveMenu(Menu menu) {
        LOGGER.info("添加菜单请求参数menu:{}",menu);
        Long pid = menu.getPid();
        Date date = new Date();
        if(pid == null){
            LOGGER.error("菜单pid参数为空!");
            return Result.fail("405","参数pid为空!");
        }
        if(pid == 0){
            int parentMenuType = 0;
            int menuType = menu.getType();
            if(menuType - parentMenuType > 1){
                LOGGER.error("操作用户没有选择正确的菜单类型,父菜单类型parentMenuType:{},当前添加菜单类型menuType:{}", parentMenuType,menuType);
                return  Result.fail("404","请选择正确的菜单类型!");
            }
            menu.setPids("[" + pid + "]");
        }else{
            Menu parentMenu = menuDao.findById(pid);
            if(parentMenu == null){
                LOGGER.error("查询父菜单为空,pid:{}",pid);
                return Result.fail("404","查询菜单数据为空!");
            }
            int parentMenuType = parentMenu.getType();
            int menuType = menu.getType() - 1;
            if(parentMenuType >= 3 || parentMenuType != menuType){
                LOGGER.error("操作用户没有选择正确的菜单类型,父菜单类型parentMenuType:{},当前添加菜单类型menuType:{}", parentMenuType,menuType);
                return  Result.fail("404","请选择正确的菜单类型!");
            }
            menu.setPids(parentMenu.getPids() + ",[" + pid + "]");
        }
        if(menu.getSort() == null){
            Integer maxSort = menuDao.getSortMax(pid);
            menu.setSort(maxSort != null ? maxSort - 1 : 0);
        }else{
            menu.setSort(menu.getSort() + 1);
        }
        menu.setCreateDate(date);
        menu.setUpdateDate(date);
        menu.setStatus(1);
        LOGGER.info("添加菜单参数:{}", JSON.toJSONString(menu));
        Integer sort = menu.getSort();
        List<Menu> menuList = menuDao.findMenuByPid(pid);
        menuList.stream().filter(s -> s.getSort() >= sort).forEach(m -> {
            Long id = m.getId();
            Integer s = m.getSort() + 1;
            Menu updateMenu = new Menu();
            updateMenu.setId(id);
            updateMenu.setSort(s);
            Integer updateStatus = menuDao.update(updateMenu);
            LOGGER.info("更新当前菜单排序,id:{},sort:{}",id,s);
            if(updateStatus != 1){
                LOGGER.error("更新菜单排序失败,菜单对象updateMenu数据:{},返回结果:{}",updateMenu,updateStatus);
                throw new ResultException(ResultEnum.SAVE_MENU_ERROR);
            }
        });
        Integer status = menuDao.save(menu);
        LOGGER.info("添加菜单返回状态结果:{}",status);
        if(status != 1){
            LOGGER.error("添加菜单失败,菜单对象数据:{},返回结果:{}",menu,status);
            throw new ResultException(ResultEnum.SAVE_MENU_ERROR);
        }
        return Result.ok(status);
    }

    @Override
    public Result<Menu> findMenuById(Long id) {
        if(id == null){
            LOGGER.error("根据Id查询对应菜单参数id为空!");
            return Result.fail("405","参数为空");
        }
        Menu menu = menuDao.findById(id);
        if(menu == null){
            LOGGER.error("根据Id:{}查询对应菜单为空!",id);
            return Result.fail("404","查询菜单为空");
        }
        Long createBy = menu.getCreateBy();
        Long updateBy = menu.getUpdateBy();
        Long pid = menu.getPid();
        User createUser = userDao.findUserById(createBy);
        User updateUser = userDao.findUserById(updateBy);
        Menu pMenu = menuDao.findById(pid);
        if(pMenu == null && pid == 0){
            pMenu = new Menu();
            pMenu.setId(0L);
            pMenu.setTitle("顶级菜单");
        }
        menu.setPMenu(pMenu);
        menu.setCreateUser(createUser);
        menu.setUpdateUser(updateUser);
        return Result.ok(menu);
    }

    @Override
    public Result<Integer> deleteMenuById(Long id) {
        if(id == null){
            LOGGER.error("当前删除菜单id为空!");
            return  Result.fail("405","请求参数为空!");
        }
        List<Menu> list = menuDao.findMenuByPid(id);
        if(CollectionUtils.isNotEmpty(list)){
            LOGGER.error("当前id关联子菜单,删除失败id:{},子菜单集合大小:{}",id,list.size());
            return Result.fail("500","当前菜单关联子菜单,删除失败!");
        }
        Integer stasus = menuDao.deleteById(id);
        if(stasus != 1){
            LOGGER.error("删除菜单失败,id:{}",id);
            return Result.fail("500","菜单删除失败");
        }
        return Result.ok(stasus);
    }

    @Override
    @Transactional
    public Result<Integer> updateMenuStatus(List<Menu> list) {
        if(CollectionUtils.isEmpty(list)){
            LOGGER.error("更新菜单状态为空或者id为空!");
            return  Result.fail("405","请求参数为空!");
        }
        Integer updateStatus = 0;
        for(Menu menu:list){
            if(menu == null || menu.getId() == null){
                LOGGER.error("更新菜单状态为空或者id为空!");
                return  Result.fail("405","请求参数为空!");
            }
             updateStatus = menuDao.update(menu);
            if(updateStatus != 1){
                LOGGER.error("更新菜单失败,id:{},数据库事务将会回滚",menu.getId());
                throw  new ResultException(ResultEnum.UPDATE_MENU_STATUS_ERROR);
            }
        }
        return Result.ok(updateStatus);
    }
}
