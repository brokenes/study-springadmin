package com.github.admin.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.admin.client.UserServiceCient;
import com.github.admin.common.constants.AdminConst;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.util.ShiroUtil;
import com.github.admin.common.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
            boolean isHasPrev = dataPage.isHasPrev();
            boolean isHasNext = dataPage.isHasNext();
            Integer pageNo = dataPage.getPageNo();
            Integer pageSize = dataPage.getPageSize();
            LOGGER.info("用户分页查询是否有上一页:{},下一页:{},当前第:{}页,每页显示:{}条数据",isHasPrev,isHasNext,pageNo,pageSize);
            model.addAttribute("list", dataPage.getDataList());
            model.addAttribute("page", dataPage);
        }
        return "/manager/user/index";
    }


    @RequestMapping("/system/user/status/start")
    @RequiresPermissions("system:user:status")
    @ResponseBody
    public ResultVo startStatus(@RequestParam(value = "ids", required = true) List<Long> ids) {
        // 不能修改超级管理员状态
        if (ids.contains(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_STATUS);
        }
        List<User> list = new ArrayList<User>();
        for(Long id:ids){
            User user = new User();
            user.setId(id);
            user.setStatus(1);
            list.add(user);
        }
        Result<Integer> result = userSeviceCient.updateUserStatus(list);
        if(result.isSuccess()){
            return ResultVoUtil.success("启用成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @RequestMapping("/system/user/status/stop")
    @RequiresPermissions("system:user:status")
    @ResponseBody
    public ResultVo stopStatus(@RequestParam(value = "ids", required = true) List<Long> ids) {
        // 不能修改超级管理员状态
        if (ids.contains(AdminConst.ADMIN_ID)) {
            throw new ResultException(ResultEnum.NO_ADMIN_STATUS);
        }
        List<User> list = new ArrayList<User>();
        for(Long id:ids){
            User user = new User();
            user.setId(id);
            user.setStatus(2);
            list.add(user);
        }
        Result<Integer> result = userSeviceCient.updateUserStatus(list);
        if(result.isSuccess()){
            return ResultVoUtil.success("停用成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @RequestMapping("/system/user/delete/{id}")
    @RequiresPermissions("system:user:status")
    @ResponseBody
    public ResultVo delete(@PathVariable( value = "id",required = true)Long id){
        if (id == AdminConst.ADMIN_ID) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }
        List<User> list = new ArrayList<User>();
        User user = new User();
        user.setId(id);
        user.setStatus(3);
        list.add(user);
        Result<Integer> result = userSeviceCient.updateUserStatus(list);
        if(result.isSuccess()){
            return ResultVoUtil.success("删除成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }

    }

    @RequestMapping("/system/user/add")
    @RequiresPermissions("system:user:add")
    public String toAdd(){
        return "/manager/user/add";
    }


    @PostMapping("/system/user/save")
    @RequiresPermissions("system:user:add")
    @ResponseBody
    public ResultVo add(@Validated(value= InsertGroup.class) UserRequest userRequest){
        LOGGER.info("添加用户请求参数:{}", JSON.toJSONString(userRequest));

        String salt = ShiroUtil.getRandomSalt();
        String encrypt = ShiroUtil.encrypt(userRequest.getPassword(), salt);
        String confirm = ShiroUtil.encrypt(userRequest.getPassword(), salt);
        User user = new User();
        BeanUtils.copyProperties(userRequest,user);
        user.setPassword(encrypt);
        user.setSalt(salt);
        user.setConfirm(confirm);
        Result<Integer> result = userSeviceCient.saveUser(user);
        if(result.isSuccess()){
            return ResultVoUtil.success("添加成功!");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }

    }


    @RequestMapping("/system/user/detail/{id}")
    @RequiresPermissions("system:user:detail")
    public String detail(@PathVariable(value = "id",required = true)Long id,Model model){
        Result<User> result = userSeviceCient.findUserById(id);
        if(result.isSuccess()){
            User user = result.getData();
            model.addAttribute("user",user);
        }
        return "/manager/user/detail";
    }

}
