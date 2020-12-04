package com.colin.datagenerator.jdbc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DataSourceAOP {
    

	@Around("@annotation(com.colin.datagenerator.jdbc.annotation.ReadOnly)")
	public Object readOnlyPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("before");
		JDBCService.readOnly.set(Boolean.TRUE);
		Object o = joinPoint.proceed();
		JDBCService.readOnly.set(Boolean.FALSE);
		if (o != null) {
			return o.toString() + " read only";
		}
		return "";
	}

}
