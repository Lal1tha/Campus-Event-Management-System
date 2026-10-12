package com.campus.controller;

import java.io.IOException;
import java.time.LocalDate;

import com.campus.dao.CoordinatorDAO;
import com.campus.dao.EventDAO;
import com.campus.model.Event;
import com.campus.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/events/create")
public class EventCreateServlet extends BaseServlet {
    private final EventDAO eventDAO = new EventDAO();
    private final CoordinatorDAO coordinatorDAO = new CoordinatorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("coordinators", coordinatorDAO.findAll());
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/event-form");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Event event = bind(req);
            if (!ValidationUtil.isFutureOrToday(event.getEventDate())) {
                throw new IllegalArgumentException("Event date cannot be in the past.");
            }
            event.setStatus("PENDING");
            eventDAO.create(event);
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } catch (Exception ex) {
            error(req, ex.getMessage());
            doGet(req, resp);
        }
    }

    static Event bind(HttpServletRequest req) {
        Event event = new Event();
        event.setId(req.getParameter("id") == null ? 0 : Integer.parseInt(req.getParameter("id")));
        event.setTitle(req.getParameter("title"));
        event.setDescription(req.getParameter("description"));
        event.setCategory(req.getParameter("category"));
        event.setVenue(req.getParameter("venue"));
        event.setEventDate(LocalDate.parse(req.getParameter("eventDate")));
        event.setStartTime(req.getParameter("startTime"));
        event.setEndTime(req.getParameter("endTime"));
        event.setCapacity(Integer.parseInt(req.getParameter("capacity")));
        event.setCoordinatorId(Integer.parseInt(req.getParameter("coordinatorId")));
        return event;
    }
}
