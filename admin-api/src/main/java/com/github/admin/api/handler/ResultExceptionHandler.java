package com.github.admin.api.handler;

import com.github.admin.common.exception.ResultException;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.util.SpringContextUtil;
import com.github.admin.common.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Objects;


@ControllerAdvice
@Slf4j
public class ResultExceptionHandler {


    /**
     * 默认全局异常处理。
     * @param e the e
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVo exception(Exception e) {
        log.error("全局异常信息,exception:",e);
        return ResultVoUtil.error(500, "系统异常,请稍后再试!");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVo handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("参数校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        return ResultVoUtil.error(400, msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVo handleConstraintViolationException(ConstraintViolationException ex) {
        return ResultVoUtil.error(400,ex.getMessage());
    }


    /** 拦截自定义异常 */
    @ExceptionHandler(ResultException.class)
    @ResponseBody
    public ResultVo resultException(ResultException e){
        return ResultVoUtil.error(e.getCode(), e.getMessage());
    }

    /** 拦截表单验证异常 */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultVo bindException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        return ResultVoUtil.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /** 拦截未知的运行时异常 */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultVo runtimeException(RuntimeException e) {
        ResultExceptionAdvice resultExceptionAdvice = SpringContextUtil.getBean(ResultExceptionAdvice.class);
        resultExceptionAdvice.runtimeException(e);
        log.error("【系统异常】", e);
        return ResultVoUtil.error(500, "未知错误：EX4399");
    }
}
