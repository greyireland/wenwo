package com.finalx.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by tengyu on 2016/7/13.
 */
@Component
@Aspect
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.finalx.controller.TestController.*(..))")
    public void beforeMethod(){
        logger.info("before index~~~~");
    }
    @After("execution(* com.finalx.controller.TestController.*(..))")
    public void afterMethod(){
        logger.info("after index~~~~");
    }
}
