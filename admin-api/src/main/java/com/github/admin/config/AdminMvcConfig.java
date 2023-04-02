package com.github.admin.config;

import com.github.admin.resolver.MyLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

@Configuration
public class AdminMvcConfig {


    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

}
