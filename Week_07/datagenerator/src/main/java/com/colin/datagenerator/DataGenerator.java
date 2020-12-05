package com.colin.datagenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.colin.datagenerator.jdbc.IJDBCService;
import com.colin.datagenerator.jdbc.ParameterItem;
import com.colin.datagenerator.jdbc.annotation.ReadOnly;

@Service
public class DataGenerator {

	private static final int SHOP_NUM = 10000;
	private static final int COMMODITY_NUM = 100;
	private static final int CLIENT_NUM = 100000;
	
//	@Resource(name="shardingsphere")
	@Resource(name="readwrite")
	IJDBCService jdbcService;
	
	Snowflake snowflake = new Snowflake(1, 1, 1);;
	Random rand = new Random();
	
	public void generateShops() {
		Long id;
		String insertStatement = "insert into t_shop (id, name, manager, introduction)"
				+ " values (?, ?, ?, ?) ";
		List<List<ParameterItem>> rows = new LinkedList<>();
		for (int i = 0; i < SHOP_NUM; ++i) {
			id = snowflake.nextId();
			List<ParameterItem> parameters = new ArrayList<>();
			parameters.add(new ParameterItem(Long.class, id));
			parameters.add(new ParameterItem(String.class, "店铺" + i));
			parameters.add(new ParameterItem(String.class, "店铺老板" + i));
			parameters.add(new ParameterItem(String.class, "这是第" + i + "个店铺"));
			rows.add(parameters);
			generateCommodities(id, i);
		} 
		jdbcService.batchSave(insertStatement, rows);
	}
	
	public void generateCommodities(Long shopId, int shopIdx) {
		Long id;
		String insertStatement = "insert into t_commodity (id, shop_id, name, price, type, introduction) values (?, ?, ?, ?, ?, ?) ";
		List<List<ParameterItem>> rows = new LinkedList<>();
		for (int i = 0; i < COMMODITY_NUM; ++i) {
			id = snowflake.nextId();
			List<ParameterItem> parameters = new ArrayList<>();
			parameters.add(new ParameterItem(Long.class, id));
			parameters.add(new ParameterItem(Long.class, shopId));
			parameters.add(new ParameterItem(String.class, "商品_" + shopIdx + "_" + i));
			parameters.add(new ParameterItem(Integer.class, 100 + rand.nextInt(10000)));
			parameters.add(new ParameterItem(String.class, "商品类型" + rand.nextInt(20)));
			parameters.add(new ParameterItem(String.class, "商品_" + shopIdx + "_" + i));
			rows.add(parameters);
		}
		jdbcService.batchSave(insertStatement, rows);
	}
	
	public void generateUser() {
		Long id;
		String insertUserStatement = "insert into t_client (id, username, password, phone, gender, nickname) values (?, ?, password(?), ?, ?, ?) ";
		for (int x = 0; x < CLIENT_NUM / 1000; ++x) {
			System.out.println(x);
			List<List<ParameterItem>> userRows = new LinkedList<>();
			for (int y = 0; y < 1000; ++y) {
				int i = y + x * 1000;
				id = snowflake.nextId();
				List<ParameterItem> parameters = new ArrayList<>();
				parameters.add(new ParameterItem(Long.class, id));
				parameters.add(new ParameterItem(String.class, "用户_" + i));
				parameters.add(new ParameterItem(String.class, "password_" + i));
				parameters.add(new ParameterItem(String.class, "18600000000" + rand.nextInt(100000)));
				parameters.add(new ParameterItem(Integer.class, rand.nextInt(1)));
				parameters.add(new ParameterItem(String.class, "阿"  + i));
				userRows.add(parameters);
			}
			jdbcService.batchSave(insertUserStatement, userRows);
		}
	}
	
	@ReadOnly
	public void checkRead() {
		Random rand = new Random();
		for (int i = 0; i < 10; ++i) {
			ResultSet results = jdbcService.select("select flag from t_shop limit " + rand.nextInt(SHOP_NUM) + ", 1", null);
			try {
				if (results.next()) {
					System.out.println(results.getInt(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
