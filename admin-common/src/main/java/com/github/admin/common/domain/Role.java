package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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

    private Set<Menu> menus = new HashSet<Menu>();

}
