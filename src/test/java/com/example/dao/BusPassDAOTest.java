package com.example.dao;

import com.example.model.BusPass;
import com.example.utils.DBUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusPassDAOTest {

    @Mock
    Connection connectionMock;

    @Mock
    Statement statementMock;

    @Mock
    PreparedStatement preparedStatementMock;

    @Mock
    ResultSet resultSetMock;

    @InjectMocks
    BusPassDAO dao;

    @Test
    void testAllBuspassDAOMethods() throws Exception {
        try (MockedStatic<DBUtil> dbUtilMocked = mockStatic(DBUtil.class)) {
            dbUtilMocked.when(DBUtil::getConnection).thenReturn(connectionMock);
            
            when(connectionMock.createStatement()).thenReturn(statementMock);
            when(statementMock.executeQuery("SELECT * FROM bus_pass")).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(false);
            List<BusPass> emptyList = dao.findAll();
            assertTrue(emptyList.isEmpty(), "findAll should return empty list when no results");

            reset(preparedStatementMock);
            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
            when(resultSetMock.next()).thenReturn(true);
            when(resultSetMock.getInt("id")).thenReturn(1);
            when(resultSetMock.getString("name")).thenReturn("naveen");
            BusPass findResult = dao.findById(1);
            assertNotNull(findResult, "findById should return BusPass");

            reset(preparedStatementMock);
            when(preparedStatementMock.executeUpdate()).thenReturn(1);
            dao.save(new BusPass());
            verify(preparedStatementMock, times(1)).executeUpdate();

            reset(preparedStatementMock);
            BusPass updatePass = new BusPass();
            updatePass.setId(2);
            dao.update(updatePass);
            verify(preparedStatementMock, times(1)).executeUpdate();

            reset(preparedStatementMock);
            dao.delete(3);
            verify(preparedStatementMock, times(1)).executeUpdate();
        }
    }
}
