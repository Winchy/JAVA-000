package com.colin.datagenerator.jdbc;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class DataSourceAOP {
    

	@Around("@annotation(com.colin.datagenerator.jdbc.annotation.ReadOnly)")
	public Object readOnlyPoint(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("before");
		ReadWriteJDBCService.readOnly.set(Boolean.TRUE);
		Object o = joinPoint.proceed();
		ReadWriteJDBCService.readOnly.set(Boolean.FALSE);
		if (o != null) {
			return o.toString() + " read only";
		}
		return "";
	}

}
