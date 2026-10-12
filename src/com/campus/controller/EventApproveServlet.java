package com.campus.controller;

import java.io.IOException;

import com.campus.dao.EventDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/events/approve")
public class EventApproveServlet extends BaseServlet {
    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            eventDAO.approve(Integer.parseInt(req.getParameter("id")));
        } catch (Exception ex) {
            req.getSession().setAttribute("flash", ex.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
    }
}
