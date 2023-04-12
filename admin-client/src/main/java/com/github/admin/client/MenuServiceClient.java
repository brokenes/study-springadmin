package com.github.admin.client;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value="springboot-admin-service")
public interface MenuServiceClient {

    @GetMapping("/findAllMenus")
    public Result<List<Menu>> findAllMenus();


    @PostMapping("/findAllMenus")
    public Result<List<Menu>> findAllMenus(@RequestBody MenuRequest menuRequest);


    @PostMapping("/findListByPidAndId/{pid}/{id}")
    public Result<List<Menu>> findListByPidAndId(@PathVariable("pid")Long pid,@PathVariable("id")Long id);


    @PostMapping("/getSortMax/{pid}")
    public Result<Integer> getSortMax(@PathVariable("pid")Long pid);

    @PostMapping("/findMenuByPid/{pid}")
    public Result<Menu> findMenuByPid(@PathVariable("pid")Long pid);

    @PostMapping("/saveMenu")
    public Result<Integer> saveMenu(@RequestBody Menu menu);


    @PostMapping("/findMenuById/{id}")
    public Result<Menu> findMenuById(@PathVariable("id")Long id);


    @PostMapping("/deleteMenuById/{id}")
    public Result<Integer> deleteMenuById(@PathVariable("id")Long id);


    @PostMapping("/updateMenuStatus")
    public Result<Integer> updateMenuStatus(@RequestBody List<Menu> list);
}
