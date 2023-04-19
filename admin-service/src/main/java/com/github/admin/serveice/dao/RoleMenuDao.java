package com.github.admin.serveice.dao;

import com.github.admin.common.domain.RoleMenu;

import java.util.List;

public interface RoleMenuDao {

    //注意：一个参数可以省略@Param注解，当2个或者2个以上的
    //时候记得加上@Param注解
    public List<RoleMenu> findByRoleId(Long roleId);

    /**
     * 根据菜单id删除角色菜单
     * @param menuId
     * @return
     */
    Integer deleteByMenuId(Long menuId);

    Integer deleteByRoleId(Long id);
}
