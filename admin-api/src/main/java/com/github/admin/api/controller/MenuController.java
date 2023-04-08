package com.github.admin.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.admin.api.util.HttpServletUtil;
import com.github.admin.client.MenuServiceClient;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MenuController {


    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class);

    @Resource
    private MenuServiceClient menuServiceClient;

    @GetMapping("/main/system/menu/index")
    @RequiresPermissions("system:menu:index")
    public String index(Model model) {
        String search = HttpServletUtil.getRequest().getQueryString();
        model.addAttribute("search", search);
        return "/manager/menu/index";
    }


    @GetMapping("/system/menu/list")
    @RequiresPermissions("system:menu:index")
    @ResponseBody
    public ResultVo list(MenuRequest menuRequest) {
        Result<List<Menu>> result = menuServiceClient.findAllMenus(menuRequest);
        List<Menu> list = new ArrayList<>();
        if(result.isSuccess()){
            list = result.getData();
            LOGGER.info("查询菜单集合数据:{}", JSON.toJSONString(list));
            list.forEach(editMenu -> {
                String type = String.valueOf(editMenu.getType());
//                editMenu.setRemark(DictUtil.keyValue("MENU_TYPE", type));
                editMenu.setRemark(type);
            });
        }
        return ResultVoUtil.success(list);
    }
}
