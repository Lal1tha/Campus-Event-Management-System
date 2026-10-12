package com.campus.filter;

import java.io.IOException;
import java.util.Set;

import com.campus.util.AppConstants;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
    private static final Set<String> PUBLIC_PATHS = Set.of("/", "/index.jsp", "/login", "/student/register", "/forgot-password");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getServletPath();
        boolean staticResource = path.startsWith("/assets/") || path.startsWith("/qr/") || path.startsWith("/certificates/");
        if (PUBLIC_PATHS.contains(path) || staticResource) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute(AppConstants.SESSION_USER_ID) == null) {
            res.sendRedirect(req.getContextPath() + "/login?expired=true");
            return;
        }
        chain.doFilter(request, response);
    }
}
