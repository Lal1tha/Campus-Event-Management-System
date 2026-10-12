package com.campus.service;

import java.sql.SQLException;

import com.campus.dao.AuthDAO;
import com.campus.dao.AuthDAO.AuthRecord;
import com.campus.dao.LoginLogDAO;
import com.campus.model.Role;
import com.campus.util.BCryptUtil;

public class AuthService {
    private final AuthDAO authDAO = new AuthDAO();
    private final LoginLogDAO loginLogDAO = new LoginLogDAO();

    public AuthRecord authenticate(String email, String password, Role role, String ipAddress) throws SQLException {

        AuthRecord record = authDAO.findByEmailAndRole(email, role);

        System.out.println("Email entered: " + email);
        System.out.println("Role: " + role);

        if (record == null) {
            System.out.println("User not found");
        } else {
            System.out.println("DB Email: " + record.email());
            System.out.println("DB Hash: " + record.passwordHash());
            System.out.println("Active: " + record.active());
            System.out.println("Password Match: " + BCryptUtil.verify(password, record.passwordHash()));
        }

        boolean success = record != null
                && record.active()
                && BCryptUtil.verify(password, record.passwordHash());

        loginLogDAO.save(record == null ? 0 : record.id(), role.name(), ipAddress, success);

        return success ? record : null;
    }
}