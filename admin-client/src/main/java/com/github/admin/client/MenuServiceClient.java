package com.github.admin.client;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="springboot-admin-service")
public interface MenuServiceClient {

    @GetMapping("/findAllMenus")
    public Result<List<Menu>> findAllMenus();


    @PostMapping("/findAllMenus")
    public Result<List<Menu>> findAllMenus(@RequestBody MenuRequest menuRequest);

}
