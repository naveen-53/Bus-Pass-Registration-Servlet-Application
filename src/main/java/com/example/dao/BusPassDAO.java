package com.example.dao;

import com.example.model.BusPass;
import com.example.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusPassDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(BusPassDAO.class);

	public List<BusPass> findAll() throws Exception {
        Connection con = DBUtil.getConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM bus_pass");

        List<BusPass> list = new ArrayList<>();
        while (rs.next()) {
            BusPass p = map(rs);
            list.add(p);
        }
        con.close();
        LOG.info("Select Query Executed");
        return list;
    }

    public BusPass findById(int id) throws Exception {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM bus_pass WHERE id=?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        BusPass p = null;
        if (rs.next()) p = map(rs);

        con.close();
        return p;
    }

    public void save(BusPass p) {
        try(Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO bus_pass(name,email,pass_type,start_date,end_date) VALUES (?,?,?,?,?)")){
        	
        	ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getPassType());
            ps.setString(4, p.getStartDate());
            ps.setString(5, p.getEndDate());
            int row = ps.executeUpdate();
            if(row!=0) {
            	LOG.info("BusPass Values Inserted");
            }
            else {
            	LOG.error("BusPass Values are not inserted into table");
            }
            con.close();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public void update(BusPass p) {
        try(Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE bus_pass SET name=?, email=?, pass_type=?, start_date=?, end_date=? WHERE id=?")){
        	
        	ps.setString(1, p.getName());
            ps.setString(2, p.getEmail());
            ps.setString(3, p.getPassType());
            ps.setString(4, p.getStartDate());
            ps.setString(5, p.getEndDate());
            ps.setInt(6, p.getId());
            int row = ps.executeUpdate();
            if(row!=0) {
            	LOG.info(p.getName()+"'s pass updated");
            }
            else {
            	LOG.error("BusPass Values doesn't updated");
            }
            con.close();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    public void delete(int id) throws Exception {
        try( Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM bus_pass WHERE id=?")){
        	
        	ps.setInt(1, id);
            int row = ps.executeUpdate();
            if(row!=0) {
            	LOG.info(id+"'s pass deleted");
            }
            else {
            	LOG.error("BusPass Values doesn't deleted");
            }
            con.close();
        }
        
    }

    private BusPass map(ResultSet rs) throws Exception {
        BusPass p = new BusPass();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setEmail(rs.getString("email"));
        p.setPassType(rs.getString("pass_type"));
        p.setStartDate(rs.getString("start_date"));
        p.setEndDate(rs.getString("end_date"));
        return p;
    }
}
