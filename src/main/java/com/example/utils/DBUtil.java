package com.example.utils;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBUtil {
	

	private static DataSource dataSource;
	
	public static void setDataSource(DataSource ds) {
		dataSource=ds;
	}

    public static Connection getConnection() throws Exception {
		return dataSource.getConnection();
    }
}
