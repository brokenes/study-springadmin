package com.github.admin.common.vo;

import lombok.Data;

/**
 * 响应数据(结果)最外层对象
 * @author 小懒虫
 * @date 2018/10/15
 */
@Data
public class ResultVo<T> {

    /** 状态码 */
//    @ApiModelProperty(notes = "状态码（200成功、400错误）")
    private String code;

    /** 提示信息 */
//    @ApiModelProperty(notes = "提示信息")
    private String msg;

    /** 响应数据 */
//    @ApiModelProperty(notes = "响应数据")
    private T data;
}
