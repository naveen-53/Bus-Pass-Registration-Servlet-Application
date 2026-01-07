package com.example.filter;


import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthFilter implements Filter {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        
        LOG.info("AuthFilter - checking session");

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
        	LOG.error("AuthFilter - Unauthorized!");
            resp.setStatus(401);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Login Required\"}");
            return;
        }
        
        String role = (String) session.getAttribute("role");
        if(req.getMethod().equals("DELETE") || req.getMethod().equals("PUT")) {
            if(!"ADMIN".equalsIgnoreCase(role)) {
                resp.setStatus(403);
                resp.getWriter().write("Access Denied");
                LOG.error("access denied for user "+session.getAttribute("username"));
                return;
            }
        }

        chain.doFilter(request, response);
        
        LOG.info("AuthFilter - Adding Security Headers");
        resp.setHeader("X-Frame-Options", "DENY");
        resp.setHeader("X-Content-Type-Options", "nosniff");

        if (resp.getContentType() == null)
            resp.setContentType("application/json;charset=UTF-8");
    }
}
