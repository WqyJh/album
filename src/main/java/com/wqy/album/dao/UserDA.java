package com.wqy.album.dao;

import com.wqy.album.StatusCode;
import com.wqy.album.StatusException;
import com.wqy.album.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;


/**
 * Created by wqy on 16-11-30.
 */
public class UserDA {
    private static final Logger logger = LogManager.getLogger(UserDA.class);

    private static final String SQL_CREATE_USER = "INSERT INTO User(username, password) VALUES('%s', '%s')";
    private static final String SQL_FIND_USER = "SELECT password FROM User WHERE username='%s'";
    private static final String SQL_UPDATE_USER = "UPDATE User SET password='%s' WHERE username='%s'";
    private static final String SQL_DELETE_USER = "DELETE FROM User WHERE username='%s'";

    private static final String DB_URI = "jdbc:mysql://localhost:3306/album";
    private static final String DB_USERNAME = "album";
    private static final String DB_PASSWORD = "tcjw412";

    private static Connection connection = null;
    private static Statement statement = null;

    public static void initialize() throws StatusException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
        }
        try {
            connection = DriverManager.getConnection(DB_URI, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
        }
    }

    public static void terminate() throws StatusException {
        try {
            statement.close();
        } catch (SQLException e) {
            throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new StatusException(e.getMessage(), e.getCause(), StatusCode.SERVER_ERROR);
        }
    }

    private static boolean checkString(String s) {
        return s == null ? false : s.length() != 0;
    }

    public static void checkUser(User user) throws StatusException {
        if (user == null || !checkString(user.getUsername())) {
            throw new StatusException(StatusCode.USERNAME_MISSING);
        }
        if (!checkString(user.getPassword())) {
            throw new StatusException(StatusCode.PASSWORD_MISSING);
        }
    }

    public static void create(User user) throws StatusException {
        checkUser(user);
        String sql = String.format(SQL_CREATE_USER, user.getUsername(), user.getPassword());
        logger.debug("create", sql);
        System.out.println(sql);
        try {
            int res = statement.executeUpdate(sql);
            System.out.println(res);
            if (res <= 0) {
                throw new StatusException(StatusCode.USERNAME_TAKEN);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new StatusException(StatusCode.SERVER_ERROR);
        }
    }

    public static User find(String username) throws StatusException {
        checkString(username);
        String sql = String.format(SQL_FIND_USER, username);
        logger.debug("find", sql);
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.first()) {
                String password = resultSet.getString("password");
                return new User(username, password);
            } else {
                throw new StatusException(StatusCode.USER_NOT_FOUND);
            }
        } catch (SQLException e) {
            throw new StatusException(StatusCode.SERVER_ERROR);
        }
    }

    public static boolean find(User user) throws StatusException {
        checkUser(user);
        User u = find(user.getUsername());
        return u.getPassword().equals(user.getPassword());
    }

    public static void update(User user) throws StatusException {
        checkUser(user);
        String sql = String.format(SQL_UPDATE_USER, user.getPassword(), user.getUsername());
        System.out.println(sql);
        logger.debug("update", sql);
        try {
            int res = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(User user) throws StatusException {
        find(user);
        String sql = String.format(SQL_DELETE_USER, user.getUsername());
        try {
            int res = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
