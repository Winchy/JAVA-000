package com.colin.hwk.xmlbeans;

import lombok.Data;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
@Data
public class Dog implements IAnimal {

    String name;
    
    @Override
    public void bark() {
        System.out.println("A " + name + " barks like 'Woof'!");
    }
    
}
