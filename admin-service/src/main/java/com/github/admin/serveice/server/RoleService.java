package com.github.admin.serveice.server;

import com.github.admin.common.domain.Role;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.common.util.Result;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Result<Boolean> existsUserOk(Long userId);

    Result<Set<Role>> getUserOkRoleList(Long userId);

    Result<List<Role>> findAllRole();

    Result<DataPage<Role>> rolePage(RoleRequest roleRequest);

    Result<Role> getUserListByRoleId(Long id);

    Result<Role> findRoleById(Long id);


    Result<Integer> deleteRoleById(Long id);

    Result<Integer> auth(Long roleId, List<Long> authMenuIds);


    Result<Integer> insertRole(Role role);

    Result<Integer> editRole(Role role);

}
