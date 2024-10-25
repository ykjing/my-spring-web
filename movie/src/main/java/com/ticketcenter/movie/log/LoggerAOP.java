package com.ticketcenter.movie.log;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ticketcenter.movie.data.Movie;

@Aspect
@Component
public class LoggerAOP {
	
	private final Logger logger = LoggerFactory.getLogger(LoggerAOP.class);
	
//	@Pointcut("execution(* com.ticketcenter.movie.bean.*.*(..))")
//	public void cutBeanFunction() {}
	
	@Around("execution(* com.ticketcenter.movie.MovieSchduler.*(..)) || "
			+ "execution(* com.ticketcenter.movie.service.*.*(..)) || "
			+ "@annotation(LogExecution)")
	public Object logTimeCost(ProceedingJoinPoint jp) throws Throwable {
		Signature signature = jp.getSignature();
		String target = signature.toShortString().split("\\(")[0];
		logger.info("[{}] Start.", target);
		long startTime = System.currentTimeMillis();
		Object res = jp.proceed();
		long timeCost = System.currentTimeMillis() - startTime;
		logger.info("[{}] End. Cost {} ms.", target, timeCost);
		return res;
	}
	
//	@Around("execution(* com.ticketcenter.movie.ScheduledTasks.*(..))")
//	public void logScheduler(ProceedingJoinPoint jp) throws Throwable {
//		Signature signature = jp.getSignature();
//		logger.info("[{}] ScheduledTask Start.",signature.getName());
//		Date startTime = new Date();
//		jp.proceed();
//		long timeCost = new Date().getTime() - startTime.getTime();
//		logger.info("[{}] ScheduledTask End. Cost {} ms.", signature.getName(), timeCost);
//	}
	
}
