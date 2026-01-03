package com.example.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.model.User;
import com.example.utils.DBUtil;

@ExtendWith(MockitoExtension.class)
public class UserDAOTest {
	 @Mock
	    Connection connectionMock;

	    @Mock
	    Statement statementMock;

	    @Mock
	    PreparedStatement preparedStatementMock;

	    @Mock
	    ResultSet resultSetMock;

	    @InjectMocks
	    UserDAO dao;
	    
	    @Test
	    void testAllUserDAOMethods() throws Exception {
	    	try(MockedStatic<DBUtil> dbUtilMocked = mockStatic(DBUtil.class)){
	    		dbUtilMocked.when(DBUtil::getConnection).thenReturn(connectionMock);
	    		
	    		when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
	    		when(preparedStatementMock.executeUpdate()).thenReturn(1);
	    		dao.save(new User());
	    		verify(preparedStatementMock, times(1)).executeUpdate();
	    		
	    		
	    		
	    		
	    		reset(preparedStatementMock);
	            when(connectionMock.prepareStatement(anyString())).thenReturn(preparedStatementMock);
	            when(preparedStatementMock.executeQuery()).thenReturn(resultSetMock);
	            when(resultSetMock.next()).thenReturn(true);
	            when(resultSetMock.getString("username")).thenReturn("naveen");
	            User findResult = dao.findByUsername("naveen");
	            assertNotNull(findResult);
	    	}
	    }

}
