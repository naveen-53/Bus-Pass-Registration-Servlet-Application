package com.example.config;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiquibaseInitializer implements ServletContextListener {
	
	private static final Logger LOG = LoggerFactory.getLogger(LiquibaseInitializer.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Properties properties = new Properties();
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

			try (Connection connection = DriverManager.getConnection(
					properties.getProperty("db.url"),
					properties.getProperty("db.username"),
					properties.getProperty("db.password"));
				 JdbcConnection jdbcConnection = new JdbcConnection(connection)) {

				Database database = DatabaseFactory.getInstance()
						.findCorrectDatabaseImplementation(jdbcConnection);

				try (Liquibase liquibase = new Liquibase(
						"db/changelog/db.changelog-master.xml",
						new ClassLoaderResourceAccessor(),
						database)) {

					liquibase.update();
					LOG.info("Liquibase started");
				}

				LOG.info("Liquibase executed automatically!");
			}

		}catch (Exception e) {
	        LOG.error(e.getMessage());
	    }
	}


}
