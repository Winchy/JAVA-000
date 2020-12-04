package com.colin.datagenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colin.datagenerator.jdbc.annotation.ReadOnly;
import com.colin.datagenerator.jdbc.JDBCService;
import com.colin.datagenerator.jdbc.ParameterItem;

@Service
public class DataGenerator {

	private static final int SHOP_NUM = 10000;
	private static final int COMMODITY_NUM = 100;
	private static final int CLIENT_NUM = 1000000;
	
	@Autowired
	JDBCService jdbcService;
	
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
	
	private static void generateUser() {
		
	}
	
	public void tryQuery() {
		jdbcService.tryPoolingQuery();
		jdbcService.poolingTransactionalBatchUpdate();
	}
	
	@ReadOnly
	public Object test() {
	    jdbcService.test();
		return Integer.valueOf(1);
	}
	
	public Object test2() {
        jdbcService.test();
        return Integer.valueOf(2);
    }
}
