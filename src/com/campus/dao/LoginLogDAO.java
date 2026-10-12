package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginLogDAO extends BaseDAO {
    public void save(int userId, String role, String ipAddress, boolean success) throws SQLException {
        String sql = "INSERT INTO login_logs(user_id, role, ip_address, success) VALUES(?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, role);
            ps.setString(3, ipAddress);
            ps.setBoolean(4, success);
            ps.executeUpdate();
        }
    }
}
