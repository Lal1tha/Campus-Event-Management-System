package com.campus.controller;

import java.io.IOException;

import com.campus.dao.StudentDAO;
import com.campus.model.Student;
import com.campus.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/profile")
public class StudentProfileServlet extends BaseServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("student", studentDAO.findById(sessionUserId(req)));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "student/profile");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (!ValidationUtil.isPhone(req.getParameter("phone"))) {
                throw new IllegalArgumentException("Enter a valid phone number.");
            }
            Student student = studentDAO.findById(sessionUserId(req));
            student.setName(req.getParameter("name"));
            student.setPhone(req.getParameter("phone"));
            student.setDepartment(req.getParameter("department"));
            student.setSemester(Integer.parseInt(req.getParameter("semester")));
            student.setActive(true);
            studentDAO.update(student);
            message(req, "Profile updated.");
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        doGet(req, resp);
    }
}
