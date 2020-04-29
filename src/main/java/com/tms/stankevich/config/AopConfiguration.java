package com.tms.stankevich.config;

import com.tms.stankevich.сontroller.AdminController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AopConfiguration {

    private final Logger logger = LogManager.getLogger(AopConfiguration.class.getName());
    private final Logger loggerController = LogManager.getLogger(AdminController.class.getName());

    @Pointcut("within(com.tms.stankevich.сontroller.*)")
    public void controllerMonitor() {
    }

    @Pointcut("@annotation(com.tms.stankevich.annotations.Loggable)")
    public void loggableMonitor() {
    }

    @Around("loggableMonitor()")
    public Object loggableMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature invocation = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = invocation.getMethod().getDeclaringClass().getName();
        String name = invocation.getMethod().getName();
        long start = System.currentTimeMillis();
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            logger.debug("className " + className + " method: " + name + " time: " + time);
        }
        return null;
    }

    @Around("controllerMonitor()")
    public Object controllerMethod(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature invocation = (MethodSignature) proceedingJoinPoint.getSignature();
        String name = invocation.getMethod().getName();

        long start = System.currentTimeMillis();
        Object returnObject = null;
        try {
            returnObject = proceedingJoinPoint.proceed();
            return returnObject;
        } catch (Throwable throwable) {
            logger.error("ThrowableError " + throwable.getMessage());
            return null;
        } finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            loggerController.debug("input: " + Arrays.toString(proceedingJoinPoint.getArgs()) + " method: " + name + " time: " + time + " output:" + returnObject);
        }
    }
}
