package com.example.servlet;

import com.example.model.BusPass;
import com.example.service.BusPassService;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusPassServletTest {

    @Mock
    HttpServletRequest requestMock;

    @Mock
    HttpServletResponse responseMock;

    @Mock
    BusPassService serviceMock;

    @InjectMocks
    BusPassServlet servlet;

    @Test
    void testAllServletMethods() throws Exception {
        MockServletInputStream mockInputStream = new MockServletInputStream("{}".getBytes());
        
        when(requestMock.getPathInfo()).thenReturn(null);
        when(serviceMock.getAll()).thenReturn(List.of(new BusPass()));
        servlet.doGet(requestMock, responseMock);
        verify(serviceMock).getAll();

        when(requestMock.getPathInfo()).thenReturn("/1");
        when(serviceMock.get(1)).thenReturn(new BusPass());
        servlet.doGet(requestMock, responseMock);
        verify(serviceMock).get(1);

        when(requestMock.getInputStream()).thenReturn(mockInputStream);
        servlet.doPost(requestMock, responseMock);
        verify(serviceMock).create(any(BusPass.class));
        verify(responseMock).setStatus(201);

        when(requestMock.getPathInfo()).thenReturn("/2");
        when(requestMock.getInputStream()).thenReturn(mockInputStream);
        servlet.doPost(requestMock, responseMock);
        servlet.doPut(requestMock, responseMock);
        verify(serviceMock).update(2, any(BusPass.class));
        verify(responseMock).setStatus(200);

        servlet.doDelete(requestMock, responseMock);
        verify(serviceMock).delete(2);
    }

    private static class MockServletInputStream extends ServletInputStream {
        private final ByteArrayInputStream inputStream;

        MockServletInputStream(byte[] bytes) {
            this.inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(jakarta.servlet.ReadListener readListener) {}
    }
}
