package com.example.utils;

import java.sql.Connection;

import javax.sql.DataSource;

public class DBUtil {
	

	private static DataSource dataSource;
	
	public static void setDataSource(DataSource ds) {
		dataSource=ds;
	}

    public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
    }
}
