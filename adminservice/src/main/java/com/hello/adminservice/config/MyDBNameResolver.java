package com.hello.adminservice.config;

import ch.qos.logback.classic.db.names.DBNameResolver;

public class MyDBNameResolver implements DBNameResolver {
	private String tableNamePrefix = "t_";
	//private String tableNameSuffix = "";
	private String columnNamePrefix = "";
	private String columnNameSuffix = "";

	//修改表名
	@Override
	public <N extends Enum<?>> String getTableName(N tableName) {
		System.out.println("get tableName: " + tableName);
		return tableNamePrefix + tableName.toString().toLowerCase();
	}

	//修改列名
	@Override
	public <N extends Enum<?>> String getColumnName(N columnName) {
		/*System.out.println(" getColumnName " + columnName + " " + columnName.name().toLowerCase());
		if("arg0".equals(columnName.name().toLowerCase())){
			return "log_type";
		} else {
			return columnNamePrefix + columnName.name().toLowerCase() + columnNameSuffix;
		}*/
		
		return columnName.toString().toLowerCase();
	}
}
