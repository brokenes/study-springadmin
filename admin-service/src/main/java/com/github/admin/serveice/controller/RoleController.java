package com.github.admin.serveice.controller;

import com.github.admin.common.domain.Role;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@RestController
public class RoleController {

    @Resource
    private RoleService roleServiceImpl;

    @GetMapping("/existsUserOk")
    public Result<Boolean> existsUserOk(@RequestParam("userId")Long userId){
        return roleServiceImpl.existsUserOk(userId);
    }

    @GetMapping("/getUserOkRoleList")
    public Result<Set<Role>> getUserOkRoleList(@RequestParam("userId")Long userId){
        return  roleServiceImpl.getUserOkRoleList(userId);
    }

    @PostMapping("/findAllRole")
    public Result<List<Role>> findAllRole(){
        return  roleServiceImpl.findAllRole();
    }

}
