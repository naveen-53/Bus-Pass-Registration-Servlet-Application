package com.example.utils;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DBUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(DBUtil.class);
	
	private static DataSource dataSource;
	
	public static void setDataSource(DataSource ds) {
		dataSource=ds;
	}

//    private static final String URL = "jdbc:mysql://localhost:3306/bus_pass_db";
//    private static final String USER = "root";
//    private static final String PASS = "1234";

    public static Connection getConnection() throws Exception {
        
        //return DriverManager.getConnection(URL, USER, PASS);
    	return dataSource.getConnection();
    }
}
