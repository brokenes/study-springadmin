package com.github.admin.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MenuRequest implements Serializable {

    //标题
    @NotBlank(message = "标题不能为空")
    private String title;
    //请求url
    @NotBlank(message = "URL地址不能为空")
    private String url;

    //权限标识
    @NotBlank(message = "权限标识不能为空")
    private String perms;

    @NotNull(message = "父级菜单不能为空")
    private Long pid;

    //状态：1启用,2停用
    @NotNull(message = "菜单类型不能为空")
    private Integer type;
    //菜单图标
    private String icon;
    //排序
//    @NotNull(message = "排序不能为空")
    private Integer sort;

}
