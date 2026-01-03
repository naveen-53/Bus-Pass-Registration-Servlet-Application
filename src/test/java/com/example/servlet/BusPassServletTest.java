package com.example.servlet;

import com.example.model.BusPass;
import com.example.service.BusPassService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;       
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BusPassServletTest {

    private BusPassServlet servlet;
    private BusPassService service;
    private HttpServletRequest req;
    private HttpServletResponse resp;
    private StringWriter writer;

    @BeforeEach
    void setup() throws Exception {
        servlet = new BusPassServlet();
        
        service = mock(BusPassService.class);

        Field field = BusPassServlet.class.getDeclaredField("service");
        field.setAccessible(true);
        field.set(servlet, service);

        req = mock(HttpServletRequest.class);
        resp = mock(HttpServletResponse.class);

        writer = new StringWriter();
        when(resp.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Test
    
    void testGetAll() throws Exception {
        when(req.getPathInfo()).thenReturn(null);

        when(service.getAll()).thenReturn(List.of(
                createPass(1, "Naveen"),
                createPass(2, "Raja")
        ));

        servlet.doGet(req, resp);

        verify(service).getAll();

        String output = writer.toString();
        assertTrue(output.contains("Naveen"));
        assertTrue(output.contains("Raja"));
    }

    @Test
    void testGetById() throws Exception {
        when(req.getPathInfo()).thenReturn("/5");
        when(service.get(5)).thenReturn(createPass(5, "Alex"));

        servlet.doGet(req, resp);

        verify(service).get(5);
        assertTrue(writer.toString().contains("Alex"));
    }

    @Test
    void testCreate() throws Exception {
        BusPass pass = createPass(10, "Sam");
        String json = new ObjectMapper().writeValueAsString(pass);

        when(req.getInputStream())
                .thenReturn(new TestServletInputStream(new ByteArrayInputStream(json.getBytes())));

        servlet.doPost(req, resp);

        ArgumentCaptor<BusPass> captor = ArgumentCaptor.forClass(BusPass.class);
        verify(service).create(captor.capture());

        assertEquals("Sam", captor.getValue().getName());
        verify(resp).setStatus(201);
    }

    @Test
    void testUpdate() throws Exception {
        when(req.getPathInfo()).thenReturn("/8");

        BusPass pass = createPass(8, "Updated");
        String json = new ObjectMapper().writeValueAsString(pass);

        when(req.getInputStream())
                .thenReturn(new TestServletInputStream(new ByteArrayInputStream(json.getBytes())));

        servlet.doPut(req, resp);

        verify(service).update(eq(8), any(BusPass.class));
        verify(resp).setStatus(200);
    }

    @Test
    void testDelete() throws Exception {
        when(req.getPathInfo()).thenReturn("/9");

        servlet.doDelete(req, resp);

        verify(service).delete(9);
        verify(resp).setStatus(200);
    }

    private BusPass createPass(int id, String name) {
        BusPass pass = new BusPass();
        pass.setId(id);
        pass.setName(name);
        pass.setEmail(name.toLowerCase() + "@mail.com");
        pass.setPassType("Monthly");
        pass.setStartDate("2024-01-01");
        pass.setEndDate("2024-12-31");
        return pass;
    }

    static class TestServletInputStream extends ServletInputStream {

        private final InputStream stream;

        TestServletInputStream(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {}
    }
}
