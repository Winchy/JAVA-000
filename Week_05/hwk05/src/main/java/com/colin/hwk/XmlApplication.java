package com.colin.hwk;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.colin.hwk.annotationbeans.DuckService;
import com.colin.hwk.xmlbeans.Farm;
import com.colin.hwk.xmlbeans.IAnimal;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
public class XmlApplication {
    
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        IAnimal animal = (IAnimal)context.getBean("animal");
        animal.bark();
        
        Farm farm = context.getBean(Farm.class);
        farm.open();
        
        DuckService duckService = context.getBean(DuckService.class);
        duckService.serve();
        
        context.close();
    }
    
    
}
