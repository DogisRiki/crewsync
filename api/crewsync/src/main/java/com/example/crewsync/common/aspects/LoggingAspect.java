package com.example.crewsync.common.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ロギングクラスです
 */
@Aspect
@Component
public class LoggingAspect {

    public static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * 開始ログを出力します
     *
     * @param joinPoint ジョインポイント
     */
    @Before("@annotation(com.example.crewsync.common.aspects.annotations.LogRequired)")
    public void logStarted(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("{}#{} Started.", className, methodName);
    }

    /**
     * 正常終了ログを出力します
     *
     * @param joinPoint ジョインポイント
     */
    @AfterReturning("@annotation(com.example.crewsync.common.aspects.annotations.LogRequired)")
    public void logEnded(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("{}#{} Normally Ended.", className, methodName);
    }

    /**
     * 異常終了ログを出力します
     *
     * @param joinPoint ジョインポイント
     * @param ex        例外オブジェクト
     */
    @AfterThrowing(value = "@annotation(com.example.crewsync.common.aspects.annotations.LogRequired)", throwing = "ex")
    public void logExceptionThrown(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        logger.info("{}#{} throws {}({}) .", className, methodName, ex.getClass().getSimpleName(),
                ex.getLocalizedMessage());
    }
}
