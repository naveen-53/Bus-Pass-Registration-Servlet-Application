package com.example.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.utils.DBUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

public class AppConfigListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(AppConfigListener.class);

	private HikariDataSource dataSource;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			Properties properties = new Properties();
		
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
			
			sce.getServletContext().setAttribute("appConfig", properties);
			
			
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(properties.getProperty("db.url"));
			config.setUsername(properties.getProperty("db.username"));
			config.setPassword(properties.getProperty("db.password"));
			config.setDriverClassName("com.mysql.cj.jdbc.Driver");   
			config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.pool.size", "10")));
			config.setConnectionTimeout(Long.parseLong(properties.getProperty("db.pool.timeout", "30000")));

			dataSource = new HikariDataSource(config);
			DBUtil.setDataSource(dataSource);

			sce.getServletContext().setAttribute("dataSource", dataSource);

			LOG.info("HikariCP initialized successfully");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		if (dataSource != null) {
			dataSource.close();
			LOG.info("HikariCP was Shutdown");
		}
	}

}
