package com.campus.controller;

import java.io.IOException;

import com.campus.dao.AuthDAO.AuthRecord;
import com.campus.model.Role;
import com.campus.service.AuthService;
import com.campus.util.AppConstants;
import com.campus.util.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends BaseServlet {
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        view(req, resp, "common/login");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            Role role = Role.valueOf(req.getParameter("role"));
            if (!ValidationUtil.isEmail(email) || ValidationUtil.isBlank(password)) {
                throw new IllegalArgumentException("Enter a valid email and password.");
            }
            AuthRecord user = authService.authenticate(email, password, role, req.getRemoteAddr());
            if (user == null) {
                throw new IllegalArgumentException("Invalid credentials or inactive account.");
            }
            HttpSession session = req.getSession(true);
            session.setMaxInactiveInterval(AppConstants.SESSION_TIMEOUT_SECONDS);
            session.setAttribute(AppConstants.SESSION_USER_ID, user.id());
            session.setAttribute(AppConstants.SESSION_USER_NAME, user.name());
            session.setAttribute(AppConstants.SESSION_ROLE, role.name());
            resp.sendRedirect(req.getContextPath() + "/" + role.name().toLowerCase() + "/dashboard");
        } catch (Exception ex) {
            error(req, ex.getMessage());
            view(req, resp, "common/login");
        }
    }
}
