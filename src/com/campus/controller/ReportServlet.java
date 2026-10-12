package com.campus.controller;

import java.io.IOException;

import com.campus.dao.ReportDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/reports")
public class ReportServlet extends BaseServlet {
    private final ReportDAO reportDAO = new ReportDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("type", req.getParameter("type") == null ? "events" : req.getParameter("type"));
            req.setAttribute("rows", reportDAO.runReport(req.getParameter("type")));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/reports");
    }
}
