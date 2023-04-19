package com.github.admin.api.controller;


import com.github.admin.client.RoleServiceClient;
import com.github.admin.common.constants.AdminConst;
import com.github.admin.common.domain.Role;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class RoleController {


    @Resource
    private RoleServiceClient roleServiceClient;

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
        if (AdminConst.ADMIN_ID == id) {
            throw new ResultException(ResultEnum.NO_ADMIN_STATUS);
        }
        Result<Integer> result = roleServiceClient.deleteRoleById(id);
        if(result.isSuccess()){
            return ResultVoUtil.success("删除成功！");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }
}
