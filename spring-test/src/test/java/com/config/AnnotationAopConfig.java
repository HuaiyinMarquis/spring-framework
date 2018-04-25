package com.config;

import com.aop.LogAspect;
import com.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy //开启基于注解的AOP模式
@Configuration
@ComponentScan("com.aop")
public class AnnotationAopConfig {

    @Bean("student")
    public Student getStudent() {
        return new Student();
    }

    @Bean
    public LogAspect getLogAspect() {
        return new LogAspect();
    }
}
