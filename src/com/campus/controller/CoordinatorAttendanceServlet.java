package com.campus.controller;

import java.io.IOException;

import com.campus.dao.AttendanceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/coordinator/attendance")
public class CoordinatorAttendanceServlet extends BaseServlet {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("attendance", attendanceDAO.findByEvent(Integer.parseInt(req.getParameter("eventId"))));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "coordinator/attendance");
    }
}
