package com.github.admin.serveice.server.impl;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.RoleMenu;
import com.github.admin.common.domain.UserRole;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.MenuDao;
import com.github.admin.serveice.dao.RoleDao;
import com.github.admin.serveice.dao.RoleMenuDao;
import com.github.admin.serveice.dao.UserRoleDao;
import com.github.admin.serveice.server.RoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private MenuDao menuDao;


    /***
     * SELECT
     * 	role0_.id AS col_0_0_
     * FROM
     * 	sys_role role0_
     * 	LEFT OUTER JOIN sys_user_role users1_ ON role0_.id = users1_.role_id
     * 	LEFT OUTER JOIN sys_user user2_ ON users1_.user_id = user2_.id
     * 	AND ( user2_.STATUS != 3 )
     * WHERE
     * 	( role0_.STATUS != 3 )
     * 	AND user2_.id =?
     * 	AND role0_.STATUS =?
     * 	LIMIT ?
     * @param userId
     * @return
     */

    @Override
    public Result<Boolean> existsUserOk(Long userId) {
        List<UserRole> list =  userRoleDao.findByUserId(userId);
        if(CollectionUtils.isEmpty(list)){
            LOGGER.error("当前userId:{}查询用户角色不存在",userId);
            return Result.fail("401","当前用户对应的角色不存在");
        }
        for(UserRole userRole:list){
            Long roleId = userRole.getRoleId();
            Role role = roleDao.findByRoleId(roleId);
            LOGGER.info("当前roleId:{} 查询角色返回结果role:{}",roleId,role);
            if(role != null){
                Boolean isExists = true;
                return Result.ok(isExists);
            }
        }
        return Result.fail("401","当前用户对应的角色不存在");
    }

    @Override
    public Result<Set<Role>> getUserOkRoleList(Long userId) {
        if(userId == null){
            LOGGER.error("当前传递的userId为空!");
            return Result.fail("404","用户id为空!");
        }
        List<UserRole> userRoleList = userRoleDao.findByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleList)){
            LOGGER.error("当前userId:{}查询用户角色不存在",userId);
            return Result.fail("404","当前用户对应的角色不存在");
        }
        List<Role> roleList = new ArrayList<Role>();
        for(UserRole userRole:userRoleList){
            Long roleId = userRole.getRoleId();
            Role role = roleDao.findByRoleId(roleId);
            if(role != null){
                roleList.add(role);
            }
        }
        if(CollectionUtils.isEmpty(roleList)){
            LOGGER.error("当前userId:{}查询角色不存在",userId);
            return Result.fail("404","当前角色不存在");
        }
        Set<Role> set = roleList.stream().collect(Collectors.toSet());
        LOGGER.info("当前用户userId:{},查询对应的角色集合大小:{}",userId,set.size());

        for(Role role:set){
            Long roleId = role.getId();
            Set<Menu> menuSet = new HashSet<Menu>();
            List<RoleMenu> list = roleMenuDao.findByRoleId(roleId);
            if(CollectionUtils.isNotEmpty(list)){
                menuSet = parseListToSet(list);
            }
            role.setMenus(menuSet);
        }
        return Result.ok(set);
    }

    private Set<Menu> parseListToSet(List<RoleMenu> list){
        Set<Menu> set = new HashSet<Menu>();
        List<Menu> menuList = new ArrayList<>();
        for(RoleMenu roleMenu:list){
            Long menuId = roleMenu.getMenuId();
            Menu menu = menuDao.findById(menuId);
            if(menu != null){
                menuList.add(menu);
            }
        }
        if(CollectionUtils.isNotEmpty(menuList)){
            set = menuList.stream().collect(Collectors.toSet());
        }
        return  set;
    }

}
