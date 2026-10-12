package com.campus.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.campus.util.DBConnection;

public abstract class BaseDAO {
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
}
