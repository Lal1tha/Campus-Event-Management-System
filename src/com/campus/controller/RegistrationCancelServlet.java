package com.campus.controller;

import java.io.IOException;

import com.campus.dao.RegistrationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/registrations/cancel")
public class RegistrationCancelServlet extends BaseServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            registrationDAO.cancel(Integer.parseInt(req.getParameter("id")), sessionUserId(req));
        } catch (Exception ex) {
            req.getSession().setAttribute("flash", ex.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/student/dashboard");
    }
}
