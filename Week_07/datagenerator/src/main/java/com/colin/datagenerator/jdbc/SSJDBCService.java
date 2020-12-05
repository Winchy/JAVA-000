package com.colin.datagenerator.jdbc;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service("shardingsphere")
public class SSJDBCService implements IJDBCService {

	
	Connection poolingConnection = null;
	
	
	public Connection getPoolingConnection() {
		if (poolingConnection != null) {
			return poolingConnection;
		}
	    try {
	        ClassPathResource classPathResource = new ClassPathResource("sharding-sphere.yml");
	        DataSource ds = YamlMasterSlaveDataSourceFactory.createDataSource(
	                classPathResource.getFile()
	                );
	    	poolingConnection = ds.getConnection();
	    	poolingConnection.setAutoCommit(false);
	    	return poolingConnection;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
            e.printStackTrace();
        }
	    return null;
	}
	
	private void parseParams(PreparedStatement pstmt, List<ParameterItem> params) throws SQLException {
		if (params == null) {
			return;
		}
		for (int i = 0; i < params.size(); ++ i) {
			ParameterItem item = params.get(i);
			if (item.getType().equals(Integer.class)) {
					pstmt.setInt(i + 1, (Integer)item.getValue());
			} else if (item.getType().equals(Long.class)) {
				pstmt.setLong(i + 1, (Long)item.getValue());
			} else if (item.getType().equals(String.class)) {
				pstmt.setString(i + 1, (String)item.getValue());
			}
		}
		pstmt.addBatch();
	}
	
	public ResultSet select(String statement, List<ParameterItem> params) {
		try {
			Connection conn = getPoolingConnection();
			PreparedStatement pstmt = conn.prepareStatement(statement);
			parseParams(pstmt, params);
			pstmt.execute();
			conn.commit();
			return pstmt.getResultSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void batchSave(String statement, List<List<ParameterItem>> rows) {
		try {
			Connection conn = getPoolingConnection();
			PreparedStatement pstmt = conn.prepareStatement(statement);
			rows.forEach(row -> {
				try {
					parseParams(pstmt, row);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			pstmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
