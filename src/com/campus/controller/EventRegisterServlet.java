package com.campus.controller;

import java.io.IOException;

import com.campus.service.RegistrationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/events/register")
public class EventRegisterServlet extends BaseServlet {
    private final RegistrationService registrationService = new RegistrationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int eventId = Integer.parseInt(req.getParameter("eventId"));
            int registrationId = registrationService.register(sessionUserId(req), eventId, getServletContext().getRealPath("/"));
            resp.sendRedirect(req.getContextPath() + "/student/receipt?id=" + registrationId);
        } catch (Exception ex) {
            req.getSession().setAttribute("flash", ex.getMessage());
            resp.sendRedirect(req.getContextPath() + "/student/events");
        }
    }
}
