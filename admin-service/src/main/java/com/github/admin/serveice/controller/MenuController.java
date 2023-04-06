package com.github.admin.serveice.controller;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
