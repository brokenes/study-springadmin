package com.github.admin.api.controller;

import com.github.admin.client.AdminLoggerServiceClient;
import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.AdminLoggerRequest;
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
public class AdminLoggerController {

    @Resource
    private AdminLoggerServiceClient adminLoggerServiceClient;

    @GetMapping("/main/system/actionLog/index")
    @RequiresPermissions("system:actionLog:index")
    public String index(AdminLoggerRequest adminLoggerRequest,Model model){
        Result<DataPage<AdminLogger>> result = adminLoggerServiceClient.getPageList(adminLoggerRequest);
        if(result.isSuccess()){
            DataPage<AdminLogger> dataPage = result.getData();
            model.addAttribute("list",dataPage.getDataList());
            model.addAttribute("page",dataPage);
        }
        return "/manager/adminlogger/index";
    }

    @RequiresPermissions("system:actionLog:delete")
    @GetMapping("/system/actionLog/delete")
    @ResponseBody
    public ResultVo clear(){
        Result<Integer> result = adminLoggerServiceClient.clearLogger();
        if(result.isSuccess()){
            return ResultVoUtil.success("操作成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @RequiresPermissions("system:actionLog:status")
    @GetMapping("/system/actionLog/delete/{id}")
    @ResponseBody
    public ResultVo delete(@PathVariable("id")Long id){
        Result<Integer> result = adminLoggerServiceClient.deleteByPrimaryKey(id);
        if(result.isSuccess()){
            return ResultVoUtil.success("操作成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @RequiresPermissions("system:actionLog:detail")
    @GetMapping("/system/actionLog/detail/{id}")
    public String detail(@PathVariable("id")Long id,Model model){
        Result<AdminLogger> result = adminLoggerServiceClient.selectByPrimaryKey(id);
        if(result.isSuccess()){
            model.addAttribute("adminLog",result.getData());
        }
        return "/manager/adminlogger/detail";
    }

}
