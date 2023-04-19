package com.github.admin.common.request;

import lombok.Data;

@Data
public class RoleRequest extends BaseRequest{

    private Integer status;
    private String name;
    private String title;
}
