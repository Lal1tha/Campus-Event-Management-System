package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.campus.model.Announcement;
import com.campus.util.DateUtil;

public class AnnouncementDAO extends BaseDAO {
    public void create(Announcement announcement) throws SQLException {
        String sql = "INSERT INTO announcements(title,message,audience,created_by) VALUES(?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, announcement.getTitle());
            ps.setString(2, announcement.getMessage());
            ps.setString(3, announcement.getAudience());
            ps.setInt(4, announcement.getCreatedBy());
            ps.executeUpdate();
        }
    }

    public List<Announcement> search(String keyword, String audience) throws SQLException {
        String sql = "SELECT * FROM announcements WHERE (title LIKE ? OR message LIKE ?) AND (audience=? OR audience='ALL') ORDER BY created_at DESC";
        List<Announcement> list = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + (keyword == null ? "" : keyword) + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, audience);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Announcement a = new Announcement();
                    a.setId(rs.getInt("id"));
                    a.setTitle(rs.getString("title"));
                    a.setMessage(rs.getString("message"));
                    a.setAudience(rs.getString("audience"));
                    a.setCreatedBy(rs.getInt("created_by"));
                    a.setCreatedAt(DateUtil.toLocalDateTime(rs.getTimestamp("created_at")));
                    list.add(a);
                }
            }
        }
        return list;
    }
}
