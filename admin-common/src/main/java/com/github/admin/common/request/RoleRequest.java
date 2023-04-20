package com.github.admin.common.request;

import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class RoleRequest extends BaseRequest{

    @NotNull(message = "角色id",groups = {UpdateGroup.class})
    private Long id;
    @NotEmpty(message = "角色编号",groups = {InsertGroup.class, UpdateGroup.class})
    private String title;
    @NotEmpty(message = "角色名称",groups = {InsertGroup.class, UpdateGroup.class})
    private String name;
    @NotNull(message = "角色名称",groups = {InsertGroup.class, UpdateGroup.class})
    private Integer status;
    private String remark;
    private Date createDate;
    private Date updateDate;
    private Long createBy;
    private Long updateBy;

}
