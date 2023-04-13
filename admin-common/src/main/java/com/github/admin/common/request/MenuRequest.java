package com.github.admin.common.request;

import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MenuRequest implements Serializable {

    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    private Long id;
    //标题
    @NotBlank(message = "标题不能为空",groups = {InsertGroup.class,UpdateGroup.class})
    private String title;
    //请求url
    @NotBlank(message = "URL地址不能为空",groups = {InsertGroup.class,UpdateGroup.class})
    private String url;
    //权限标识
    @NotBlank(message = "权限标识不能为空",groups = {InsertGroup.class,UpdateGroup.class})
    private String perms;
    //父id
    @NotNull(message = "父级菜单不能为空",groups = {InsertGroup.class,UpdateGroup.class})
    private Long pid;
    //状态：1启用,2停用
    @NotNull(message = "菜单类型不能为空",groups = {InsertGroup.class,UpdateGroup.class})
    private Integer type;
    //菜单图标
    private String icon;
    //排序
    private Integer sort;
    //备注
    private String remark;
    @NotNull(message = "菜单状态不能为空",groups = {InsertGroup.class,UpdateGroup.class})
    private Integer status;

}
