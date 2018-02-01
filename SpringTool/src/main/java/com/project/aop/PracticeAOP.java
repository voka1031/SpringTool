package com.project.aop;

import java.util.logging.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PracticeAOP {
	private static final Logger logger = LoggerFactory.getLogger(PracticeAOP.class);

	// 所有繼承這個interface的方法.
	// @Pointcut("target(com.project.model.PracticeDAO_interface)")

	// @Pointcut("execution(* com.project.model.PracticeDAO_interface.get*(..))"
	// + " && !execution(* com.project.model.PracticeDAO_interface.getPaging(..))")
	@Pointcut("@annotation(com.project.annotation.CatchForAOP)")
	private void getMethod() {
	}

	@Pointcut("@within(org.springframework.transaction.annotation.Transactional)")
	private void transMethod() {
	}

	@Before("getMethod()")
	public void beforeLogAdvice(JoinPoint joinPoint) {
		loggingMethod(joinPoint);
		System.out.println("beforeLogAdvice : getMethod()");
	}

	@AfterReturning(pointcut = "transMethod()", returning = "retVal")
	public void logAfterReturning(JoinPoint joinPoint, Object retVal) {
		loggingMethod(joinPoint);
		System.out.println("Transactional AOP - hijacked method : " + joinPoint.getSignature().getName());
	}

	private void loggingMethod(JoinPoint joinPoint) {
		logger.info(Level.INFO + " - AOP logging : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		System.out.println("-------------------------------------");
	}
}