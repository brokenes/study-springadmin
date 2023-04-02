package com.github.admin.common.util;

import java.io.Serializable;

public class Result<T> implements Serializable {

    public  static final  String SUCCESS_CODE = "0000";

    public  static final  String SUCCESS_MSG = "操作成功";

    public  static final String ERROR_CODE = "400";

    public  static final String ERROR_MESSAGE = "系统错误！";

    protected String code;

    protected String message;

    protected boolean success;

    protected  T data;



    public static Result ok(){
        return ok(null);
    }

    public  static <T> Result ok(final T data) {
        return ok(data,SUCCESS_CODE,SUCCESS_MSG);
    }

    private static <T> Result ok(T data, String code, String msg) {
        final  Result<T> result = new Result<T>();
        result.setData(data);
        result.setSuccess(true);
        result.setCode(code);
        result.setMessage(msg);
        return  result;
    }

    public static Result fail(){
        return fail(ERROR_CODE,ERROR_MESSAGE);
    }

    public static <T> Result<T> fail(String code, String msg) {
        final Result errorResult = new Result();
        errorResult.setCode(code);
        errorResult.setMessage(msg);
        errorResult.setSuccess(false);
        return errorResult;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static void main(String[] args) {
        Result errorResult = fail("401","系统错误");
        System.out.println(errorResult.message);
    }
}
