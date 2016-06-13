package com.voligov.movieland.aspect;

import com.voligov.movieland.dao.MovieDao;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MovieDaoLogging {
    private final Logger log = LoggerFactory.getLogger(MovieDao.class);

    @Around("execution(* com.voligov.movieland.dao.MovieDao.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Started query to database from method {} of {}", joinPoint.getSignature().getName(), joinPoint.getSourceLocation().getWithinType());
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        log.info("Finished query to database from method {} of {}. It took {} ms", joinPoint.getSignature().getName(),
                joinPoint.getSourceLocation().getWithinType(), System.currentTimeMillis() - startTime);
        return result;
    }
}
