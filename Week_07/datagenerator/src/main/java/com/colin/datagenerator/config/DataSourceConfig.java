package com.colin.datagenerator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
	@Value("${spring.datasource.url}")
	String dataSourceUrl;
	
	@Value("${spring.datasource.username}")
	String dataSourceUsername;
	
	@Value("${spring.datasource.password}")
	String dataSourcePassword;
//	
//	@Autowired
//    private Environment environment;
	
	@Bean("dataSourceUrl")
	public String getDatasourceUrl() {
		return dataSourceUrl;
	}
	
	@Bean("dataSourceUsername")
	public String getDataSourceUsername() {
		return dataSourceUsername;
	}


	@Bean("dataSourcePassword")
	public String getDataSourcePassword() {
		return dataSourcePassword;
	}

}
