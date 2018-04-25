package com.config;

import com.bean.Apple;
import com.bean.AppleFactoryBean;
import com.bean.Person;
import com.condition.LinuxCondition;
import com.condition.MyImportBeanDefinitionRegistrar;
import com.condition.MyImportSelector;
import org.springframework.context.annotation.*;

@PropertySource(value = {"classpath:init.properties"}) //加载外部配置文件
@Configuration
@ComponentScan("com.bean") //自动扫描包
@Import(value = {Person.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})  //导入组件
public class AnnotationBaseConfig {

    @Bean(initMethod = "initMethod",destroyMethod = "destroyMethod")
    @Conditional(LinuxCondition.class) //按照一定的条件进行判断，满足条件给容器中注册bean
    public Apple getAppleA() {
        Apple apple = new Apple();
        apple.setColor("${apple.color}");
        return apple;
    }

    @Bean
    public Apple getAppleB() {
        Apple apple = new Apple();
        apple.setSize(10);
        return apple;
    }

    @Bean //使用FactoryBean注册组件
    public AppleFactoryBean getAppleFactoryBean() {
        return new AppleFactoryBean();
    }
}