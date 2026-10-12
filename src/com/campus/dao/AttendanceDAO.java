package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.campus.model.Attendance;
import com.campus.util.DateUtil;

public class AttendanceDAO extends BaseDAO {
    public boolean exists(int registrationId) throws SQLException {
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement("SELECT id FROM attendance WHERE registration_id=?")) {
            ps.setInt(1, registrationId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void mark(int registrationId, int studentId, int eventId, int markedBy) throws SQLException {
        String sql = "INSERT INTO attendance(registration_id, student_id, event_id, marked_by) VALUES(?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, registrationId);
            ps.setInt(2, studentId);
            ps.setInt(3, eventId);
            ps.setInt(4, markedBy);
            ps.executeUpdate();
        }
    }

    public List<Attendance> findByEvent(int eventId) throws SQLException {
        String sql = baseSelect() + " WHERE a.event_id=? ORDER BY a.marked_at DESC";
        return list(sql, eventId);
    }

    public List<Attendance> findByStudent(int studentId) throws SQLException {
        String sql = baseSelect() + " WHERE a.student_id=? ORDER BY a.marked_at DESC";
        return list(sql, studentId);
    }

    private List<Attendance> list(String sql, int parameter) throws SQLException {
        List<Attendance> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Attendance attendance = new Attendance();
                    attendance.setId(rs.getInt("id"));
                    attendance.setRegistrationId(rs.getInt("registration_id"));
                    attendance.setStudentId(rs.getInt("student_id"));
                    attendance.setEventId(rs.getInt("event_id"));
                    attendance.setStudentName(rs.getString("student_name"));
                    attendance.setEventTitle(rs.getString("event_title"));
                    attendance.setMarkedAt(DateUtil.toLocalDateTime(rs.getTimestamp("marked_at")));
                    attendance.setMarkedBy(rs.getInt("marked_by"));
                    list.add(attendance);
                }
            }
        }
        return list;
    }

    private String baseSelect() {
        return "SELECT a.*, s.name student_name, e.title event_title FROM attendance a JOIN students s ON s.id=a.student_id JOIN events e ON e.id=a.event_id";
    }
}
