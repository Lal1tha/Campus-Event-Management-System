package com.campus.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import com.campus.util.DBConnection;

public class DashboardService {
    public Map<String, Integer> statistics() throws Exception {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("Students", count("students"));
        stats.put("Coordinators", count("coordinators"));
        stats.put("Events", count("events"));
        stats.put("Registrations", count("event_registrations"));
        stats.put("Attendance", count("attendance"));
        return stats;
    }

    private int count(String table) throws Exception {
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM " + table);
                ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
