package com.voligov.movieland.aspect;

import com.voligov.movieland.controller.MovieController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ControllerLogging {
    private final Logger log = LoggerFactory.getLogger(MovieController.class);

    @Around("execution(* com.voligov.movieland.controller.*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Received request to {}", joinPoint.getSignature().getName());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("Finished processing request to {}. It took {} ms", joinPoint.getSignature().getName(), System.currentTimeMillis() - startTime);
        return result;
    }
}
