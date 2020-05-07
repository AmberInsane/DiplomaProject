package com.tms.stankevich.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.ArrayList;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AopConfiguration {

    private final Logger logger = LogManager.getLogger(AopConfiguration.class.getName());

    @Pointcut("@annotation(com.tms.stankevich.annotations.Loggable)")
    public void loggableMonitor() {
    }

    @Around("loggableMonitor()")
    public Object loggableMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature invocation = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = invocation.getMethod().getDeclaringClass().getName();
        String name = invocation.getMethod().getName();
        Object returnObject = null;
        long start = System.currentTimeMillis();
        try {
            returnObject = proceedingJoinPoint.proceed();
            return returnObject;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            if (name.equals("loadMovies")) {
                logger.debug("className " + className + " method: " + name + " found new movies: " + ((ArrayList)returnObject).size() + " time: " + time);
            }else {
                logger.debug("className " + className + " method: " + name + " time: " + time);
            }
        }
        return null;
    }
}
