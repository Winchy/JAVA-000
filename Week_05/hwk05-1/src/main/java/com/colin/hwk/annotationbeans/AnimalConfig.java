package com.colin.hwk.annotationbeans;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Conditional;
import com.colin.hwk.xmlbeans.IAnimal;

/**
 *
 * @author Yiwenzhi
 * @date 2020年11月18日
 *
 */
@Configuration
public class AnimalConfig {
    
    @Bean("cat")
    public IAnimal getCat() {
        return new Cat();
    }
    
    @Bean("whiteCat")
    @Conditional(WhiteCondition.class)
    public IAnimal getWhiteCat() {
        return new Cat("white");
    }
    
    @Bean("blackCat")
    @ConditionalOnBean(name = "whiteCat")
    public IAnimal getBlackCat() {
        System.out.println("black cat goes with white cat!");
        return new Cat("black");
    }
    
}
