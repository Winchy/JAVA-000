package com.colin.hwk.annotationbeans;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
@Service
public class DuckService {
    
    @Resource
    Duck duck;
    
    public void serve() {
        System.out.println("Duck service serves ducks");
        duck.bark();
    }
    
}
