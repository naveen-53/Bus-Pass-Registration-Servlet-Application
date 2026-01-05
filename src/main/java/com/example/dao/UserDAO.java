package com.example.dao;

import com.example.model.User;
import com.example.utils.DBUtil;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {
	private static final Logger LOG = LoggerFactory.getLogger(UserDAO.class);


    public User findByUsername(String username) {
    	LOG.info("called findByUsername");
        try(Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=?")){
        	
        	ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            User u = null;
            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setRole(rs.getString("role"));
            }
            con.close();
            return u;
        	
        } catch (Exception e) {
			e.printStackTrace();
			return null;
		}        
    }
    
    public void save(User user) throws Exception {
        try(Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
          "INSERT INTO users(username,password,role) VALUES(?,?,?)")){
        	ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            ps.executeUpdate();
            con.close();
        }
        
    }
}
