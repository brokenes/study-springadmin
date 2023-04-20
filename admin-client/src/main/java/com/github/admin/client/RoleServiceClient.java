package com.github.admin.client;

import com.github.admin.common.domain.Role;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@FeignClient(value="springboot-admin-service")
public interface RoleServiceClient {

    @GetMapping("/existsUserOk")
    public Result<Boolean> existsUserOk(@RequestParam("userId")Long userId);


    @GetMapping("/getUserOkRoleList")
    public Result<Set<Role>> getUserOkRoleList(@RequestParam("userId")Long userId);

    @PostMapping("/findAllRole")
    public Result<List<Role>> findAllRole();


    @PostMapping("/rolePage")
    public Result<DataPage<Role>> rolePage(@RequestBody RoleRequest roleRequest);

    @PostMapping("/getUserListByRoleId/{id}")
    Result<Role> getUserListByRoleId(@PathVariable("id") Long id);

    @PostMapping("/findRoleById/{id}")
    Result<Role> findRoleById(@PathVariable("id")Long id);

    @PostMapping("/deleteRoleById/{id}")
    Result<Integer> deleteRoleById(@PathVariable("id")Long id);

    @PostMapping("/auth")
    Result<Integer> auth(@RequestParam("roleId")Long roleId,@RequestParam("authMenuIds") List<Long> authMenuIds);


    @PostMapping("/insertRole")
    Result<Integer> insertRole(@RequestBody Role role);

    @PostMapping("/updatetRole")
    Result<Integer> updatetRole(@RequestBody Role role);

}
