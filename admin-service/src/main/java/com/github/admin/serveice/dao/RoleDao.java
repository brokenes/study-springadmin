package com.github.admin.serveice.dao;

import com.github.admin.common.domain.Role;

import java.util.List;

public interface RoleDao {


    Role findByRoleId(Long roleId);

    List<Role> findAllRole();
}
