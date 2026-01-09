package com.example.filter;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggingFilter implements Filter{
	
	private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		 HttpServletRequest req = (HttpServletRequest) request;
		 
		 LOG.info("LoggingFilter - Incoming Request: " + req.getRequestURI());

	     long start = System.currentTimeMillis();
	     
	     chain.doFilter(request, response);
	     
	     long time = System.currentTimeMillis() - start;
         LOG.info("LoggingFilter - Response completed in " + time + " ms");
	     HttpServletResponse res = (HttpServletResponse) response;
	     
	     res.setHeader("X-App", "Employee-Service");
         res.setHeader("Cache-Control", "no-store");
		
	}

}
