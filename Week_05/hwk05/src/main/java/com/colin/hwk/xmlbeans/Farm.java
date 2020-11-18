package com.colin.hwk.xmlbeans;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
@Data
public class Farm {
    
    @Autowired
    private IAnimal animal;
    
    public void open() {
        System.out.println("The farm is opening, and the animals are barking");
        this.animal.bark();
    }
    
}
