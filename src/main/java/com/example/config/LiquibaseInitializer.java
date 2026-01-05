package com.example.config;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebListener
public class LiquibaseInitializer implements ServletContextListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(LiquibaseInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");

	        Connection connection = DriverManager.getConnection(
	                "jdbc:mysql://localhost:3306/bus_pass_db",
	                "root",
	                "1234"
	        );

	        Database database = DatabaseFactory.getInstance()
	                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

	        try (Liquibase liquibase = new Liquibase(
	                "db/changelog/db.changelog-master.xml",
	                new ClassLoaderResourceAccessor(),
	                database
	        )) {
				liquibase.update();
			}

	        System.out.println("Liquibase executed automatically!");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
