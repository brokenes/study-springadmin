package com.github.admin.common.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuRequest implements Serializable {

    private Integer status;
    private String title;
    private String url;

}
