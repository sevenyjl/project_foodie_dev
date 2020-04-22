package com.seven.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @ClassName ServiceLogAspect
 * @Description TODO
 * @Author seven
 * @Date 2020/4/8 15:35
 * @Version 1.0
 * 记录Service层的运行效率
 **/
@Aspect
@Component
@Slf4j
public class ServiceLogAspect {

    @Around("execution(* com.seven.service..impl..*.*(..))")
    private Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取当前执行的对象
        log.info("===== 开始执行 {}.{} =====",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());
        //获取到时间戳
        long start = System.currentTimeMillis();
        //执行目标方法
        Object proceed = joinPoint.proceed();
        //结束时间
        long end = System.currentTimeMillis();
        long runTime=end-start;
        //记录运行时间
        if (runTime>3000){
            log.error("===== 执行结束，耗时：{} 毫秒 =====",runTime);
        }else if(runTime>2000){
            log.warn("===== 执行结束，耗时：{} 毫秒 =====",runTime);
        }else {
            log.info("===== 执行结束，耗时：{} 毫秒 =====",runTime);
        }
        return proceed;
    }
}
