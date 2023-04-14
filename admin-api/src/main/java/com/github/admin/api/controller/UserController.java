package com.github.admin.api.controller;

import com.github.admin.client.UserServiceCient;
import com.github.admin.common.domain.User;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserServiceCient userSeviceCient;



    @GetMapping("/main/system/user/index")
    @RequiresPermissions("system:user:index")
    public String index(Model model, UserRequest userRequest) {

        // 获取用户列表
        Result<DataPage<User>> result = userSeviceCient.getPageList(userRequest);
        if(result.isSuccess()){
            DataPage<User> dataPage = result.getData();
            LOGGER.info("是否有上一页:{},下一页:{}",dataPage.isHasPrev(),dataPage.isHasNext());
            model.addAttribute("list", dataPage.getDataList());
            model.addAttribute("page", dataPage);
        }
        // 封装数据

//        model.addAttribute("dept", user.getDept());
        return "/manager/user/index";
    }



    @GetMapping(value = "/findByUserName")
    public Result<User> findByUserName(@RequestParam("userName")String userName){
        return userSeviceCient.findByUserName(userName);
    }

}
