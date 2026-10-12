package com.campus.controller;

import java.io.IOException;

import com.campus.model.EventRegistration;
import com.campus.service.AttendanceService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/coordinator/mark-attendance")
public class MarkAttendanceServlet extends BaseServlet {
    private final AttendanceService attendanceService = new AttendanceService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        try {
            EventRegistration registration = attendanceService.markByQr(req.getParameter("qr"), sessionUserId(req));
            resp.getWriter().write("{\"success\":true,\"student\":\"" + escape(registration.getStudentName()) + "\",\"event\":\""
                    + escape(registration.getEventTitle()) + "\"}");
        } catch (Exception ex) {
            resp.getWriter().write("{\"success\":false,\"message\":\"" + escape(ex.getMessage()) + "\"}");
        }
    }

    private String escape(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
