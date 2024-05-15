package ru.katiafill.bookings.aircraft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.stereotype.Service *)")
    public void springBeanPointcuts() {}

    @AfterThrowing(pointcut = "springBeanPointcuts()", throwing = "e")
    public void errorLogging(JoinPoint joinPoint, Throwable e) {
        log.error("Exception in {}.{}() with cause: {}, {}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                e.getLocalizedMessage(),
                e.getStackTrace());
    }

    @Around("springBeanPointcuts()")
    public Object aroundMethodLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Enter: {}.{}() with argument[s] = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));

        Object result = joinPoint.proceed();

        log.info("Exit: {}.{}() with result = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);

        return result;
    }
}
