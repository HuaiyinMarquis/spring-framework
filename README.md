# Spring源码分析

## IOC容器初始化分析

​	在spring-test工程中的test代码块中我放置了自己写的测试代码，TestDemo.java中有测试各种场景的入口方法。下面我们以**AbstractApplicationContext#refresh()**方法为入口开始分析IOC的初始化，当然你可以根据我这里的说明详细跟进源代码具体流程。

* prepareRefresh(); 为刷新容器做准备
* obtainFreshBeanFactory();获取（刷新）BeanFactory
* prepareBeanFactory(beanFactory); 为将要使用的BeanFactory做前期准备
* postProcessBeanFactory(beanFactory);予许后置处理器对其子类进行处理
* invokeBeanFactoryPostProcessors(beanFactory);调用BeanFactory的后置处理器
* registerBeanPostProcessors(beanFactory);注册Bean的后置处理器，用于拦截Bean的创建
* initMessageSource();加载信息源
* initApplicationEventMulticaster();为上下文实例化事件多播器，用于事件的广播
* onRefresh();留与子类做扩展功能
* registerListeners();注册事件监听器，将之放入之间多播器
* finishBeanFactoryInitialization(beanFactory);完成非懒加载的单实例bean的实例化，这里涉及到AOP具体的实现原理，可以重点关注。
* finishRefresh();发布相应的事件

## AOP源码解析

​	具体的实现逻辑，你可以根据本文的提示在源码中找到我写的注释。有助于你对spring框架源码的理解

> **注册阶段--基于@EnableAspectJAutoProxy申明AOP注解为入口进行分析**
>
> * 通过注解内部实现我们可以查看到下面关键代码(有修改)，通过下面的代码将**AnnotationAwareAspectJAutoProxyCreator.class**交于Spring注册管理。
>
>   ``` java
>   beanDefinition = new RootBeanDefinition(AnnotationAwareAspectJAutoProxyCreator.class);
>
>   registry.registerBeanDefinition("org.springframework.aop.config.internalAutoProxyCreator", beanDefinition );
>
>   ```
>
> *  我们可以通过分析**AnnotationAwareAspectJAutoProxyCreator.class**源码可以得知其实现了**InstantiationAwareBeanPostProcessor**接口**BeanFactoryAware**接口和**BeanPostProcessor**接口。
>
> **注册阶段--在IOC容器初始化刷新时基于BeanPostProcessor的注册处理**
>
> ​	跟踪**AnnotationAwareAspectJAutoProxyCreator**的实例化流程：**refresh()->registerBeanPostProcessors()->beanFactory.getBean(ppName, BeanPostProcessor.class)->...参考IOC实例化单例bean的过程...->initializeBean->invokeAwareMethods(beanName, bean)->AbstractAdvisorAutoProxyCreator#setBeanFactory->AnnotationAwareAspectJAutoProxyCreator#initBeanFactory->...->beanFactory.addBeanPostProcessor(postProcessor)**
>
> **注册阶段--对自定义的单例Bean进行处理**
>
> ​	通过跟踪**refresh()->finishBeanFactoryInitialization(beanFactory)->beanFactory.preInstantiateSingletons()->getBean(beanName)->doGetBean(name..)->return createBean(beanName, mbd, args)->doCreateBean->applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName)->beanProcessor.postProcessAfterInitialization(result, beanName)->wrapIfNecessary(bean, beanName, cacheKey)->proxyFactory.getProxy(getProxyClassLoader())**，到这里作了创建代理对象以及注册增强器的处理。

> **调用阶段--拦截器的链式调用**
>
> ​	跟踪代码**spring-test模块中的com.TestDemo#test3方法student.innerMethod()**流程如下：
>
>  * 跳转到**org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor#intercept**方法继续执行
>  * 执行拦截器链**new CglibMethodInvocation(proxy, target, method, args, targetClass, chain, methodProxy).proceed()**
>
>   ​具体的连接器链调用流程代码里有详细的注释喔~
>
>   ​



