package com.example.service;

import java.util.List;

import com.example.model.BusPass;

public interface BusPassService {
    List<BusPass> getAll();
    BusPass get(int id);
    void create(BusPass pass);
    void update(int id, BusPass pass);
    void delete(int id);
}
