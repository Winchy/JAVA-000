package com.colin.hwk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.colin.hwk.autoconfig.Cow;

import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
//@EnableAutoConfiguration
@ComponentScan("com.colin.hwk.autoconfig")
public class AutoConfigureApplicationContext {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AutoConfigureApplicationContext.class);
        System.out.println(context.getBean(Cow.class).getClass());
    }
    
}
