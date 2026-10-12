package com.campus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.campus.model.Event;
import com.campus.util.DateUtil;

public class EventDAO extends BaseDAO {
    public int create(Event event) throws SQLException {
        String sql = "INSERT INTO events(title,description,category,venue,event_date,start_time,end_time,capacity,coordinator_id,status) VALUES(?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindEvent(ps, event);
            ps.setString(10, event.getStatus());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : 0;
            }
        }
    }

    public void update(Event event) throws SQLException {
        String sql = "UPDATE events SET title=?,description=?,category=?,venue=?,event_date=?,start_time=?,end_time=?,capacity=?,coordinator_id=? WHERE id=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            bindEvent(ps, event);
            ps.setInt(10, event.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement("DELETE FROM events WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void approve(int id) throws SQLException {
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement("UPDATE events SET status='APPROVED' WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Event findById(int id) throws SQLException {
        String sql = "SELECT e.*, c.name coordinator_name FROM events e LEFT JOIN coordinators c ON c.id=e.coordinator_id WHERE e.id=?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public List<Event> search(String keyword, String category, boolean onlyApproved) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT e.*, c.name coordinator_name FROM events e LEFT JOIN coordinators c ON c.id=e.coordinator_id WHERE 1=1");
        if (onlyApproved) {
            sql.append(" AND e.status='APPROVED'");
        }
        sql.append(" AND (?='' OR e.title LIKE ? OR e.venue LIKE ?)");
        sql.append(" AND (?='' OR e.category=?) ORDER BY e.event_date ASC");
        List<Event> events = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql.toString())) {
            String key = keyword == null ? "" : keyword;
            String like = "%" + key + "%";
            String cat = category == null ? "" : category;
            ps.setString(1, key);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, cat);
            ps.setString(5, cat);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    events.add(map(rs));
                }
            }
        }
        return events;
    }

    public List<Event> findByCoordinator(int coordinatorId) throws SQLException {
        String sql = "SELECT e.*, c.name coordinator_name FROM events e LEFT JOIN coordinators c ON c.id=e.coordinator_id WHERE e.coordinator_id=? ORDER BY e.event_date";
        List<Event> events = new ArrayList<>();
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, coordinatorId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    events.add(map(rs));
                }
            }
        }
        return events;
    }

    private void bindEvent(PreparedStatement ps, Event event) throws SQLException {
        ps.setString(1, event.getTitle());
        ps.setString(2, event.getDescription());
        ps.setString(3, event.getCategory());
        ps.setString(4, event.getVenue());
        ps.setDate(5, DateUtil.toSqlDate(event.getEventDate()));
        ps.setString(6, event.getStartTime());
        ps.setString(7, event.getEndTime());
        ps.setInt(8, event.getCapacity());
        ps.setInt(9, event.getCoordinatorId());
    }

    private Event map(ResultSet rs) throws SQLException {
        Event event = new Event();
        event.setId(rs.getInt("id"));
        event.setTitle(rs.getString("title"));
        event.setDescription(rs.getString("description"));
        event.setCategory(rs.getString("category"));
        event.setVenue(rs.getString("venue"));
        event.setEventDate(DateUtil.toLocalDate(rs.getDate("event_date")));
        event.setStartTime(rs.getString("start_time"));
        event.setEndTime(rs.getString("end_time"));
        event.setCapacity(rs.getInt("capacity"));
        event.setCoordinatorId(rs.getInt("coordinator_id"));
        event.setCoordinatorName(rs.getString("coordinator_name"));
        event.setStatus(rs.getString("status"));
        event.setCreatedAt(DateUtil.toLocalDateTime(rs.getTimestamp("created_at")));
        return event;
    }
}
