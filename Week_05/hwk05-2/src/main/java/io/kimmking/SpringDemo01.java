package io.kimmking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import io.kimmking.aop.ISchool;
import io.kimmking.jdbc.JDBCService;
import io.kimmking.spring01.Student;
import io.kimmking.spring02.Klass;

@SpringBootApplication
public class SpringDemo01 {
    
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringDemo01.class, args);
        Klass klass = context.getBean(Klass.class);
        klass.dong();
        
        JDBCService jdbcService = context.getBean(JDBCService.class);
        jdbcService.tryQuery();
        jdbcService.tryQuery();
        jdbcService.tryPoolingQuery();
    }
}
