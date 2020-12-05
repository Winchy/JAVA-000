package com.colin.datagenerator.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "datasources")
@Data
public class DataSourceConfig {
	Map<String, String> master;
	List<Map<String, String>> slaves;
}
