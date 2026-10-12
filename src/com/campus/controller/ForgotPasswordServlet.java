package com.campus.controller;

import java.io.IOException;

import com.campus.dao.StudentDAO;
import com.campus.util.BCryptUtil;
import com.campus.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends BaseServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        view(req, resp, "common/forgot-password");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            if (!ValidationUtil.isEmail(email) || !ValidationUtil.isStrongPassword(password)) {
                throw new IllegalArgumentException("Enter a valid email and strong new password.");
            }
            studentDAO.updatePassword(email, BCryptUtil.hash(password));
            message(req, "Password updated for student account. Login with the new password.");
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "common/forgot-password");
    }
}
