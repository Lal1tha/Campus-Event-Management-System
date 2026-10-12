package com.campus.controller;

import java.io.IOException;

import com.campus.dao.EventDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/coordinator/dashboard")
public class CoordinatorDashboardServlet extends BaseServlet {
    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("events", eventDAO.findByCoordinator(sessionUserId(req)));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "coordinator/dashboard");
    }
}
