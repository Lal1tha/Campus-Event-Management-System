package com.campus.controller;

import java.io.IOException;

import com.campus.util.AppConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {
    protected void view(HttpServletRequest req, HttpServletResponse resp, String jsp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + jsp + ".jsp").forward(req, resp);
    }

    protected int sessionUserId(HttpServletRequest req) {
        Object value = req.getSession().getAttribute(AppConstants.SESSION_USER_ID);
        return value instanceof Integer ? (Integer) value : Integer.parseInt(String.valueOf(value));
    }

    protected void message(HttpServletRequest req, String text) {
        req.setAttribute("message", text);
    }

    protected void error(HttpServletRequest req, String text) {
        req.setAttribute("error", text);
    }
}
