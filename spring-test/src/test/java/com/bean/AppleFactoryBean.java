package com.bean;

import org.springframework.beans.factory.FactoryBean;

/**
 * 使用FactoryBean来注册组件
 */
public class AppleFactoryBean implements FactoryBean<Apple> {
    @Override
    public Apple getObject() throws Exception {
        return new Apple();
    }

    @Override
    public Class<?> getObjectType() {
        return Apple.class;
    }

    /**
     *  返回true是单例
     *  返回false是多实例
     */
    @Override
    public boolean isSingleton() {
        return true;
    }
}
