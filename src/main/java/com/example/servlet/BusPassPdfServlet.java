package com.example.servlet;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.model.BusPass;
import com.example.service.BusPassPdfService;
import com.example.service.BusPassService;
import com.example.service.BusPassServiceImpl;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BusPassPdfServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(BusPassPdfServlet.class);

    private static final long serialVersionUID = 1L;

    private final BusPassService busPassService = new BusPassServiceImpl();
    private final BusPassPdfService pdfService = new BusPassPdfService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // example: /5/pdf  OR /5
        String path = req.getPathInfo(); // mapped like /passesPdf/*

        if (path == null || path.equals("/") || !path.matches("^/\\d+$")) {
            resp.setStatus(400);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Invalid URL. Use /passesPdf/{id}\"}");
            return;
        }

        int id = Integer.parseInt(path.substring(1));

        BusPass pass = busPassService.get(id);

        if (pass == null) {
            resp.setStatus(404);
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"Bus pass not found\"}");
            return;
        }

        resp.setContentType("application/pdf");
        resp.setHeader("Content-Disposition", "inline; filename=BusPass_" + id + ".pdf");

        pdfService.generateBusPassPdf(pass, resp.getOutputStream());

        LOG.info("PDF generated for bus pass id: " + id);
    }
}
