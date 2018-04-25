package com;

import com.bean.Apple;
import com.bean.Person;
import com.bean.Student;
import com.config.AnnotationAopConfig;
import com.config.AnnotationBaseConfig;
import com.config.AnnotationProfileConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public class TestDemo {

    @Test
    public void test1() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AnnotationBaseConfig.class);
        /******************************************************************************/
//        Apple apple = (Apple) applicationContext.getBean("getApple");
//        System.out.println(apple);
//        Map<String, Apple> beansOfType = applicationContext.getBeansOfType(Apple.class);
//        System.out.println(beansOfType);
        /******************************************************************************/
//        String[] definitionNames = applicationContext.getBeanDefinitionNames();
//        Stream<String> stream= Arrays.asList(definitionNames).stream();
//        stream.forEach(System.out::println);
        /******************************************************************************/
//        Object appleFactoryBean1 = applicationContext.getBean("getAppleFactoryBean");
//        Object appleFactoryBean2 = applicationContext.getBean("getAppleFactoryBean");
//        System.out.println("appleFactoryBean类型为："+appleFactoryBean1.getClass());
//        System.out.println("appleFactoryBean是否为单例："+(appleFactoryBean1==appleFactoryBean2));
//        Object appleFactoryBean = applicationContext.getBean("&getAppleFactoryBean");
//        System.out.println("&getAppleFactoryBean类型为："+appleFactoryBean.getClass());
        /******************************************************************************/
        Apple appleB = (Apple) applicationContext.getBean("getAppleB");
        System.out.println(appleB);
        applicationContext.close();
    }

    @Test
    public void test2() {
        /******************************************************************************/
//        -Dspring.profiles.active=personB
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AnnotationProfileConfig.class);
        /******************************************************************************/
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("personA","personB");
        applicationContext.register(AnnotationProfileConfig.class);
        applicationContext.refresh();
        /******************************************************************************/
        Map<String, Person> beans = applicationContext.getBeansOfType(Person.class);
        System.out.println(beans.keySet());
//        Person person = applicationContext.getBean(Person.class);
//        System.out.println(person);
        applicationContext.close();
    }

    @Test
    public void test3() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AnnotationAopConfig.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
//        System.out.println("--------------------------------------------------");
//        Arrays.asList(beanDefinitionNames).stream().forEach(System.out::println);
//        System.out.println("--------------------------------------------------");
        Student student = (Student) applicationContext.getBean("student");
        student.study(3);
        applicationContext.close();
    }
}
