package com.github.admin.client;

import com.github.admin.common.domain.Role;
import com.github.admin.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value="springboot-admin-service")
public interface RoleServiceClient {

    @GetMapping("/existsUserOk")
    public Result<Boolean> existsUserOk(@RequestParam("userId")Long userId);


    @GetMapping("/getUserOkRoleList")
    public Result<Set<Role>> getUserOkRoleList(@RequestParam("userId")Long userId);

}
