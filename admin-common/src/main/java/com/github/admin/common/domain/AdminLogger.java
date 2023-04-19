package com.github.admin.common.domain;

import com.github.framework.sensitive.annotation.Sensitive;
import com.github.framework.sensitive.core.api.strategory.StrategyAccountNo;
import com.github.framework.sensitive.core.api.strategory.StrategyPassword;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminLogger implements Serializable {

    private Long id;

    private String description;

    @Sensitive(strategy = StrategyAccountNo.class)
    private String userName;

    private Date createDate;

    private Integer spendTime;

    private String basePath;

    private String uri;

    private String url;

    private String method;

    private String userAgent;

    private String ip;

    @Sensitive(strategy = StrategyPassword.class)
    private String parameter;

    private String statusCode;


}