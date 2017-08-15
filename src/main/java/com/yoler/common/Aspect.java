package com.yoler.common;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangyu on 2017/6/29.
 */
public class Aspect {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void before(JoinPoint point) {
        StringBuffer sb = new StringBuffer();
        String thread = Thread.currentThread().getName();
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        sb.append("--[").append(thread).append("]before--").append(className).append(".").append(methodName);
        sb.append("(");
        if (point.getArgs() != null) {
        }
        sb.append(")");
        logger.debug(sb.toString());
    }

    public void afterReturning(JoinPoint point, Object returnVal) {
        StringBuffer sb = new StringBuffer();
        String thread = Thread.currentThread().getName();
        sb.append("--[").append(thread).append("]after--");
        if (returnVal == null) {
            sb.append("void or null");
        } else {
            sb.append(returnVal.toString());
        }
        logger.debug(sb.toString());
    }

    public void afterThrowing(JoinPoint point, Throwable e) {
        logger.error(e.getMessage(), e);
    }
}
