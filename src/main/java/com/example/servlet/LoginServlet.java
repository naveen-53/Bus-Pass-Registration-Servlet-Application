package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.utils.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(LoginServlet.class);
    
	private static final long serialVersionUID = 1L;
	
	private final UserDAO dao = new UserDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    static class LoginRequest {
        public String username;
        public String password;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        LoginRequest login = mapper.readValue(req.getInputStream(), LoginRequest.class);

        try {
            User user = dao.findByUsername(login.username);

            if (user != null && PasswordUtil.verify(login.password, user.getPassword())) {

                HttpSession session = req.getSession(true);
                session.setAttribute("user", user.getUsername());
                session.setAttribute("role", user.getRole());
                session.setMaxInactiveInterval(30 * 60);
                LOG.info(login.username+" Logged in Successfully");
                resp.setStatus(200);
                resp.getWriter().write("Login Successful");
            } else {
                resp.setStatus(401);
                resp.getWriter().write("Invalid Credentials");
            }

        } catch (Exception e) {
        	LOG.error(login.username+" Login was Failed");
            resp.setStatus(500);
            resp.getWriter().write("Login Failed");
        }
    }
}
