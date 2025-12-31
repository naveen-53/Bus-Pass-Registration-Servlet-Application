package com.example.service;

import com.example.dao.BusPassDAO;
import com.example.model.BusPass;

import java.util.List;

public class BusPassServiceImpl implements BusPassService {

    BusPassDAO dao = new BusPassDAO();

    @Override
    public List<BusPass> getAll() {
        try { 
        	return dao.findAll(); 
        }
        catch (Exception e) { 
        	throw new RuntimeException(e); 
        	}
    }

    @Override
    public BusPass get(int id) {
        try { 
        	return dao.findById(id); 
        }
        catch (Exception e) { 
        	throw new RuntimeException(e); 
        }
    }

    @Override
    public void create(BusPass pass) {
        try { 
        	dao.save(pass); 
        }
        catch (Exception e) { 
        	throw new RuntimeException(e); 
        }
    }

    @Override
    public void update(int id, BusPass pass) {
        try {
            pass.setId(id);
            dao.update(pass);
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    @Override
    public void delete(int id) {
        try { dao.delete(id); }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
