package com.encrypt.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.encrypt")
@PropertySource(value="classpath:prop.properties",encoding="UTF-8")
public class SpringJavaConfig {

}
