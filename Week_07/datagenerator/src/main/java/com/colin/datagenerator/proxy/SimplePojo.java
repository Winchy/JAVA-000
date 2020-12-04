package com.colin.datagenerator.proxy;

import org.springframework.aop.framework.AopContext;

/**
 *
 * @author Yiwenzhi
 * @date 2020年12月4日
 *
 */
public class SimplePojo implements Pojo {
    public void foo() {
        System.out.println("foo");
//        this.bar();
        ((Pojo) AopContext.currentProxy()).bar();
    }
    
    public void bar() {
        System.out.println("bar");
    }
}
