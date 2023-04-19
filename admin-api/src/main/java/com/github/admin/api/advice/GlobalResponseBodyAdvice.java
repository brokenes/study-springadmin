package com.github.admin.api.advice;

import com.alibaba.fastjson.JSON;
import com.github.admin.common.vo.ResultVo;
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


/**
 * 统一返回response
 *
 **/
@ControllerAdvice
@Slf4j
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest serverHttpRequest, ServerHttpResponse response) {
       boolean objectType = body instanceof ResultVo;
       log.info("*******************{},数据类型:{}", JSON.toJSONString(body),objectType);
        try {
            if (body instanceof ResultVo ) {
                ResultVo resultVo = (ResultVo) body;
                String code = resultVo.getCode();
                String msg = resultVo.getMsg();
                Object data = resultVo.getData();
                if (isDesensitization(data)) {
                    Object obj = SensitiveUtils.desCopy(data);
                    String simpleClassName = obj.getClass().getSimpleName();
                    log.info(">>>>>>>>>>>>>>>>globalResponseBodyAdvice source data return code:{},msg:{},data:{}<<<<<<<<<<<<<<<<<simpleClassName={}", code, msg, obj, simpleClassName);
                }
                return body;
            }
//            //处理返回值
//            return ResultVoUtil.success(body);
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