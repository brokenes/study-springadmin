package com.github.admin.serveice.dao;

import com.github.admin.common.domain.Role;

import java.util.List;
import java.util.Map;

public interface RoleDao {


    Role findByRoleId(Long roleId);

    List<Role> findAllRole();

    Long pageListCount(Map<String, Object> map);

    List<Role> pageList(Map<String, Object> map);

    Integer deleteByRoleId(Long id);

    Integer insertSelective(Role role);

    Integer updateByPrimaryKeySelective(Role role);
}
