package com.campus.controller;

import java.io.IOException;

import com.campus.dao.RegistrationDAO;
import com.campus.dao.AttendanceDAO;
import com.campus.model.EventRegistration;
import com.campus.service.CertificateService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/coordinator/certificates/generate")
public class GenerateCertificatesServlet extends BaseServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final CertificateService certificateService = new CertificateService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int eventId = Integer.parseInt(req.getParameter("eventId"));
        try {
            for (EventRegistration registration : registrationDAO.findByEvent(eventId)) {
                if (attendanceDAO.exists(registration.getId())) {
                    certificateService.generateIfMissing(registration.getId(), getServletContext().getRealPath("/"));
                }
            }
        } catch (Exception ex) {
            req.getSession().setAttribute("flash", ex.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/coordinator/participants?eventId=" + eventId);
    }
}
