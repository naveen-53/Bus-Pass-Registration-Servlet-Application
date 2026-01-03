package com.example.servlet;

import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.utils.PasswordUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(RegisterServlet.class);
	
	private static final long serialVersionUID = 1L;
	private final UserDAO dao = new UserDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        User user = mapper.readValue(req.getInputStream(), User.class);
        try {
            user.setPassword(PasswordUtil.hash(user.getPassword()));
            if (user.getRole() == null) user.setRole("USER");

            dao.save(user);
            resp.setStatus(201);
        } catch (Exception e) {
            resp.setStatus(500);
            resp.getWriter().write("Registration failed");
        }
    }
}
