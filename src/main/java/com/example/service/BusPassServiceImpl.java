package com.example.service;

import com.example.dao.BusPassDAO;
import com.example.model.BusPass;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BusPassServiceImpl implements BusPassService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BusPassServiceImpl.class);


    BusPassDAO dao = new BusPassDAO();

    @Override
    public List<BusPass> getAll() {
        try { 
        	List<BusPass> list =  dao.findAll();
        	LOG.info("Called Find All method");
        	return list;
        	
        }
        catch (Exception e) { 
        	throw new RuntimeException(e); 
        	}
    }

    @Override
    public BusPass get(int id) {
        try { 
        	BusPass pass =  dao.findById(id);
        	LOG.info(pass.getName()+" retrived from table");
        	return pass;
        }
        catch (Exception e) { 
        	throw new RuntimeException(e); 
        }
    }

    @Override
    public void create(BusPass pass) {
        try { 
        	dao.save(pass);
        	LOG.info("BusPass Registered");
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
            LOG.info("BusPass Id : "+id+" Updated...");
        } catch (Exception e) { 
        	throw new RuntimeException(e); 
        }
    }

    @Override
    public void delete(int id) {
        try { 
        	dao.delete(id); 
        	LOG.info("BusPass Id : "+id+" Deleted..");
        }
        catch (Exception e) { throw new RuntimeException(e); }
    }
}
