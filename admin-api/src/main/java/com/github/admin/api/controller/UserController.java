package com.github.admin.api.controller;

import com.github.admin.client.UserServiceCient;
import com.github.admin.common.domain.User;
import com.github.admin.common.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserServiceCient userSeviceCient;

    @GetMapping(value = "/findByUserName")
    public Result<User> findByUserName(@RequestParam("userName")String userName){
        return userSeviceCient.findByUserName(userName);
    }

}
