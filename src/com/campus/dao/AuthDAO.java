package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.campus.model.Role;

public class AuthDAO extends BaseDAO {
    public AuthRecord findByEmailAndRole(String email, Role role) throws SQLException {
        String table = switch (role) {
            case ADMIN -> "admins";
            case STUDENT -> "students";
            case COORDINATOR -> "coordinators";
        };
        String sql = "SELECT id, name, email, password_hash, active FROM " + table + " WHERE email = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new AuthRecord(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                            rs.getString("password_hash"), rs.getBoolean("active"), role);
                }
            }
        }
        return null;
    }

    public record AuthRecord(int id, String name, String email, String passwordHash, boolean active, Role role) {
    }
}
