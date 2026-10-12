package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campus.model.Student;
import com.campus.util.DateUtil;

public class StudentDAO extends BaseDAO {
    public int create(Student student) throws SQLException {
        String sql = "INSERT INTO students(name, usn, email, phone, department, semester, password_hash, active) VALUES(?,?,?,?,?,?,?,1)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getUsn());
            ps.setString(3, student.getEmail());
            ps.setString(4, student.getPhone());
            ps.setString(5, student.getDepartment());
            ps.setInt(6, student.getSemester());
            ps.setString(7, student.getPasswordHash());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public boolean existsByEmailOrUsn(String email, String usn) throws SQLException {
        String sql = "SELECT id FROM students WHERE email = ? OR usn = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, usn);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public List<Student> search(String keyword) throws SQLException {
        String sql = "SELECT * FROM students WHERE name LIKE ? OR usn LIKE ? OR email LIKE ? ORDER BY created_at DESC";
        List<Student> students = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(map(rs));
                }
            }
        }
        return students;
    }

    public void update(Student student) throws SQLException {
        String sql = "UPDATE students SET name=?, phone=?, department=?, semester=?, active=? WHERE id=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getPhone());
            ps.setString(3, student.getDepartment());
            ps.setInt(4, student.getSemester());
            ps.setBoolean(5, student.isActive());
            ps.setInt(6, student.getId());
            ps.executeUpdate();
        }
    }

    public void updatePassword(String email, String hash) throws SQLException {
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement("UPDATE students SET password_hash=? WHERE email=?")) {
            ps.setString(1, hash);
            ps.setString(2, email);
            ps.executeUpdate();
        }
    }

    private Student map(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setUsn(rs.getString("usn"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setDepartment(rs.getString("department"));
        student.setSemester(rs.getInt("semester"));
        student.setPasswordHash(rs.getString("password_hash"));
        student.setActive(rs.getBoolean("active"));
        student.setCreatedAt(DateUtil.toLocalDateTime(rs.getTimestamp("created_at")));
        return student;
    }
}
