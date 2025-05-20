package org.example.taskstarter.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.taskstarter.properties.LoggingProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final LoggingProperties properties;

    public LoggingAspect(LoggingProperties properties) {
        this.properties = properties;
    }

    @Before("@annotation(org.example.taskstarter.annotations.LogBefore)")
    public void before(JoinPoint joinPoint) {
            logAccordingToLevel("Вызван метод {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(
            pointcut = "@annotation(org.example.taskstarter.annotations.LogAfterReturning)",
            returning = "result"
    )
    public void afterReturning(JoinPoint joinPoint, Object result) {
            logAccordingToLevel("Метод {} вернул {}", joinPoint.getSignature().getName(), result);
    }

    @AfterThrowing(
            pointcut = "@annotation(org.example.taskstarter.annotations.LogAfterThrowing)",
            throwing = "exception"
    )
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
            logger.error("В методе {} произошло исключение {}",
                    joinPoint.getSignature().getName(), exception.getClass());
    }

    @Around("@annotation(org.example.taskstarter.annotations.LogAround)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object proceeded = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logAccordingToLevel("Время выполнения {} - {} мс",
                joinPoint.getSignature().getName(), endTime - startTime);

        return proceeded;
    }

    private void logAccordingToLevel(String message, Object... args) {
        switch (properties.getLevel()) {
            case TRACE -> logger.trace(message, args);
            case DEBUG -> logger.debug(message, args);
            case INFO -> logger.info(message, args);
            case WARN -> logger.warn(message, args);
            case ERROR -> logger.error(message, args);
        }
    }
}