package com.colin.datagenerator;

import org.springframework.aop.framework.ProxyFactory;

import com.colin.datagenerator.proxy.Pojo;
import com.colin.datagenerator.proxy.RetryAdvice;
import com.colin.datagenerator.proxy.SimplePojo;

import org.aopalliance.aop.Advice;
/**
 *
 * @author Yiwenzhi
 * @date 2020年12月4日
 *
 */
public class ProxyExp {
    
    
    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory(new SimplePojo());
        factory.addInterface(Pojo.class);
        
        factory.addAdvice(new RetryAdvice());
        factory.setExposeProxy(true);
        Pojo pojo = (Pojo)factory.getProxy();
        pojo.foo();
//        pojo.bar();
    }
    
}
