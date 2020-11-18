package com.colin.hwk.annotationbeans;

import com.colin.hwk.xmlbeans.IAnimal;

import lombok.Data;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
@Data
public class Cat implements IAnimal {
    
    private String color;
    
    public Cat() {
        this("gray");
    }
    
    public Cat(String color) {
        this.color = color;
    }
    
    public void bark() {
        System.out.println("A cat sounds like 'Mew! My color is " + color + "!'");
    }
    
}
