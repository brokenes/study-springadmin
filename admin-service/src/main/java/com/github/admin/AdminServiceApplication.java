package com.github.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 * @MapperScan 注释会自动扫码指定包路径下的对象，让spring容器管理
 * 如果在启动类里加里@MapperScan注解，在xxxxDao对象不需要再加@Maper注解
 */
@SpringBootApplication
@MapperScan("com.github.admin.serveice.dao")
@EnableDiscoveryClient
@EnableTransactionManagement
public class AdminServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AdminServiceApplication.class,args);
        LOGGER.error("************AdminServiceApplication项目启动成功************");
    }
}
