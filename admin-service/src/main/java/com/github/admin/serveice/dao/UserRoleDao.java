package com.github.admin.serveice.dao;

import com.github.admin.common.domain.UserRole;

import java.util.List;


public interface UserRoleDao {

    List<UserRole> findByUserId(Long userId);


    Integer deleteByUserId(Long userId);


    Integer save(UserRole userRole);
}
