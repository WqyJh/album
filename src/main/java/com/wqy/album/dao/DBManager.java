package com.wqy.album.dao;

import com.wqy.album.StatusCode;
import com.wqy.album.StatusException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by wqy on 16-12-1.
 */
public class DBManager {
    private static Connection connection = null;

    public static void initialize(String dbConfigFile) throws StatusException {
        DBConfig dbConfig = DBConfig.getDBConfig(dbConfigFile);
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
