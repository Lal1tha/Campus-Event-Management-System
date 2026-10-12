package com.campus.controller;

import java.io.IOException;

import com.campus.dao.StudentDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/students")
public class ManageStudentsServlet extends BaseServlet {
    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("students", studentDAO.search(req.getParameter("q")));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/students");
    }
}
