package com.colin.hwk.annotationbeans;

import org.springframework.stereotype.Component;
import com.colin.hwk.xmlbeans.IAnimal;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
@Component
public class Duck implements IAnimal {
   
    public void bark() {
        System.out.println("Ducks bark like 'Quark'!");
    }
    
}
