package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campus.model.EventRegistration;
import com.campus.util.DateUtil;

public class RegistrationDAO extends BaseDAO {
    public int create(int studentId, int eventId) throws SQLException {
        String sql = "INSERT INTO event_registrations(student_id,event_id,status) VALUES(?,?,'REGISTERED')";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, studentId);
            ps.setInt(2, eventId);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public boolean hasActiveRegistration(int studentId, int eventId) throws SQLException {
        String sql = "SELECT id FROM event_registrations WHERE student_id=? AND event_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setInt(2, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
     }
   

    public void saveQr(int registrationId, String payload, String imagePath) throws SQLException {
        String sql = "UPDATE event_registrations SET qr_code=?, qr_image_path=? WHERE id=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, payload);
            ps.setString(2, imagePath);
            ps.setInt(3, registrationId);
            ps.executeUpdate();
        }
    }

    public void cancel(int registrationId, int studentId) throws SQLException {

        String sql = "DELETE FROM event_registrations WHERE id=? AND student_id=?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, registrationId);
            ps.setInt(2, studentId);

            ps.executeUpdate();
        }
    }

    public EventRegistration findByQrPayload(String payload) throws SQLException {
        String sql = baseSelect() + " WHERE r.qr_code=? AND r.status='REGISTERED'";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, payload);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public EventRegistration findById(int id) throws SQLException {
        String sql = baseSelect() + " WHERE r.id=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public List<EventRegistration> findByStudent(int studentId) throws SQLException {
        return list(baseSelect() + " WHERE r.student_id=? ORDER BY r.registered_at DESC", studentId);
    }

    public List<EventRegistration> findByEvent(int eventId) throws SQLException {
        return list(baseSelect() + " WHERE r.event_id=? AND r.status='REGISTERED' ORDER BY s.name", eventId);
    }

    public List<EventRegistration> search(String keyword) throws SQLException {
        String sql = baseSelect() + " WHERE s.name LIKE ? OR s.usn LIKE ? OR e.title LIKE ? ORDER BY r.registered_at DESC";
        List<EventRegistration> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    private List<EventRegistration> list(String sql, int parameter) throws SQLException {
        List<EventRegistration> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, parameter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    private String baseSelect() {
        return "SELECT r.*, s.name student_name, s.usn, e.title event_title FROM event_registrations r JOIN students s ON s.id=r.student_id JOIN events e ON e.id=r.event_id";
    }

    private EventRegistration map(ResultSet rs) throws SQLException {
        EventRegistration registration = new EventRegistration();
        registration.setId(rs.getInt("id"));
        registration.setStudentId(rs.getInt("student_id"));
        registration.setEventId(rs.getInt("event_id"));
        registration.setStudentName(rs.getString("student_name"));
        registration.setUsn(rs.getString("usn"));
        registration.setEventTitle(rs.getString("event_title"));
        registration.setStatus(rs.getString("status"));
        registration.setQrCode(rs.getString("qr_code"));
        registration.setQrImagePath(rs.getString("qr_image_path"));
        registration.setRegisteredAt(DateUtil.toLocalDateTime(rs.getTimestamp("registered_at")));
        return registration;
    }
}
