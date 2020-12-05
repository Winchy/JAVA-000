package com.colin.datagenerator.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.colin.datagenerator.config.DataSourceConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

@Component("readwrite")
@Data
public class ReadWriteJDBCService implements IJDBCService {

	protected static ThreadLocal<Boolean> readOnly = new ThreadLocal<>();
	static {
		readOnly.set(false);
	}
	
	@Resource
	DataSourceConfig dataSourceConfig;
	
	Connection masterConnection;
	
	List<Connection> slaveConnections;
	
	AtomicInteger count = new AtomicInteger(0);

	
	private Connection getMasterConnection() {
		if (masterConnection != null) {
			return masterConnection;
		}
		HikariDataSource ds = new HikariDataSource();
		Map<String, String> master = dataSourceConfig.getMaster();
		ds.setJdbcUrl(master.get("url"));
		ds.setUsername(master.get("username"));
		ds.setPassword(master.get("password"));
		ds.addDataSourceProperty("cachePrepStmts", "true");
	    ds.addDataSourceProperty("prepStmtCacheSize", "250");
	    ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
	    try {
	    	masterConnection = ds.getConnection();
	    	masterConnection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return masterConnection;
	}
	
	private Connection getSlaveConnection() {
		if (slaveConnections == null) {
			slaveConnections = new ArrayList<>();
			List<Map<String, String>> slaves = dataSourceConfig.getSlaves();
			slaves.forEach(slave -> {
				HikariDataSource ds = new HikariDataSource();
				ds.setJdbcUrl(slave.get("url"));
				ds.setUsername(slave.get("username"));
				ds.setPassword(slave.get("password"));
				ds.addDataSourceProperty("cachePrepStmts", "true");
				ds.addDataSourceProperty("prepStmtCacheSize", "250");
				ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
				try {
					Connection conn = ds.getConnection();
					conn.setAutoCommit(false);
					slaveConnections.add(conn);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		}
	    return slaveConnections.get(count.getAndIncrement() % slaveConnections.size());
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
	
	private Connection getConnection() {
		if (readOnly.get()) {
			return getSlaveConnection();
		} else {
			return getMasterConnection();
		}
	}
	
	public ResultSet select(String statement, List<ParameterItem> params) {
		try {
			Connection conn = getConnection();
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
			Connection conn = getConnection();
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
