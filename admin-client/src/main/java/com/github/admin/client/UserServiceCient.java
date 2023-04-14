package com.github.admin.client;

import com.github.admin.common.domain.User;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="springboot-admin-service")
public interface UserServiceCient {

    @GetMapping(value = "/findByUserName")
    public Result<User> findByUserName(@RequestParam("userName")String userName);


    @PostMapping("/getPageList")
    Result<DataPage<User>> getPageList(@RequestBody UserRequest userRequest);
}
