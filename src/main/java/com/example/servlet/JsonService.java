package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class JsonService extends HttpServlet {
	
    
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
       
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");

        String jsonResponse = String.format(
            "{\"status\": %d, \"error\": \"Bad Request\", \"message\": \"%s\"}", 
            statusCode, message != null ? message : "Invalid request data"
        );

        response.getWriter().write(jsonResponse);

    }
}
