package com.github.admin.serveice.controller;

import com.github.admin.common.domain.User;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {


    @Resource
    private UserService userServiceImpl;

    @GetMapping("/findByUserName")
    public Result<User> findByUserName(@RequestParam("userName") String userName){
        return userServiceImpl.findByUserName(userName);
    }


}
