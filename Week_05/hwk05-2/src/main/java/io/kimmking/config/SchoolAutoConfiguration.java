package io.kimmking.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.kimmking.aop.ISchool;
import io.kimmking.spring01.Student;
import io.kimmking.spring02.Klass;
import io.kimmking.spring02.School;

@Configuration
@Import(SchoolConfiguration.class)
public class SchoolAutoConfiguration {

	@Bean("student100")
	public Student getStudent100() {
		return new Student(100, "liangchaowei");
	}
	
	@Bean("students")
	@ConfigurationProperties(prefix="students")
	public List<Student> getStudents() {
		return new ArrayList<>();
	}
	
	@Bean
	public ISchool getSchool() {
		return new School();
	}
	
	@Bean
	public Klass getKlass() {
		return new Klass();
	}
	
//	@Bean("dataSource")
//	public DataSource getDataSource() {
//		String url = environment.getProperty("spring.datasource.url");
//		String username = environment.getProperty("spring.datasource.username");
//		String password = environment.getProperty("spring.datasource.password");
//		return new DataSource(url, username, password);
//	}
	
}
