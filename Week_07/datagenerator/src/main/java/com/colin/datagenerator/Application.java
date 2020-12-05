package com.colin.datagenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@ComponentScan("com.colin.datagenerator")
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class);
		DataGenerator dataGenerator = context.getBean(DataGenerator.class);
//		dataGenerator.generateShops();
//		dataGenerator.generateUser();
		dataGenerator.checkRead();
	}
	
}
