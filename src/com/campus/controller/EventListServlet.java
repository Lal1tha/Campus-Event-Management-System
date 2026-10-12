package com.campus.controller;

import java.io.IOException;

import com.campus.dao.EventDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/events")
public class EventListServlet extends BaseServlet {
    private final EventDAO eventDAO = new EventDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("events", eventDAO.search(req.getParameter("q"), req.getParameter("category"), true));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "student/events");
    }
}
