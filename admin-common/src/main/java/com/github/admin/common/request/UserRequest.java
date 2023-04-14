package com.github.admin.common.request;

import lombok.Data;

@Data
public class UserRequest extends BaseRequest {

    private Integer status;
    private String userName;
    private String nickName;




}
