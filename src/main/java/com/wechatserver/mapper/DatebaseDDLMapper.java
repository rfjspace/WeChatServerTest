package com.wechatserver.mapper;

public interface DatebaseDDLMapper {
	public void createTable();

	public void dropTable();

	public void alterTable();
	
	public void deleteTableData();
}
