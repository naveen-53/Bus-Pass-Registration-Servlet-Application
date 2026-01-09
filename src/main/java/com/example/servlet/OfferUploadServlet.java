package com.example.servlet;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.service.OfferProducer;
import com.example.servlet.LoginServlet.LoginRequest;
import com.example.utils.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OfferUploadServlet extends HttpServlet {
	

    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    
	private static final long serialVersionUID = 1L;
	
    private final ObjectMapper mapper = new ObjectMapper();
    
    static class OfferUpload{
    	public String offerMessage;
    }

    
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

    	OfferUpload message = mapper.readValue(req.getInputStream(), OfferUpload.class);
    	
        try {
        	
        	OfferProducer.publishOffer(message.offerMessage);
            

        } catch (Exception e) {
        	LOG.error("Offer message failed");
            resp.setStatus(500);
            resp.getWriter().write("Offer message failed");
        }
    }
}


