package com.github.admin.serveice.server;

import com.github.admin.common.domain.Role;
import com.github.admin.common.util.Result;

import java.util.Set;

public interface RoleService {

    Result<Boolean> existsUserOk(Long userId);

    Result<Set<Role>> getUserOkRoleList(Long userId);
}
