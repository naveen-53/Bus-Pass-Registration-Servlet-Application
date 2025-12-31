package com.example.service;


import com.example.dao.BusPassDAO;
import com.example.model.BusPass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestBusPassMethods {

    BusPassServiceImpl service;
    BusPassDAO daoMock;

    @BeforeEach
    void setUp() throws Exception {
        service = new BusPassServiceImpl();
        daoMock = mock(BusPassDAO.class);
        Field field = BusPassServiceImpl.class.getDeclaredField("dao");
        field.setAccessible(true);
        field.set(service, daoMock);
    }

    @Test
    void testAllMethods() throws Exception {
        BusPass mockPass = mock(BusPass.class);
        when(mockPass.getName()).thenReturn("TestPass");
        List<BusPass> mockList = List.of(mockPass);
        
        when(daoMock.findAll()).thenReturn(mockList);
        when(daoMock.findById(anyInt())).thenReturn(mockPass);
        doNothing().when(daoMock).save(any(BusPass.class));
        doNothing().when(daoMock).update(any(BusPass.class));
        doNothing().when(daoMock).delete(anyInt());
        
        List<BusPass> allResult = service.getAll();
        assertEquals(mockList, allResult, "getAll should return list from DAO");
        
        BusPass getResult = service.get(1);
        assertEquals(mockPass, getResult, "get should return BusPass from DAO");
        
        service.create(mockPass);
        verify(daoMock).save(mockPass);
        
        BusPass updatePass = new BusPass();
        service.update(2, updatePass);
        verify(daoMock).update(updatePass);
        
        service.delete(3);
        verify(daoMock).delete(3);
    }
}
