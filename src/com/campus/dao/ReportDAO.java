package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.campus.model.ReportRow;

public class ReportDAO extends BaseDAO {
    public List<ReportRow> runReport(String type) throws SQLException {
        String sql = switch (type == null ? "" : type) {
            case "attendance" -> "SELECT event_title, student_name, usn, marked_at FROM v_attendance_report";
            case "registrations" -> "SELECT event_title, student_name, usn, status, registered_at FROM v_registration_report";
            case "students" -> "SELECT name, usn, email, department, semester FROM students ORDER BY name";
            case "coordinators" -> "SELECT name, email, phone, department FROM coordinators ORDER BY name";
            default -> "SELECT title, category, venue, event_date, status FROM events ORDER BY event_date DESC";
        };
        List<ReportRow> rows = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                ReportRow row = new ReportRow();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    row.put(meta.getColumnLabel(i), rs.getString(i));
                }
                rows.add(row);
            }
        }
        return rows;
    }
}
