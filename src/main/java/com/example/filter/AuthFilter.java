package com.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@WebFilter("/passes/*")
public class AuthFilter implements Filter {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthFilter.class);


    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
        	LOG.error("Session was not Created");
            resp.setStatus(401);
            resp.getWriter().write("Authentication Required");
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
    }
}
