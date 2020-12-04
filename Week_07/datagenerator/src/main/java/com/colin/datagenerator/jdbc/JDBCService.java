package com.colin.datagenerator.jdbc;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.colin.datagenerator.jdbc.annotation.ReadOnly;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class JDBCService {

	
	Connection poolingConnection = null;
	
	protected static ThreadLocal<Boolean> readOnly = new ThreadLocal<>();
	
	
	public Connection getPoolingConnection() {
		if (poolingConnection != null) {
			return poolingConnection;
		}
	    try {
//		HikariDataSource ds = new HikariDataSource();
	        ClassPathResource classPathResource = new ClassPathResource("sharding-sphere.yml");
	        DataSource ds = YamlMasterSlaveDataSourceFactory.createDataSource(
	                classPathResource.getFile()
	                );
//	        ds.setJdbcUrl(dataSourceUrl);
//	        ds.setUsername(dataSourceUsername);
//	        ds.setPassword(dataSourcePassword);
//	        ds.addDataSourceProperty("cachePrepStmts", "true");
//	        ds.addDataSourceProperty("prepStmtCacheSize", "250");
//	        ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	    	poolingConnection = ds.getConnection();
	    	return poolingConnection;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return null;
	}
	
	
	/**
	 * 单次查询
	 */
	private void tryQuery(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			if (stmt.execute("SELECT * FROM alum_APPInfo LIMIT 10")) {
				rs = stmt.getResultSet();
				while (rs.next()) {
					System.out.println(rs.getString(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException sqlEx) { } // ignore
		        rs = null;
		    }

		    if (stmt != null) {
		        try {
		            stmt.close();
		        } catch (SQLException sqlEx) { } // ignore

		        stmt = null;
		    }
		}
	}
	
	public void tryPoolingQuery() {
		tryQuery(getPoolingConnection());
	}
	
	/**
	 * 批处理更新事务
	 */
	private void transactionalBatchUpdate(Connection conn) {
		try {
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("UPDATE VistTest set visitTimes=? WHERE id=?");
			for (int i = 0; i < 10; ++i) {
				pstmt.setInt(1, i);
				pstmt.setInt(2, i);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			
		}
	}
	
	
	public void poolingTransactionalBatchUpdate() {
		transactionalBatchUpdate(getPoolingConnection());
	}
	
	public void batchSave(String statement, List<List<ParameterItem>> rows) {
		try {
			Connection conn = getPoolingConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(statement);
			rows.forEach(row -> {
				try {
					for (int i = 0; i < row.size(); ++ i) {
						ParameterItem item = row.get(i);
						if (item.getType().equals(Integer.class)) {
								pstmt.setInt(i + 1, (Integer)item.getValue());
						} else if (item.getType().equals(Long.class)) {
							pstmt.setLong(i + 1, (Long)item.getValue());
						} else if (item.getType().equals(String.class)) {
							pstmt.setString(i + 1, (String)item.getValue());
						}
					}
					pstmt.addBatch();
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
	
	
	public void test() {
	    System.out.println(readOnly.get());
	}
}
