package com.github.admin.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.admin.api.util.HttpServletUtil;
import com.github.admin.client.MenuServiceClient;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.User;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

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
                int menuType = editMenu.getType();
                String remark = "-";
                if(menuType == 1){
                    remark = "目录";
                }else if(menuType == 2){
                    remark = "菜单";
                }else if(menuType == 3){
                    remark = "按钮";
                }
                editMenu.setRemark(remark);
            });
        }
        return ResultVoUtil.success(list);
    }


    /**
     * 获取排序菜单列表
     */
    @GetMapping("/system/menu/sortList/{pid}/{notId}")
    @RequiresPermissions({"system:menu:add", "system:menu:edit"})
    @ResponseBody
    public Map<Integer, String> sortList(
            @PathVariable(value = "pid", required = false) Long pid,
            @PathVariable(value = "notId", required = false) Long notId){
        // 本级排序菜单列表
        notId = notId != null ? notId : (long) 0;
        Result<List<Menu>> result = menuServiceClient.findListByPidAndId(pid,notId);
        Map<Integer, String> sortMap = new TreeMap<>();
        if(result.isSuccess()){
            List<Menu> levelMenu = result.getData();
            for (int i = 1; i <= levelMenu.size(); i++) {
                sortMap.put(i, levelMenu.get(i - 1).getTitle());
            }
        }
        return sortMap;
    }


    @GetMapping({"/system/menu/add"})
    @RequiresPermissions("system:menu:add")
    public String toAdd() {
        return "/manager/menu/add";
    }


    @PostMapping("/system/menu/save")
//    @RequiresPermissions({"system:menu:add", "system:menu:edit"})
    @RequiresPermissions({"system:menu:add"})
    @ResponseBody
    public ResultVo save(@Validated MenuRequest menuRequest){
        LOGGER.info("菜单添加请求参数:{}",menuRequest);
        if(SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getPrincipal() == null){
              throw new UnknownAccountException();
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuRequest,menu);
        menu.setCreateBy(userId);
        menu.setUpdateBy(userId);
        LOGGER.info("保存菜单对象属性复制后数据:{}",JSON.toJSONString(menu));
        Result<Integer> result = menuServiceClient.saveMenu(menu);
        if(result.isSuccess()){
            return ResultVoUtil.SAVE_SUCCESS;
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }



}
