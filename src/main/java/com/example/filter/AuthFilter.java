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

        chain.doFilter(request, response);
    }
}
