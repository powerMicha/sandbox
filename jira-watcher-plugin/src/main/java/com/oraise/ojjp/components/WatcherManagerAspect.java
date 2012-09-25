package com.oraise.ojjp.components;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: malbers
 * Date: 24.08.12
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
@Aspect
public class WatcherManagerAspect {

    private static final Logger log = LoggerFactory.getLogger(WatcherManagerAspect.class);

    public WatcherManagerAspect(){
        log.info("constructor called!");
    }

    @Around("com.atlassian.jira.issue.watchers.DefaultWatcherManager.startWatching()")
    public Object aroundStartWatching(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("before start watching");
        // start stopwatch
        Object retVal = proceedingJoinPoint.proceed();
        // stop stopwatch

        log.info("after start watching");

        return retVal;
    }

}
