package com.campus.controller;

import java.io.IOException;

import com.campus.dao.StudentDAO;
import com.campus.model.Student;
import com.campus.util.BCryptUtil;
import com.campus.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/register")
public class StudentRegisterServlet extends BaseServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        view(req, resp, "student/register");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (!ValidationUtil.isEmail(email) || !ValidationUtil.isStrongPassword(password)) {
                throw new IllegalArgumentException("Use a valid email and a password with 8 characters, uppercase letter and number.");
            }
            if (!ValidationUtil.isPhone(req.getParameter("phone"))) {
                throw new IllegalArgumentException("Enter a valid 10 digit phone number.");
            }
            if (studentDAO.existsByEmailOrUsn(email, req.getParameter("usn"))) {
                throw new IllegalArgumentException("Email or USN already registered.");
            }
            Student student = new Student();
            student.setName(req.getParameter("name"));
            student.setUsn(req.getParameter("usn"));
            student.setEmail(email);
            student.setPhone(req.getParameter("phone"));
            student.setDepartment(req.getParameter("department"));
            student.setSemester(Integer.parseInt(req.getParameter("semester")));
            student.setPasswordHash(BCryptUtil.hash(password));
            studentDAO.create(student);
            resp.sendRedirect(req.getContextPath() + "/login?registered=true");
        } catch (Exception ex) {
            error(req, ex.getMessage());
            view(req, resp, "student/register");
        }
    }
}
