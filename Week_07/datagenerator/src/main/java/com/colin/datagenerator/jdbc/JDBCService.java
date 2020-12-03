package com.colin.datagenerator.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colin.datagenerator.annotation.ReadOnly;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class JDBCService {
	
	@Autowired
	String dataSourceUrl;
	
	@Autowired
	String dataSourceUsername;
	
	@Autowired
	String dataSourcePassword;
	
	Connection conn = null;
	
	Connection poolingConnection = null;
	
	public Connection getConnection() {
		try {
			if (conn != null) {
				return conn;
			}
		    conn =
		       DriverManager.getConnection(dataSourceUrl
		                                   + "&user=" + dataSourceUsername
		                                   + "&password=" + dataSourcePassword);
		    System.out.println("connected to datasource, connection: " + conn);
		    return conn;
		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}
	
	@SuppressWarnings("resource")
	public Connection getPoolingConnection() {
		if (poolingConnection != null) {
			return poolingConnection;
		}
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl(dataSourceUrl);
		ds.setUsername(dataSourceUsername);
		ds.setPassword(dataSourcePassword);
		ds.addDataSourceProperty("cachePrepStmts", "true");
	    ds.addDataSourceProperty("prepStmtCacheSize", "250");
	    ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	    try {
	    	poolingConnection = ds.getConnection();
	    	return poolingConnection;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return null;
	}
	
	
	/**
	 * 单次查询
	 */
	@ReadOnly
	private void tryQuery(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			if (stmt.execute("SELECT * FROM t_client LIMIT 10")) {
				rs = stmt.getResultSet();
				while (rs.next()) {
					System.out.println(rs);
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
	
	@ReadOnly
	public void tryQuery() {
		tryQuery(getConnection());
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
	
	public void transactionalBatchUpdate() {
		transactionalBatchUpdate(getConnection());
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
	
}
