package com.github.admin.api.controller;

import com.github.admin.client.MenuServiceClient;
import com.github.admin.client.RoleServiceClient;
import com.github.admin.common.constants.AdminConst;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.MenuTypeEnum;
import com.github.admin.common.enums.StatusEnum;
import com.github.admin.common.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private MenuServiceClient menuServiceClient;

    @Autowired
    private RoleServiceClient roleServiceClient;

    @GetMapping("/main")
    @RequiresPermissions("index")
    public String main(Model model){
        Result<List<Menu>> result = menuServiceClient.findAllMenus();
        // 判断是否拥有后台角色
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 封装菜单树形数据
        Map<Long, Menu> treeMenu = new HashMap<>();
        if(result.isSuccess()){
            // 菜单键值对(ID->菜单)
            Map<Long, Menu> keyMenu = new HashMap<>();
            // 管理员实时更新菜单
            if(user.getId() == AdminConst.ADMIN_ID){
                List<Menu> menus = result.getData();
                menus.forEach(menu -> keyMenu.put(menu.getId(), menu));
            }else{
                //TODO
                // 其他用户需从相应的角色中获取菜单资源
                Result<Set<Role>> roleResult = roleServiceClient.getUserOkRoleList(user.getId());
                if(roleResult.isSuccess()){
                    Set<Role> roles = roleResult.getData();
                    roles.forEach(role -> {
                        role.getMenus().forEach(menu -> {
                            if(menu.getStatus() == StatusEnum.OK.getCode()){
                                keyMenu.put(menu.getId(), menu);
                            }
                        });
                    });
                }
            }
            keyMenu.forEach((id, menu) -> {
                if(menu.getType() != MenuTypeEnum.BUTTON.getCode()){
                    if(keyMenu.get(menu.getPid()) != null){
                        keyMenu.get(menu.getPid()).getChildren().put(Long.valueOf(menu.getSort()), menu);
                    }else{
                        if(menu.getType() == MenuTypeEnum.DIRECTORY.getCode()){
                            treeMenu.put(Long.valueOf(menu.getSort()), menu);
                        }
                    }
                }
            });
        }
        model.addAttribute("user", user);
        model.addAttribute("treeMenu", treeMenu);
        return "main";
    }


    @GetMapping("/main/index")
    @RequiresPermissions("index")
    public String index(){
        return "/manager/main/index";
    }
}
