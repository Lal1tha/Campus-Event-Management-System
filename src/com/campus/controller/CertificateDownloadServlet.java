package com.campus.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import com.campus.dao.RegistrationDAO;
import com.campus.model.Certificate;
import com.campus.model.EventRegistration;
import com.campus.service.CertificateService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/certificate")
public class CertificateDownloadServlet extends BaseServlet {
    private final CertificateService certificateService = new CertificateService();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int registrationId = Integer.parseInt(req.getParameter("registrationId"));
            EventRegistration registration = registrationDAO.findById(registrationId);
            if (registration == null || registration.getStudentId() != sessionUserId(req)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            Certificate certificate = certificateService.generateIfMissing(registrationId, getServletContext().getRealPath("/"));
            File file = new File(getServletContext().getRealPath("/"), certificate.getPdfPath());
            resp.setContentType("application/pdf");
            resp.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            Files.copy(file.toPath(), resp.getOutputStream());
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }
}
