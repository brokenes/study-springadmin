package com.github.admin.serveice.server;

import com.github.admin.common.domain.User;
import com.github.admin.common.util.Result;

public interface UserService {

    /**
     * 根据用户名称查询用户信息
     * @param userName  用户名称
     * @return
     */
    Result<User> findByUserName(String userName);
}