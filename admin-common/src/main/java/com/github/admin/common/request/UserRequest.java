package com.github.admin.common.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRequest implements Serializable {

//    private Integer status;

    private Integer pageNo = 1;

    private Integer pageSize = 1;

}
