package com.wqy.album.dao;

import com.wqy.album.StatusCode;
import com.wqy.album.StatusException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

// TODO: 16-12-6 Using a connection pool.
public class DBManager {
    private static Connection connection = null;
    private static DBConfig dbConfig = null;

    public static void initialize(String dbConfigFile) throws StatusException {
        dbConfig = DBConfig.getDBConfig(dbConfigFile);
        initialize(dbConfig);
    }

    public static void initialize(DBConfig dbConfig) throws StatusException {
        if (connection == null) {
            try {
                Class.forName(dbConfig.driver);
            } catch (ClassNotFoundException e) {
                throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
            }
            try {
                connection = DriverManager.getConnection(dbConfig.url, dbConfig.username, dbConfig.password);
            } catch (SQLException e) {
                throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
            }
        }
    }

    public static void terminate() throws StatusException {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
