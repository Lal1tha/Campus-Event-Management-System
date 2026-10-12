package com.campus.controller;

import java.io.IOException;

import com.campus.dao.AnnouncementDAO;
import com.campus.model.Announcement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/announcements")
public class AnnouncementServlet extends BaseServlet {
    private final AnnouncementDAO announcementDAO = new AnnouncementDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("announcements", announcementDAO.search(req.getParameter("q"), "ALL"));
        } catch (Exception ex) {
            error(req, ex.getMessage());
        }
        view(req, resp, "admin/announcements");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Announcement announcement = new Announcement();
            announcement.setTitle(req.getParameter("title"));
            announcement.setMessage(req.getParameter("message"));
            announcement.setAudience(req.getParameter("audience"));
            announcement.setCreatedBy(sessionUserId(req));
            announcementDAO.create(announcement);
            resp.sendRedirect(req.getContextPath() + "/admin/announcements");
        } catch (Exception ex) {
            error(req, ex.getMessage());
            doGet(req, resp);
        }
    }
}
