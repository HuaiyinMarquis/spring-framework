package com.config;

import com.aop.LogAspect;
import com.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 基于注解的AOP源码分析：
 * 1、给容器中注册AnnotationAwareAspectJAutoProxyCreator,对应容器的名称为：“org.springframework.aop.config.internalAutoProxyCreator” （AOP:stepAX ）
 * 		@EnableAspectJAutoProxy
 * 		->	@Import(AspectJAutoProxyRegistrar.class implements ImportBeanDefinitionRegistrar)
 * 2、AnnotationAwareAspectJAutoProxyCreator源码分析(AUTO_PROXY_CREATOR_BEAN_NAME = "org.springframework.aop.config.internalAutoProxyCreator")：
 * 		AnnotationAwareAspectJAutoProxyCreator
 * 		 extends AspectJAwareAdvisorAutoProxyCreator
 * 		  extends AbstractAdvisorAutoProxyCreator
 * 		   extends AbstractAutoProxyCreator
 * 		    extends ProxyProcessorSupport implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 * 		     extends ...InstantiationAwareBeanPostProcessor Ordered
 * 		    这里实现了BeanPostProcessor子接口（关注Bean初始化）和BeanFactoryAware接口（类似ApplicationContextAware可以获取IOC容器）
 * 3、断点类方法：
 * 		org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)
 * 		 (delegate)->org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator#setBeanFactory(org.springframework.beans.factory.BeanFactory)//传入BeanFactory的方法
 * 		  	->org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator#initBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
 * 		org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#postProcessBeforeInitialization(java.lang.Object, java.lang.String)//前置处理器方法
 * 		org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#postProcessAfterInitialization(java.lang.Object, java.lang.String)//后置处理器方法
 * 4、容器初始化源码分析：（IOC:stepAX）
 * 		org.springframework.context.annotation.AnnotationConfigApplicationContext#AnnotationConfigApplicationContext(java.lang.Class[]) //初始化IOC容器入口
 *		 ->registerBeanPostProcessors(beanFactory)
 *		  ->org.springframework.context.support.PostProcessorRegistrationDelegate#registerBeanPostProcessors(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, org.springframework.context.support.AbstractApplicationContext)
 *		   ->BeanPostProcessor pp = beanFactory.getBean(ppName, BeanPostProcessor.class);//实例化internalAutoProxyCreator
 *		    ->registerBeanPostProcessors(beanFactory, orderedPostProcessors); //注册到BeanFactory
 *		 ->finishBeanFactoryInitialization(beanFactory);
 *		  ->beanFactory.preInstantiateSingletons();
 *		   ->org.springframework.beans.factory.support.AbstractBeanFactory#doGetBean(java.lang.String, java.lang.Class, java.lang.Object[], boolean)
 *		    ->return createBean(beanName, mbd, args);
 *		     ->Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
 *		      ->org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator#postProcessBeforeInstantiation(java.lang.Class, java.lang.String)
 *		       ->Object proxy = createProxy(beanClass, beanName, specificInterceptors, targetSource);
 *		        ->
 *
 */
@EnableAspectJAutoProxy //AOP:stepA1 开启基于注解的AOP模式
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
