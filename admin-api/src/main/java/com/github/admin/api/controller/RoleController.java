package com.github.admin.api.controller;


import com.github.admin.client.MenuServiceClient;
import com.github.admin.client.RoleServiceClient;
import com.github.admin.common.constants.AdminConst;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class RoleController {


    @Resource
    private RoleServiceClient roleServiceClient;
    @Resource
    private MenuServiceClient menuServiceClient;

    @GetMapping("/main/system/role/index")
    @RequiresPermissions("system:role:index")
    public String index(Model model, RoleRequest roleRequest){
        Result<DataPage<Role>> result = roleServiceClient.rolePage(roleRequest);
        if(result.isSuccess()){
            DataPage<Role> dataPage = result.getData();
            model.addAttribute("list", dataPage.getDataList());
            model.addAttribute("page", dataPage);
        }
        return "/manager/role/index";
    }


    @GetMapping("/system/role/userList/{id}")
    @RequiresPermissions("system:role:userList")
    public String userList(@PathVariable("id")Long id,Model model){
        Result<Role> result = roleServiceClient.getUserListByRoleId(id);
        if(result.isSuccess()){
            Role role = result.getData();
            model.addAttribute("list", role.getUsers());
        }
        return "/manager/role/userList";
    }

    @GetMapping("/system/role/detail/{id}")
    @RequiresPermissions("system:role:detail")
    public String detail(@PathVariable("id")Long id,Model model){
        Result<Role> result = roleServiceClient.findRoleById(id);
        if(result.isSuccess()){
            Role role = result.getData();
            model.addAttribute("role",role);
        }
        return "/manager/role/detail";
    }


    @GetMapping("/system/role/delete/{id}")
    @RequiresPermissions("system:role:status")
    @ResponseBody
    public ResultVo delete(@PathVariable("id")Long id){
        Result<Integer> result = roleServiceClient.deleteRoleById(id);
        if(result.isSuccess()){
            return ResultVoUtil.success("删除成功！");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }


    @GetMapping("/system/role/auth")
    @RequiresPermissions("system:role:auth")
    public String auth(@RequestParam("ids")Long id,Model model){
        model.addAttribute("id",id);
        return "/manager/role/auth";
    }


    @GetMapping("/system/role/authList/{id}")
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    public ResultVo authList(@PathVariable("id")Long id){
        // 获取指定角色权限资源
        Result<Set<Menu>> authResult = menuServiceClient.getMenusByRoleId(id);
        // 获取全部菜单列表
        Result<List<Menu>> result = menuServiceClient.getListBySortOk();
        if(authResult.isSuccess() && result.isSuccess()){
            List<Menu> list = result.getData();
            Set<Menu> authMenus = authResult.getData();
            // 融合两项数据
            list.forEach(menu -> {
                if(authMenus.contains(menu)){
                    menu.setRemark("auth:true");
                }else {
                    menu.setRemark("");
                }
            });
            return ResultVoUtil.success(list);
        }else{
            String code = authResult.getCode();
            String errMsg = authResult.getMessage();
            return ResultVoUtil.error(code,errMsg);
        }
    }

    @PostMapping("/system/role/auth")
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    public ResultVo authList(@RequestParam("roleId")Long roleId,@RequestParam("authMenuIds")List<Long> authMenuIds){
        // 不允许操作管理员角色数据
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user == null || user.getId() != AdminConst.ADMIN_ID){
            throw new ResultException(ResultEnum.NO_ADMINROLE_AUTH);
        }

        Result<Integer> result = roleServiceClient.auth(roleId,authMenuIds);
        if(result.isSuccess()){
            return ResultVoUtil.success("授权成功!");
        }else{
            String code = result.getCode();
            String errMsg = result.getMessage();
            return ResultVoUtil.error(code,errMsg);
        }
    }


    @GetMapping("/system/role/add")
    @RequiresPermissions("system:role:add")
    public String toAdd(){
        return "/manager/role/add";
    }


    @PostMapping("/system/role/save")
    @RequiresPermissions("system:role:add")
    @ResponseBody
    public ResultVo add(@Validated(value = InsertGroup.class) RoleRequest roleRequest){
        Role role = new Role();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        BeanUtils.copyProperties(roleRequest,role);
        Date date = new Date();
        Long userId = user.getId();
        role.setCreateBy(userId);
        role.setUpdateBy(userId);
        role.setCreateDate(date);
        role.setUpdateDate(date);
        Result<Integer> result = roleServiceClient.insertRole(role);
        if(result.isSuccess()){
            return ResultVoUtil.success("角色添加成功!");
        }else {
            String code = result.getCode();
            String errMsg = result.getMessage();
            return ResultVoUtil.error(code,errMsg);
        }
    }


    @GetMapping("/system/role/edit/{id}")
    @RequiresPermissions("system:role:edit")
    public String toEdit(@PathVariable("id")Long id,Model model){
        Result<Role> result = roleServiceClient.findRoleById(id);
        if(result.isSuccess()){
            model.addAttribute("role",result.getData());
        }
        return "/manager/role/edit";
    }

    @PostMapping("/system/role/edit")
    @RequiresPermissions("system:role:edit")
    @ResponseBody
    public ResultVo edit(@Validated(value = UpdateGroup.class) RoleRequest roleRequest){
        Role role = new Role();
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        BeanUtils.copyProperties(roleRequest,role);
        Long userId = user.getId();
        role.setUpdateDate(new Date());
        role.setUpdateBy(userId);
        Result<Integer> result = roleServiceClient.updatetRole(role);
        if(result.isSuccess()){
            return ResultVoUtil.success("编辑角色成功!");
        }else {
            String code = result.getCode();
            String errMsg = result.getMessage();
            return ResultVoUtil.error(code,errMsg);
        }
    }
}
