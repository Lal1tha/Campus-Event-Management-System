package com.campus.controller;

import java.io.IOException;

import com.campus.dao.AttendanceDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/attendance")
public class StudentAttendanceServlet extends BaseServlet {
    private final AttendanceDAO attendanceDAO = new AttendanceDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("attendance", attendanceDAO.findByStudent(sessionUserId(req)));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "student/attendance");
    }
}
