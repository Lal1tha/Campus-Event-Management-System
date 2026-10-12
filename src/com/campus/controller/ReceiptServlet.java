package com.campus.controller;

import java.io.IOException;

import com.campus.dao.RegistrationDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/receipt")
public class ReceiptServlet extends BaseServlet {
    private final RegistrationDAO registrationDAO = new RegistrationDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("registration", registrationDAO.findById(Integer.parseInt(req.getParameter("id"))));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "student/receipt");
    }
}
