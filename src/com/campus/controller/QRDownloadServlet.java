package com.campus.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.campus.dao.RegistrationDAO;
import com.campus.model.EventRegistration;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/qr-download")
public class QRDownloadServlet extends BaseServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            EventRegistration registration = registrationDAO.findById(Integer.parseInt(req.getParameter("id")));
            if (registration == null || registration.getStudentId() != sessionUserId(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            File file = new File(getServletContext().getRealPath("/"), registration.getQrImagePath());
            resp.setContentType("image/png");
            resp.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            Files.copy(file.toPath(), resp.getOutputStream());
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
