package com.github.admin.serveice.controller;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
public class MenuController {

    @Resource
    private MenuService menuServiceImpl;

    @GetMapping("/findAllMenus")
    public Result<List<Menu>> findAllMenus(){
        return menuServiceImpl.findAll();
    }
    @PostMapping("/findAllMenus")
    public Result<List<Menu>> findAllMenus(@RequestBody MenuRequest menuRequest){
        return menuServiceImpl.findAll(menuRequest);
    }

    @PostMapping("/findListByPidAndId/{pid}/{id}")
    public Result<List<Menu>> findListByPidAndId(@PathVariable("pid")Long pid, @PathVariable("id")Long id){
        return menuServiceImpl.findListByPidAndId(pid,id);
    }

    @PostMapping("/getSortMax/{pid}")
    public Result<Integer> getSortMax(@PathVariable("pid")Long pid){
        return menuServiceImpl.getSortMax(pid);
    }

    @PostMapping("/findMenuByPid/{pid}")
    public Result<Menu> findMenuByPid(@PathVariable("pid")Long pid){
        return menuServiceImpl.findMenuByPid(pid);
    }

    @PostMapping("/saveMenu")
    public Result<Integer> saveMenu(@RequestBody Menu menu){
        return menuServiceImpl.saveMenu(menu);
    }

    @PostMapping("/findMenuById/{id}")
    public Result<Menu> findMenuById(@PathVariable("id")Long id){
        return menuServiceImpl.findMenuById(id);
    }

    @PostMapping("/deleteMenuById/{id}")
    public Result<Integer> deleteMenuById(@PathVariable("id")Long id){
        return menuServiceImpl.deleteMenuById(id);
    }

    @PostMapping("/updateMenuStatus")
    public Result<Integer> updateMenuStatus(@RequestBody List<Menu> list){
        return menuServiceImpl.updateMenuStatus(list);
    }

    @PostMapping("/updateMenu")
    public Result<Integer> updateMenu(@RequestBody Menu menu){
        return menuServiceImpl.updateMenu(menu);
    }
}
