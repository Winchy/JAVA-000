package io.kimmking.spring01;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    
    private int id;
	
    private String name;
    
    public void init(){
        System.out.println("hello...........");
    }
    
}
