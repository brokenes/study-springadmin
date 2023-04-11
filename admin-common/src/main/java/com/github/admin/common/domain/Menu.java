package com.github.admin.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class Menu implements Serializable {

    private Long id;
    private String title;
    private Long pid;
    private String pids;
    private String url;
    private String perms;
    private String icon;
    private int type;
    private Integer sort;
    private String remark;
    private Date createDate;
    private Date updateDate;
    private Long createBy;
    private Long updateBy;
    private int status;

//    @JSONField(serialize = false)
    @JsonIgnore
    private Map<Long, Menu> children = new HashMap<>();


}
