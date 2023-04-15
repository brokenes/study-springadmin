package com.github.admin.common.request;

import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.PasswordGroup;
import com.github.admin.common.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class UserRequest extends BaseRequest {

    @NotNull(message = "用户id不能为空",groups = {UpdateGroup.class, PasswordGroup.class})
    private Long id;

    @NotNull(message = "启用状态不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    @Range(message = "启用状态范围为 {min} 到 {max} 之间", min = 1,max = 2,groups = {InsertGroup.class, UpdateGroup.class})
    private Integer status;

    @NotBlank(message = "用户名不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    @Length(message = "用户名称围为 {min} 到 {max} 之间", min = 3,max = 12,groups = {InsertGroup.class, UpdateGroup.class})
    private String userName;

    @NotBlank(message = "用户昵称不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    @Length(message = "用户昵称范围为 {min} 到 {max} 之间", min = 3,max = 12,groups = {InsertGroup.class, UpdateGroup.class})
    private String nickName;

    @NotBlank(message = "密码不能为空",groups = {InsertGroup.class, PasswordGroup.class})
    @Length(message = "密码围为 {min} 到 {max} 之间", min = 3,max = 12,groups = {InsertGroup.class, PasswordGroup.class})
    private String password;

    @NotBlank(message = "确认密码不能为空",groups = {InsertGroup.class, PasswordGroup.class})
    @Length(message = "确认密码围为 {min} 到 {max} 之间", min = 3,max = 12,groups = {InsertGroup.class, PasswordGroup.class})
    private String confirm;

    @NotBlank(message = "手机号码不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    @Pattern(regexp = "^1[345678]\\d{9}$",message = "手机号码格式错误",groups = {InsertGroup.class, UpdateGroup.class})
    private String phone;

    @NotBlank(message = "邮箱不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    @Email(message = "邮箱格式错误",groups = {InsertGroup.class, UpdateGroup.class})
    private String email;

    @NotNull(message = "性别不能为空",groups = {InsertGroup.class, UpdateGroup.class})
    @Range(message = "性别范围为 {min} 到 {max} 之间", min = 1,max = 2,groups = {InsertGroup.class, UpdateGroup.class})
    private Integer sex;

    private String remark;

    private Date createDate;

    private Date updateDate;



}
