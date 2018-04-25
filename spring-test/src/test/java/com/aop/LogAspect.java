package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

@Aspect
public class LogAspect {

    @Pointcut("execution(public int com.bean.Student.*(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        System.out.println("logStart...args:{"+Arrays.asList(joinPoint.getArgs())+"}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println("logEnd...");
    }

    @AfterReturning(value = "pointCut()",returning = "result")
    public void logReturn(Object result) {
        System.out.println("logReturn...result:{"+result+"}");
    }

    @AfterThrowing(value = "pointCut()",throwing = "exception")
    public void logException(Exception exception) {
        System.out.println("logException...ex:{"+exception.getMessage()+"}");
    }

//    @Around("pointCut()")
//    public void logAround(ProceedingJoinPoint pjp) throws Throwable {
//    }
}
