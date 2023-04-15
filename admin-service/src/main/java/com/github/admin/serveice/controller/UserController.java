package com.github.admin.serveice.controller;

import com.github.admin.common.domain.User;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import com.github.admin.serveice.server.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {


    @Resource
    private UserService userServiceImpl;

    @GetMapping("/findByUserName")
    public Result<User> findByUserName(@RequestParam("userName") String userName){
        return userServiceImpl.findByUserName(userName);
    }

    @PostMapping("/getPageList")
    Result<DataPage<User>> getPageList(@RequestBody UserRequest userRequest){
        return userServiceImpl.getPageList(userRequest);
    }

    @PostMapping("/updateUserStatus")
    Result<Integer> updateUserStatus(@RequestBody List<User> list){
        return userServiceImpl.updateUserStatus(list);
    }
    @PostMapping("/saveUser")
    Result<Integer> saveUser(@RequestBody User user){
        return userServiceImpl.saveUser(user);
    }

    @PostMapping("/findUserById/{id}")
    Result<User> findUserById(@PathVariable("id") Long id){
        return userServiceImpl.findUserById(id);
    }
    @PostMapping("/updateUserPwd")
    Result<Integer> updateUserPwd(@RequestBody User user){
        return userServiceImpl.updateUserPwd(user);
    }

    @PostMapping("/updateUser")
    Result<Integer> updateUser(@RequestBody User user){
        return userServiceImpl.updateUser(user);
    }

    @PostMapping("/findUserAndRoleById/{id}")
    Result<User> findUserAndRoleById(@PathVariable("id")Long id){
        return userServiceImpl.findUserAndRoleById(id);
    }

    @PostMapping("/userAuth")
    Result<Integer> userAuth(@RequestBody User user){
        return userServiceImpl.userAuth(user);
    }
}
