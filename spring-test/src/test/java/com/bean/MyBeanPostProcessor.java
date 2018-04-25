package com.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == Apple.class)
            System.out.println("对象-"+beanName +":postProcessBeforeInitialization...");
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass() == Apple.class)
            System.out.println("对象-"+beanName +":postProcessAfterInitialization...");
        return bean;
    }
}
