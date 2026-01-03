package com.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.servlet.BusPassServlet;

public class DBUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(DBUtil.class);

    private static final String URL = "jdbc:mysql://localhost:3306/bus_pass_db";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
