package com.bean;

import lombok.Data;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

@Data
public class Apple implements InitializingBean,DisposableBean {
    @Value("${apple.color}")
    private String color;
    @Value("#{3+2}")
    private Integer size;

    public void setColor(String color) {
        System.out.println("-----------------setColor()------------------");
        this.color = color;
    }

    public Apple() {
        System.out.println("Apple Class类正在创建对象...");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("调用接口方法：afterPropertiesSet");
    }

    public void destroy() throws Exception {
        System.out.println("调用接口方法：destroy");
    }

    public void initMethod() {
        System.out.println("正在执行initMethod");
    }

    public void destroyMethod() {
        System.out.println("正在执行destroyMethod");
    }
}
