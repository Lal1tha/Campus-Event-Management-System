package com.campus.controller;

import java.io.IOException;

import com.campus.dao.RegistrationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/coordinator/participants")
public class ParticipantsServlet extends BaseServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("registrations", registrationDAO.findByEvent(Integer.parseInt(req.getParameter("eventId"))));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "coordinator/participants");
    }
}
