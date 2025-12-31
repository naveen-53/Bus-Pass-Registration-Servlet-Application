package com.example.dao;

import com.example.model.BusPass;
import com.example.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusPassDAO {

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

    public void save(BusPass p) throws Exception {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO bus_pass(name,email,pass_type,start_date,end_date) VALUES (?,?,?,?,?)");
        ps.setString(1, p.getName());
        ps.setString(2, p.getEmail());
        ps.setString(3, p.getPassType());
        ps.setString(4, p.getStartDate());
        ps.setString(5, p.getEndDate());
        ps.executeUpdate();
        con.close();
    }

    public void update(BusPass p) throws Exception {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE bus_pass SET name=?, email=?, pass_type=?, start_date=?, end_date=? WHERE id=?");
        ps.setString(1, p.getName());
        ps.setString(2, p.getEmail());
        ps.setString(3, p.getPassType());
        ps.setString(4, p.getStartDate());
        ps.setString(5, p.getEndDate());
        ps.setInt(6, p.getId());
        ps.executeUpdate();
        con.close();
    }

    public void delete(int id) throws Exception {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement("DELETE FROM bus_pass WHERE id=?");
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
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
