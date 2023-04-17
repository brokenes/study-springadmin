package com.github.admin.client;

import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.admin.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="springboot-admin-service")
public interface AdminLoggerServiceClient {


    @PostMapping("/getLogPageList")
    Result<DataPage<AdminLogger>> getPageList(@RequestBody AdminLoggerRequest adminLoggerRequest);

    @PostMapping("/insertSelective")
    Result<Integer> insertSelective(@RequestBody AdminLogger adminLogger);

    @PostMapping("/clearLogger")
    Result<Integer> clearLogger();

    @PostMapping("/deleteByPrimaryKey/{id}")
    Result<Integer> deleteByPrimaryKey(@PathVariable("id")Long id);

    @PostMapping("/selectByPrimaryKey/{id}")
    Result<AdminLogger> selectByPrimaryKey(@PathVariable("id")Long id);

}
