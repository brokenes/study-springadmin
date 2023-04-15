package com.github.admin.client;

import com.github.admin.common.domain.User;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value="springboot-admin-service")
public interface UserServiceCient {

    @GetMapping(value = "/findByUserName")
    public Result<User> findByUserName(@RequestParam("userName")String userName);


    @PostMapping("/getPageList")
    Result<DataPage<User>> getPageList(@RequestBody UserRequest userRequest);

    @PostMapping("/updateUserStatus")
    Result<Integer> updateUserStatus(@RequestBody  List<User> list);

    @PostMapping("/saveUser")
    Result<Integer> saveUser(@RequestBody User user);

    @PostMapping("/findUserById/{id}")
    Result<User> findUserById(@PathVariable("id") Long id);

    @PostMapping("/updateUserPwd")
    Result<Integer> updateUserPwd(@RequestBody User user);

    @PostMapping("/updateUser")
    Result<Integer> updateUser(@RequestBody User user);

    @PostMapping("/findUserAndRoleById/{id}")
    Result<User> findUserAndRoleById(@PathVariable("id")Long id);

    @PostMapping("/userAuth")
    Result<Integer> userAuth(@RequestBody User user);
}
