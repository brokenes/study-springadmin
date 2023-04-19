package com.github.admin.serveice.server.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.*;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.dao.*;
import com.github.admin.serveice.server.RoleService;
import com.github.framework.sensitive.core.api.SensitiveUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
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

    @Resource
    private UserDao userDao;


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

    @Override
    public Result<List<Role>> findAllRole() {
        List<Role> list = roleDao.findAllRole();
        return Result.ok(list);
    }

    @Override
    public Result<DataPage<Role>> rolePage(RoleRequest roleRequest) {
        Integer pageSize = roleRequest.getPageSize();
        Integer pageNo = roleRequest.getPageNo();
        if(StringUtils.isBlank(roleRequest.getAsc()) && StringUtils.isBlank(roleRequest.getOrderByColumn())){
            roleRequest.setAsc("desc");
            roleRequest.setOrderByColumn("create_date");
        }
        DataPage<Role> dataPage = new DataPage<Role>(pageNo,pageSize);
        Map<String,Object> map = BeanUtil.beanToMap(roleRequest);
        map.put("start",dataPage.getStartIndex());
        map.put("offset",dataPage.getPageSize());
        LOGGER.info("角色分页查询,当前第:{},每页显示:{}",pageNo,pageSize);
        Long count = roleDao.pageListCount(map);
        List<Role> list = roleDao.pageList(map);
        dataPage.setTotalCount(count);
        dataPage.setDataList(list);
        return Result.ok(dataPage);
    }

    @Override
    public Result<Role> getUserListByRoleId(Long id) {
        if(id == null){
            LOGGER.error("查询角色对应用户列表参数id为空");
            return Result.fail("405","请求参数为空");
        }
        Role role = roleDao.findByRoleId(id);
        if(role == null){
            LOGGER.error("查询用户角色不存在,roleId:{}",id);
            return Result.fail("404","角色不存在!");
        }
        List<UserRole> userRole = userRoleDao.findByRoleId(id);
        Set<User> users = new HashSet<User>();
        if(CollectionUtils.isEmpty(userRole)){
            LOGGER.error("查询角色对应的用户集合不存在,roleId:{}",id);
            return Result.fail("404","用户角色不存在!");
        }
        userRole.stream().forEach(u ->{
            Long userId = u.getUserId();
            User user = userDao.findUserById(userId);
            if(user != null){
                users.add(SensitiveUtils.desCopy(user));
            }
        });
        role.setUsers(users);
        return Result.ok(role);
    }

    @Override
    public Result<Role> findRoleById(Long id) {
        if(id == null){
            LOGGER.error("查询角色对应用户列表参数id为空");
            return Result.fail("405","请求参数为空");
        }
        Role role = roleDao.findByRoleId(id);
        if(role == null){
            LOGGER.error("查询用户角色不存在,roleId:{}",id);
            return Result.fail("404","角色不存在!");
        }
        Long createUserId = role.getCreateBy();
        Long updateUserId = role.getUpdateBy();
        if(createUserId != null){
            User user = userDao.findUserById(createUserId);
            role.setCreateUser(user);
        }
        if(updateUserId != null){
            User user = userDao.findUserById(updateUserId);
            role.setUpdateUser(user);
        }
        return Result.ok(role);
    }

    @Override
    @Transactional
    public Result<Integer> deleteRoleById(Long id) {
        if(id == null){
            LOGGER.error("查询角色对应用户列表参数id为空");
            return Result.fail("405","请求参数为空");
        }
        Integer userRoleStatus = userRoleDao.deleteByRoleId(id);
        Integer roleMenuStatus = roleMenuDao.deleteByRoleId(id);
        LOGGER.info("删除用户角色状态:{},角色菜单状态:{}",userRoleStatus,roleMenuStatus);
        Integer status = roleDao.deleteByRoleId(id);
        if(status != 1){
            LOGGER.error("删除角色失败,roleId:{}",id);
//            return Result.fail("404","删除角色失败!");
            throw  new ResultException(ResultEnum.DELETE_ROLE_ERROR);
        }

        return Result.ok(status);
    }
}
