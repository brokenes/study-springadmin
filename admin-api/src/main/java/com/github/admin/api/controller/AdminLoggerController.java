package com.github.admin.api.controller;

import com.github.admin.client.AdminLoggerServiceClient;
import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.admin.common.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class AdminLoggerController {

    @Resource
    private AdminLoggerServiceClient AdminLoggerServiceClient;

    @GetMapping("/main/system/actionLog/index")
    @RequiresPermissions("system:actionLog:index")
    public String index(AdminLoggerRequest adminLoggerRequest,Model model){
        Result<DataPage<AdminLogger>> result = AdminLoggerServiceClient.getPageList(adminLoggerRequest);
        if(result.isSuccess()){
            DataPage<AdminLogger> dataPage = result.getData();
            model.addAttribute("list",dataPage.getDataList());
            model.addAttribute("page",dataPage);
        }
        return "/manager/adminlogger/index";
    }
}
