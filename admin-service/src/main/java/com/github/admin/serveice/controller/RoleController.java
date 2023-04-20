package com.github.admin.serveice.controller;

import com.github.admin.common.domain.Role;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.RoleService;
import com.github.framework.sensitive.annotation.Desensitization;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/rolePage")
    public Result<DataPage<Role>> rolePage(@RequestBody RoleRequest roleRequest){
        return  roleServiceImpl.rolePage(roleRequest);
    }

    @PostMapping("/getUserListByRoleId/{id}")
    @Desensitization
    Result<Role> getUserListByRoleId(@PathVariable("id") Long id){
        return  roleServiceImpl.getUserListByRoleId(id);
    }

    @PostMapping("/findRoleById/{id}")
    Result<Role> findRoleById(@PathVariable("id")Long id){
        return  roleServiceImpl.findRoleById(id);
    }
    @PostMapping("/deleteRoleById/{id}")
    Result<Integer> deleteRoleById(@PathVariable("id")Long id){
        return  roleServiceImpl.deleteRoleById(id);
    }

    @PostMapping("/auth")
    Result<Integer> auth(@RequestParam("roleId")Long roleId,@RequestParam("authMenuIds") List<Long> authMenuIds){
        return  roleServiceImpl.auth(roleId,authMenuIds);
    }

    @PostMapping("/insertRole")
    Result<Integer> insertRole(@RequestBody Role role){
        return  roleServiceImpl.insertRole(role);
    }

    @PostMapping("/updatetRole")
    Result<Integer> updatetRole(@RequestBody Role role){
        return  roleServiceImpl.editRole(role);
    }
}
