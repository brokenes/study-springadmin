package com.github.admin.serveice.controller;

import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;


@RestController
public class RoleController {

    @Resource
    private RoleService roleServiceImpl;

    @GetMapping("/existsUserOk")
    public Result<Boolean> existsUserOk(@RequestParam("userId")Long userId){
        return roleServiceImpl.existsUserOk(userId);
    }


}
