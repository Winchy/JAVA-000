package io.kimmking.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariDataSource;



@Service
public class JDBCService {
	
	@Resource(name="dataSourceUrl")
	String dataSourceUrl;
	
	@Resource(name="dataSourceUsername")
	String dataSourceUsername;
	
	@Resource(name="dataSourcePassword")
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
			return ds.getConnection();
		} catch (SQLException e) {
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
			conn = getConnection();
			stmt = conn.createStatement();
			if (stmt.execute("SELECT APPNAME FROM alum_APPInfo LIMIT 10")) {
				rs = stmt.getResultSet();
				while (rs.next()) {
					System.out.println(rs.getString("APPNAME"));
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
	
}
