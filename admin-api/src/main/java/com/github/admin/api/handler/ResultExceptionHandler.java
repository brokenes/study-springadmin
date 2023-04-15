package com.github.admin.api.handler;

import com.github.admin.common.exception.ResultException;
import com.github.admin.common.util.ResultVoUtil;
import com.github.admin.common.vo.ResultVo;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



    private static final Logger LOGGER = LoggerFactory.getLogger(ResultExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVo handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("参数校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        LOGGER.error("handleMethodArgumentNotValidException方法参数异常信息:",ex);
        return ResultVoUtil.error("400", msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVo handleConstraintViolationException(ConstraintViolationException ex) {
        LOGGER.error("handleConstraintViolationException表单验证异常信息:",ex);
        return ResultVoUtil.error("400",ex.getMessage());
    }


    /** 拦截自定义异常 */
    @ExceptionHandler(ResultException.class)
    @ResponseBody
    public ResultVo resultException(ResultException ex){
        LOGGER.error("resultException系统自定义异常信息:",ex);
        return ResultVoUtil.error(ex.getCode(), ex.getMessage());
    }

    /** 拦截表单验证异常 */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResultVo bindException(BindException ex){
        BindingResult bindingResult = ex.getBindingResult();
        LOGGER.error("bindException表单验证异常信息:",ex);
        return ResultVoUtil.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
    }

    /** 拦截未知的运行时异常 */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultVo runtimeException(RuntimeException ex) {
//        ResultExceptionAdvice resultExceptionAdvice = SpringContextUtil.getBean(ResultExceptionAdvice.class);
//        resultExceptionAdvice.runtimeException(ex);
        LOGGER.error("runtimeException运行异常信息:",ex);
        return ResultVoUtil.error("500", "未知错误：EX4399");
    }

    /** 拦截未知的运行时异常 */
    @ExceptionHandler(FeignException.class)
    @ResponseBody
    public ResultVo feignException(FeignException ex) {
        String msg = ex.getMessage();
        String code = String.valueOf(ex.status());
        LOGGER.error("feignException远程接口调用异常信息:",ex);
        return ResultVoUtil.error(code, msg);
    }

    /**
     * 默认全局异常处理。
     * @param ex the ex
     * @return ResultData
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVo exception(Exception ex) {
        LOGGER.error("exception全局异常信息:",ex);
        return ResultVoUtil.error("500", "系统异常,请稍后再试!");
    }
}
