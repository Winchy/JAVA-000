package com.colin.datagenerator.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DataSourceAOP {

	@Around("@annotation(com.colin.datagenerator.annotation.ReadOnly)")
	public String readOnlyPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("before");
		Object o = joinPoint.proceed();
		System.out.println("after");
		if (o != null) {
			return o.toString() + " read only";
		}
		return "";
	}
//	
//	@Pointcut("@annotation(secured)")
//    public void callAt(Secured secured) {
//    }

	 @Before("execution(* com.colin.datagenerator.*.*(..))")
     public void advice() {
		 System.out.println("xxxxxxxxxxxxxxxxxxxxx");
         // advise FooService methods as appropriate
     }
}
