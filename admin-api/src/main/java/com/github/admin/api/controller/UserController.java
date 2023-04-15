package com.github.admin.api.controller;

import com.alibaba.fastjson.JSON;
import com.github.admin.client.RoleServiceClient;
import com.github.admin.client.UserServiceCient;
import com.github.admin.common.constants.AdminConst;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.ResultEnum;
import com.github.admin.common.exception.ResultException;
import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.PasswordGroup;
import com.github.admin.common.group.UpdateGroup;
import com.github.admin.common.page.DataPage;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.util.Result;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.util.ShiroUtil;
import com.github.admin.common.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserServiceCient userSeviceCient;

    @Resource
    private RoleServiceClient roleServiceClient;



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
        String confirm = ShiroUtil.encrypt(userRequest.getConfirm(), salt);
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


    @GetMapping("/system/user/pwd")
    @RequiresPermissions("system:user:pwd")
    public String toEditPassword(@RequestParam(value = "ids", required = true)Long id,Model model) {
        model.addAttribute("id", id);
        return "/manager/user/pwd";
    }

    @PostMapping("/system/user/pwd")
    @RequiresPermissions("system:user:pwd")
    @ResponseBody
    public ResultVo editPassword(@Validated(value = PasswordGroup.class) UserRequest userRequest) {
        if (userRequest.getId() == AdminConst.ADMIN_ID) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }
        String salt = ShiroUtil.getRandomSalt();
        String encrypt = ShiroUtil.encrypt(userRequest.getPassword(), salt);
        String confirm = ShiroUtil.encrypt(userRequest.getConfirm(), salt);
        User user = new User();
        BeanUtils.copyProperties(userRequest,user);
        user.setPassword(encrypt);
        user.setSalt(salt);
        user.setConfirm(confirm);
        user.setUpdateDate(new Date());
        Result<Integer> result = userSeviceCient.updateUserPwd(user);
        if(result.isSuccess()){
            return ResultVoUtil.success("密码修改成功!");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }


    @GetMapping("/system/user/edit/{id}")
    @RequiresPermissions("system:user:edit")
    public String toEdit(@PathVariable(value = "id",required = true)Long id,Model model){
        Result<User> result = userSeviceCient.findUserById(id);
        if(result.isSuccess()){
            User user = result.getData();
            model.addAttribute("user",user);
        }
        return "/manager/user/edit";
    }

    @PostMapping("/system/user/edit")
    @RequiresPermissions("system:user:edit")
    @ResponseBody
    public ResultVo edit(@Validated(value = UpdateGroup.class) UserRequest userRequest){
        if (userRequest.getId() == AdminConst.ADMIN_ID) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }
        User user = new User();
        BeanUtils.copyProperties(userRequest,user);
        Result<Integer> result = userSeviceCient.updateUser(user);
        if(result.isSuccess()){
            return ResultVoUtil.success("用户改成功!");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

    @GetMapping("/system/user/role")
    @RequiresPermissions("system:user:role")
    public String toAuth(@RequestParam(value = "ids",required = true) Long id, Model model) {
        Result<User> result = userSeviceCient.findUserAndRoleById(id);
        Result<List<Role>> roleResult = roleServiceClient.findAllRole();
        if(result.isSuccess() && roleResult.isSuccess()){
            // 获取指定用户角色列表
            User user = result.getData();
            Set<Role> authRoles = user.getRoles();
            // 获取全部角色列表
            List<Role> list = roleResult.getData();
            model.addAttribute("id", user.getId());
            model.addAttribute("list", list);
            model.addAttribute("authRoles", authRoles);
        }
        return "/manager/user/role";
    }

    /**
     * 保存角色分配信息
     */
    @PostMapping("/system/user/role")
    @RequiresPermissions("system:user:role")
    @ResponseBody
    public ResultVo auth(
            @RequestParam(value = "id", required = true) Long id,
            @RequestParam(value = "roleId", required = true) List<Long> roleIds) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 不允许操作超级管理员数据
        if (AdminConst.ADMIN_ID == id && user.getId() != AdminConst.ADMIN_ID) {
            throw new ResultException(ResultEnum.NO_ADMIN_AUTH);
        }
        User userAuth = new User();
        userAuth.setId(id);
        Set<Role> roles = new HashSet<Role>();
        for (Long roleId:roleIds){
            Role role = new Role();
            role.setId(roleId);
            roles.add(role);
        }
        // 更新用户角色
        userAuth.setRoles(roles);
        // 保存数据
        Result<Integer> result = userSeviceCient.userAuth(userAuth);
        if(result.isSuccess()){
            return ResultVoUtil.success("授权成功");
        }else{
            String errMsg = result.getMessage();
            String code = result.getCode();
            return  ResultVoUtil.error(code,errMsg);
        }
    }

}
