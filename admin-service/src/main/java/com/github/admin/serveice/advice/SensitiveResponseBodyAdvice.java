package com.github.admin.serveice.advice;

import com.alibaba.fastjson.JSON;
import com.github.admin.common.util.Result;
import com.github.framework.sensitive.annotation.Desensitization;
import com.github.framework.sensitive.annotation.Sensitive;
import com.github.framework.sensitive.annotation.SensitiveEntry;
import com.github.framework.sensitive.core.api.SensitiveUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 统一返回response
 *
 **/
@ControllerAdvice
@Slf4j
public class SensitiveResponseBodyAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse response) {

        try {
            returnType.getExecutable();
            Desensitization annotation  = returnType.getMethodAnnotation(Desensitization.class);
            Method method = returnType.getMethod();
            String methodName = method.getName();
            String clazzName = method.getDeclaringClass().getSimpleName();
            log.info("当前执行类名称:{},方法名称:{}",methodName,clazzName);
            if(annotation != null){
                if (body instanceof Result) {
                    Result result = (Result) body;
                    String code = result.getCode();
                    String msg = result.getMessage();
                    Object data = result.getData();
                    Object desCopy = SensitiveUtils.desCopy(data);
                    String simpleClassName = desCopy.getClass().getSimpleName();
                    result.setData(desCopy);
                    log.info(">>>>>>>>>>>>>>>>脱敏数据data:{}<<<<<<<<<<<<",JSON.toJSONString(desCopy));
                    return result;
                }
            }
            return body;
        } catch (Exception e) {
            log.error("globalResponseBodyAdvice exception:{}", e);
            //防止程序异常
            return body;
        }

    }

    //判断对象是否脱敏
    private boolean isDesensitization(Object data) {
        if(data != null) {
            if(data instanceof String) {
                return false;
            }
            Field[] fields = data.getClass().getDeclaredFields();
            for(Field field:fields) {
                SensitiveEntry sensitiveEntry = field.getAnnotation(SensitiveEntry.class);
                Sensitive sensitive = field.getAnnotation(Sensitive.class);
                if(sensitive != null || sensitiveEntry != null) {
                    log.info("<<<<<<<<<<<<<<<<对象存在脱敏>>>>>>>>>>>>>>>,simpleClassName={}",data.getClass().getSimpleName());
                    return true;
                }
            }
        }
        return false;
    }
}