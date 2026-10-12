package com.campus.controller;

import java.io.IOException;

import com.campus.dao.RegistrationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/registrations")
public class ViewRegistrationsServlet extends BaseServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("registrations", registrationDAO.search(req.getParameter("q")));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/registrations");
    }
}
