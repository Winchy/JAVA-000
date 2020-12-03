package com.colin.datagenerator.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParameterItem {
	
	private Class<?> type;
	
	private Object value;
	
}
