package com.colin.datagenerator.proxy;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;

/**
 *
 * @author Yiwenzhi
 * @date 2020年12月4日
 *
 */
public class RetryAdvice extends MethodBeforeAdviceInterceptor {

    /**
     * 
     */
    private static final long serialVersionUID = 759158256998704141L;

    public RetryAdvice(MethodBeforeAdvice advice) {
        super(advice);
        // TODO Auto-generated constructor stub
    }
    
    public RetryAdvice() {
        this(new MethodBeforeAdvice() {

            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                // TODO Auto-generated method stub
                System.out.println("before calling");
//                method.invoke(target, args);
            }
            
        });
    }
    
}
