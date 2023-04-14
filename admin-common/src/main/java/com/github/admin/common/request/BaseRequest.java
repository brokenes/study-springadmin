package com.github.admin.common.request;

import lombok.Data;
import java.io.Serializable;

@Data
public abstract class BaseRequest implements Serializable {

    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String orderByColumn;
    private String asc;



}
