package io.kimmking.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchoolConfiguration {
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
	
	public void setDataSrouceUrl(String dsUrl) {
		dataSourceUrl = dsUrl;
	}

	@Bean("dataSourceUsername")
	public String getDataSourceUsername() {
		return dataSourceUsername;
	}

	public void setDataSourceUsername(String dataSourceUsername) {
		this.dataSourceUsername = dataSourceUsername;
	}

	@Bean("dataSourcePassword")
	public String getDataSourcePassword() {
		return dataSourcePassword;
	}

	public void setDataSourcePassword(String dataSourcePassword) {
		this.dataSourcePassword = dataSourcePassword;
	}
}
