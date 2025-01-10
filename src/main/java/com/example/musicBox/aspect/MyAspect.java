package com.example.musicBox.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
    private static final Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Pointcut("@annotation(com.example.musicBox.aspect.MyLogger)")
    private void annotatedBy() {}

    @Around(value = "annotatedBy()")
    public Object aroundLogic(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        logger.info("Method execution start: " + start);
        Object proceed = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("Method execution end: " + end + " - Execution time: " + (end - start));
        return proceed;
    }
}
