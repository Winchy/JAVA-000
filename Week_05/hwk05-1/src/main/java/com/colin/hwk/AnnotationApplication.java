package com.colin.hwk;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.colin.hwk.annotationbeans.DuckService;
import com.colin.hwk.xmlbeans.IAnimal;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
public class AnnotationApplication {
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.colin.hwk.annotationbeans");
        
        DuckService duckService = context.getBean(DuckService.class);
        duckService.serve();
        
        IAnimal cat = (IAnimal)context.getBean("cat");
        cat.bark();
        
        System.out.println("Black cat exists: " + context.containsBean("blackCat"));
        
        context.close();
    }
    
}
