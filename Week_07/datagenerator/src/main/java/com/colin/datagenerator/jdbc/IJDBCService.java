package com.colin.datagenerator.jdbc;

import java.sql.ResultSet;
import java.util.List;

public interface IJDBCService {
	ResultSet select(String statement, List<ParameterItem> params);
	
	void batchSave(String statement, List<List<ParameterItem>> rows);
}
