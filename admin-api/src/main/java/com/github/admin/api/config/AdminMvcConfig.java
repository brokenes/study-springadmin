package com.github.admin.api.config;

import com.github.admin.api.resolver.MyLocaleResolver;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class AdminMvcConfig {


    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

//    @Bean
//    public LocalValidatorFactoryBean getValidatorFactory() {
//        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
//        localValidatorFactoryBean.getValidationPropertyMap().put("hibernate.validator.fail_fast", "true");
//        return localValidatorFactoryBean;
//    }

    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class )
                .configure()
                .failFast(true)
                //.addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator;
    }
}
