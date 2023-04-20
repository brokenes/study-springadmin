package com.github.admin.common.domain;

import com.github.framework.sensitive.annotation.SensitiveEntry;
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

    private Integer status;

    private String remark;
    private Date createDate;
    private Date updateDate;
    private Long createBy;
    private Long updateBy;

    private User createUser;
    private User updateUser;

    private Set<Menu> menus = new HashSet<Menu>();

    @SensitiveEntry
    private Set<User> users = new HashSet<User>();

}
