package com.example.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoggingFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		 HttpServletRequest req = (HttpServletRequest) request;
		 
		 System.out.println("LoggingFilter - Incoming Request: " + req.getRequestURI());

	     long start = System.currentTimeMillis();
	     
	     chain.doFilter(request, response);
	     
	     long time = System.currentTimeMillis() - start;
         System.out.println("LoggingFilter - Response completed in " + time + " ms");
	     HttpServletResponse res = (HttpServletResponse) response;
	     
	     res.setHeader("X-App", "Employee-Service");
         res.setHeader("Cache-Control", "no-store");
		
	}

}
