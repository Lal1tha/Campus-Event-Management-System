package com.campus.controller;

import java.io.IOException;

import com.campus.dao.CoordinatorDAO;
import com.campus.model.Coordinator;
import com.campus.util.BCryptUtil;
import com.campus.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/coordinators")
public class ManageCoordinatorsServlet extends BaseServlet {
    private final CoordinatorDAO coordinatorDAO = new CoordinatorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("coordinators", coordinatorDAO.findAll());
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/coordinators");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Coordinator c = new Coordinator();
            c.setName(req.getParameter("name"));
            c.setEmail(req.getParameter("email"));
            c.setPhone(req.getParameter("phone"));
            c.setDepartment(req.getParameter("department"));
            if (!ValidationUtil.isEmail(c.getEmail()) || !ValidationUtil.isPhone(c.getPhone())) {
                throw new IllegalArgumentException("Enter valid coordinator email and phone.");
            }
            c.setPasswordHash(BCryptUtil.hash(req.getParameter("password")));
            coordinatorDAO.create(c);
            resp.sendRedirect(req.getContextPath() + "/admin/coordinators");
        } catch (Exception ex) {
            error(req, ex.getMessage());
            doGet(req, resp);
        }
    }
}
