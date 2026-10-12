package com.campus.controller;

import java.io.IOException;

import com.campus.dao.EventDAO;
import com.campus.service.DashboardService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends BaseServlet {
    private final DashboardService dashboardService = new DashboardService();
    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("stats", dashboardService.statistics());
            req.setAttribute("events", eventDAO.search("", "", false));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/dashboard");
    }
}
