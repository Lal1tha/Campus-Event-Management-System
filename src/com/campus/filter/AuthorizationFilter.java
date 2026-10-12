package com.campus.filter;

import java.io.IOException;

import com.campus.util.AppConstants;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String role = (String) req.getSession().getAttribute(AppConstants.SESSION_ROLE);
        String path = req.getServletPath();
        if ("/student/register".equals(path)) {
            chain.doFilter(request, response);
            return;
        }
        if ((path.startsWith("/admin/") && !AppConstants.ROLE_ADMIN.equals(role))
                || (path.startsWith("/student/") && !AppConstants.ROLE_STUDENT.equals(role))
                || (path.startsWith("/coordinator/") && !AppConstants.ROLE_COORDINATOR.equals(role))) {
            res.sendRedirect(req.getContextPath() + "/access-denied.jsp");
            return;
        }
        chain.doFilter(request, response);
    }
}
