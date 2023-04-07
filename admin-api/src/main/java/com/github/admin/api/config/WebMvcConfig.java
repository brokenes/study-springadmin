package com.github.admin.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    /**
     * 解决 org.springframework.context.ApplicationContextException: Failed to start bean <br>
     * 'documentationPluginsBootstrapper'; nested exception is java.lang.NullPointerException
     * <a href="http://localhost:8179/swagger-ui/index.html#/">...</a>
     *
     * @param registry ResourceHandlerRegistry
     * @see <a href="https://www.mobaijun.com/posts/3051425539.html"></a>
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);
    }
//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(handlerInterceptor)
////                .addPathPatterns("/**")
////                .excludePathPatterns("/swagger**/**")
////                .excludePathPatterns("/webjars/**")
////                .excludePathPatterns("/v3/**")
////                .excludePathPatterns("/doc.html");
//
//        super.addInterceptors(registry);
//    }
}