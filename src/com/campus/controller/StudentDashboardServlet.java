package com.campus.controller;

import java.io.IOException;

import com.campus.dao.AnnouncementDAO;
import com.campus.dao.EventDAO;
import com.campus.dao.RegistrationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/dashboard")
public class StudentDashboardServlet extends BaseServlet {
    private final EventDAO eventDAO = new EventDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final AnnouncementDAO announcementDAO = new AnnouncementDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("events", eventDAO.search("", "", true));
            req.setAttribute("registrations", registrationDAO.findByStudent(sessionUserId(req)));
            req.setAttribute("announcements", announcementDAO.search("", "STUDENT"));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "student/dashboard");
    }
}
