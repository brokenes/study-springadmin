package com.github.admin.serveice.dao;

import com.github.admin.common.domain.Role;

public interface RoleDao {


    Role findByRoleId(Long roleId);
}
