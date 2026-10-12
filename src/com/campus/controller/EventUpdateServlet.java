package com.campus.controller;

import java.io.IOException;

import com.campus.dao.CoordinatorDAO;
import com.campus.dao.EventDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/events/update")
public class EventUpdateServlet extends BaseServlet {
    private final EventDAO eventDAO = new EventDAO();
    private final CoordinatorDAO coordinatorDAO = new CoordinatorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("event", eventDAO.findById(Integer.parseInt(req.getParameter("id"))));
            req.setAttribute("coordinators", coordinatorDAO.findAll());
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/event-form");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            eventDAO.update(EventCreateServlet.bind(req));
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } catch (Exception ex) {
            error(req, ex.getMessage());
            doGet(req, resp);
        }
    }
}
