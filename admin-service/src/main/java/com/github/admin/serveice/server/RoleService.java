package com.github.admin.serveice.server;

import com.github.admin.common.util.Result;

public interface RoleService {

    Result<Boolean> existsUserOk(Long userId);

}
