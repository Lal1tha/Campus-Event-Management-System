package com.campus.controller;

import java.io.IOException;

import com.campus.dao.AnnouncementDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/student/announcements")
public class StudentAnnouncementsServlet extends BaseServlet {
    private final AnnouncementDAO announcementDAO = new AnnouncementDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("announcements", announcementDAO.search(req.getParameter("q"), "STUDENT"));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "student/announcements");
    }
}
