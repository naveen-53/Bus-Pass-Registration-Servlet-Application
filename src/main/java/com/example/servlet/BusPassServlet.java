package com.example.servlet;

import com.example.model.BusPass;
import com.example.service.BusPassService;
import com.example.service.BusPassServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/passes")
public class BusPassServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final BusPassService service = new BusPassServiceImpl();
    private final  ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");

        String path = req.getPathInfo();

        if (path == null || path.equals("/")) {
            mapper.writeValue(resp.getWriter(),service.getAll());
        } else {
            int id = Integer.parseInt(path.substring(1));
            mapper.writeValue(resp.getWriter(),service.get(id));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BusPass pass = readBody(req);
        service.create(pass);
        resp.setStatus(201);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        BusPass pass = readBody(req);
        service.update(id, pass);
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getPathInfo().substring(1));
        service.delete(id);
        resp.setStatus(200);
    }

    private BusPass readBody(HttpServletRequest req) throws IOException {
        return mapper.readValue(req.getInputStream(), BusPass.class);
    }
}
