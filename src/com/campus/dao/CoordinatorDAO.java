package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campus.model.Coordinator;

public class CoordinatorDAO extends BaseDAO {
    public int create(Coordinator coordinator) throws SQLException {
        String sql = "INSERT INTO coordinators(name,email,phone,department,password_hash,active) VALUES(?,?,?,?,?,1)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, coordinator.getName());
            ps.setString(2, coordinator.getEmail());
            ps.setString(3, coordinator.getPhone());
            ps.setString(4, coordinator.getDepartment());
            ps.setString(5, coordinator.getPasswordHash());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public List<Coordinator> findAll() throws SQLException {
        List<Coordinator> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement("SELECT * FROM coordinators ORDER BY name");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public void update(Coordinator coordinator) throws SQLException {
        String sql = "UPDATE coordinators SET name=?, phone=?, department=?, active=? WHERE id=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, coordinator.getName());
            ps.setString(2, coordinator.getPhone());
            ps.setString(3, coordinator.getDepartment());
            ps.setBoolean(4, coordinator.isActive());
            ps.setInt(5, coordinator.getId());
            ps.executeUpdate();
        }
    }

    private Coordinator map(ResultSet rs) throws SQLException {
        Coordinator coordinator = new Coordinator();
        coordinator.setId(rs.getInt("id"));
        coordinator.setName(rs.getString("name"));
        coordinator.setEmail(rs.getString("email"));
        coordinator.setPhone(rs.getString("phone"));
        coordinator.setDepartment(rs.getString("department"));
        coordinator.setPasswordHash(rs.getString("password_hash"));
        coordinator.setActive(rs.getBoolean("active"));
        return coordinator;
    }
}
