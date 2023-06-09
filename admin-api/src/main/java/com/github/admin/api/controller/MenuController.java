package com.github.admin.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.admin.api.util.HttpServletUtil;
import com.github.admin.client.MenuServiceClient;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.User;
import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
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
import org.springframework.web.bind.annotation.*;

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


    @GetMapping({"/system/menu/add","/system/menu/add/{pid}"})
    @RequiresPermissions("system:menu:add")
    public String toAdd(@PathVariable(value = "pid",required = false)Long pid,Model model) {
        if(pid != null){
            Result<Menu> result = menuServiceClient.findMenuById(pid);
            if(result.isSuccess()){
                model.addAttribute("pMenu",result.getData());
            }
        }
        return "/manager/menu/add";
    }


    @PostMapping("/system/menu/save")
    @RequiresPermissions({"system:menu:add"})
    @ResponseBody
    public ResultVo save(@Validated(value = InsertGroup.class) MenuRequest menuRequest){
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


    @PostMapping("/system/menu/edit")
    @RequiresPermissions({"system:menu:edit"})
    @ResponseBody
    public ResultVo edit(@Validated(value = UpdateGroup.class) MenuRequest menuRequest){
        if(SecurityUtils.getSubject() == null || SecurityUtils.getSubject().getPrincipal() == null){
            throw new UnknownAccountException();
        }
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuRequest,menu);
        menu.setUpdateBy(userId);
        LOGGER.info("更新菜单对象属性复制后数据:{}",JSON.toJSONString(menu));
        Result<Integer> result = menuServiceClient.updateMenu(menu);
        if(result.isSuccess()){
            return ResultVoUtil.success("修改成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @GetMapping({"/system/menu/detail/{id}"})
    @RequiresPermissions("system:menu:detail")
    public String toDetail(@PathVariable("id")Long id,Model model) {
        Result<Menu> result = menuServiceClient.findMenuById(id);
        if(result.isSuccess()){
            Menu menu = result.getData();
            model.addAttribute("menu",menu);
        }
        return "/manager/menu/detail";
    }


    @GetMapping({"/system/menu/edit/{id}"})
    @RequiresPermissions("system:menu:add")
    public String toEdit(@PathVariable("id")Long id,Model model) {
        Result<Menu> result = menuServiceClient.findMenuById(id);
        if(result.isSuccess()){
            Menu menu = result.getData();
            model.addAttribute("menu",menu);
            model.addAttribute("pMenu",menu.getPMenu());
        }
        return "/manager/menu/edit";
    }



    @GetMapping("/system/menu/delete/{id}")
    @RequiresPermissions("system:menu:status")
    @ResponseBody
    public ResultVo delete(@PathVariable("id")Long id){
        Result<Integer> result = menuServiceClient.deleteMenuById(id);
        if(result.isSuccess()){
            return ResultVoUtil.success("删除成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @PostMapping("/system/menu/status/start")
    @RequiresPermissions("system:menu:status")
    @ResponseBody
    public ResultVo startStatus(@RequestParam("ids")List<Long> ids){
        List<Menu> list = new ArrayList<Menu>();
        for(Long id:ids){
            Menu menu = new Menu();
            menu.setId(id);
            menu.setStatus(1);
            list.add(menu);
        }
        Result<Integer> result = menuServiceClient.updateMenuStatus(list);
        if(result.isSuccess()){
            return ResultVoUtil.success("启用成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @PostMapping("/system/menu/status/stop")
    @RequiresPermissions("system:menu:status")
    @ResponseBody
    public ResultVo stopStatus(@RequestParam("ids")List<Long> ids){
        List<Menu> list = new ArrayList<Menu>();
        for(Long id:ids){
            Menu menu = new Menu();
            menu.setId(id);
            menu.setStatus(2);
            list.add(menu);
        }
        Result<Integer> result = menuServiceClient.updateMenuStatus(list);
        if(result.isSuccess()){
            return ResultVoUtil.success("停用成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }
}
