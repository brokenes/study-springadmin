package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Role implements Serializable {

    private Long id;
    private String title;
    private String name;
    private String remark;
    private Date createDate;
    private Date updateDate;
    private Long createBy;
    private Long updateBy;
    private int status;



}
