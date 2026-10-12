package com.campus.controller;

import java.io.IOException;

import com.campus.dao.AttendanceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/attendance")
public class ViewAttendanceServlet extends BaseServlet {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String eventId = req.getParameter("eventId");
            req.setAttribute("attendance", eventId == null || eventId.isBlank() ? java.util.List.of()
                    : attendanceDAO.findByEvent(Integer.parseInt(eventId)));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/attendance");
    }
}
