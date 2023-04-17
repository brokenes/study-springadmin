package com.github.admin.serveice.controller;

import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.AdminLoggerService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminLoggerController {

    @Resource
    private AdminLoggerService adminLoggerServiceImpl;

    @PostMapping("/getLogPageList")
    Result<DataPage<AdminLogger>> getPageList(@RequestBody AdminLoggerRequest adminLoggerRequest){
        return adminLoggerServiceImpl.page(adminLoggerRequest);
    }

    @PostMapping("/insertSelective")
    Result<Integer> insertSelective(@RequestBody AdminLogger adminLogger){
        return adminLoggerServiceImpl.insertSelective(adminLogger);
    }

    @PostMapping("/clearLogger")
    Result<Integer> clearLogger(){
        return adminLoggerServiceImpl.clearLogger();
    }

    @PostMapping("/deleteByPrimaryKey/{id}")
    Result<Integer> deleteByPrimaryKey(@PathVariable("id")Long id){
        return adminLoggerServiceImpl.deleteByPrimaryKey(id);
    }

    @PostMapping("/selectByPrimaryKey/{id}")
    Result<AdminLogger> selectByPrimaryKey(@PathVariable("id")Long id){
        return adminLoggerServiceImpl.selectByPrimaryKey(id);
    }


}
