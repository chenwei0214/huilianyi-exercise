package com.cloudhelios.atlantis.aop;

import com.cloudhelios.atlantis.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * author: chenwei
 * createDate: 18-8-27 下午7:23
 * description:
 */
@Component
@Aspect
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    @Pointcut(value = "execution(* com.cloudhelios.atlantis.controller.*.*(..))")
    public void myPointcut() {
    }


    @Around(value = "myPointcut()")
    public Object myAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String jsonArgs = JsonUtil.toJson(joinPoint.getArgs());
        logger.info("args:{}({}),",joinPoint.getSignature(), jsonArgs);
        //手动执行目标方法
        Object result = joinPoint.proceed();
        String jsonResult = JsonUtil.toJson(result);
        logger.info("result:{}", jsonResult);
        return result;
    }
}
