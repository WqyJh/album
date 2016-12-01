package com.wqy.album.dao;

import com.wqy.album.StatusCode;
import com.wqy.album.StatusException;
import com.wqy.album.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class UserDA {
    private static final Logger logger = LogManager.getLogger(UserDA.class);

    private static final String SQL_CREATE_USER = "INSERT INTO User(username, password) VALUES(?, ?)";
    private static final String SQL_FIND_USER = "SELECT _id, password FROM User WHERE username = ?";
    private static final String SQL_UPDATE_USER = "UPDATE User SET password = ? WHERE username = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM User WHERE username = ?";
    private static final String SQL_FIND_BY_ID = "SELECT username, password FROM User WHERE _id = ?";

    private static PreparedStatement createUserStat;
    private static PreparedStatement findUserStat;
    private static PreparedStatement updateUserStat;
    private static PreparedStatement deleteUserStat;
    private static PreparedStatement findByIdStat;

    public static void initialize() throws SQLException {
        Connection connection = DBManager.getConnection();
        createUserStat = connection.prepareStatement(SQL_CREATE_USER);
        findUserStat = connection.prepareStatement(SQL_FIND_USER);
        updateUserStat = connection.prepareStatement(SQL_UPDATE_USER);
        deleteUserStat = connection.prepareStatement(SQL_DELETE_USER);
        findByIdStat = connection.prepareStatement(SQL_FIND_BY_ID);
    }

    public static void terminate() throws SQLException {
        createUserStat.close();
        findUserStat.close();
        updateUserStat.close();
        deleteUserStat.close();
        findByIdStat.close();
    }

    private static boolean checkString(String s) {
        return s == null ? false : s.length() != 0;
    }

    public static int checkUser(User user) {
        if (user == null || !checkString(user.getUsername())) {
            return StatusCode.USERNAME_MISSING;
        }
        if (!checkString(user.getPassword())) {
            return StatusCode.PASSWORD_MISSING;
        }
        return StatusCode.SUCCESS;
    }

    public static int create(User user) {
        int userChecked = checkUser(user);
        if (userChecked != StatusCode.SUCCESS) {
            return userChecked;
        }

        try {
            createUserStat.setString(1, user.getUsername());
            createUserStat.setString(2, user.getPassword());
            int res = createUserStat.executeUpdate();
            if (res > 0) {
                return StatusCode.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return StatusCode.USERNAME_TAKEN;
        }
        return StatusCode.UNKNOWN_ERROR;
    }

    public static User find(String username) {
        if (!checkString(username)) {
            return null;
        }

        try {
            findUserStat.setString(1, username);
            ResultSet resultSet = findUserStat.executeQuery();
            if (resultSet.first()) {
                int _id = resultSet.getInt("_id");
                String password = resultSet.getString("password");

                User user = new User(username, password);
                user.setId(_id);
                return user;
            }
        } catch (SQLException e) {
//            throw new StatusException(StatusCode.SERVER_ERROR);
            e.printStackTrace();
        }

        return null;
    }

    public static int find(User user) {
        int userChecked = checkUser(user);
        if (userChecked != StatusCode.SUCCESS) {
            return userChecked;
        }

        User u = find(user.getUsername());
        if (u != null && u.getPassword().equals(user.getPassword())) {
            return StatusCode.SUCCESS;
        } else {
            return StatusCode.USER_NOT_FOUND;
        }
    }

    public static User find(int id) {
        if (id <= 0) {
            return null;
        }

        try {
            findByIdStat.setInt(1, id);
            ResultSet rs = findByIdStat.executeQuery();
            if (rs.first()) {
                String username = rs.getString("username");
                String password = rs.getString("password");

                User user = new User(username, password);
                user.setId(id);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int update(User user) {
        int userChecked = checkUser(user);
        if (userChecked != StatusCode.SUCCESS) {
            return userChecked;
        }

        try {
            updateUserStat.setString(1, user.getPassword());
            updateUserStat.setString(2, user.getUsername());
            int res = updateUserStat.executeUpdate();
            if (res > 0) {
                return StatusCode.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return StatusCode.USER_NOT_FOUND;
    }

    public static int delete(User user) throws StatusException {
        int userFound = find(user);
        if (userFound != StatusCode.SUCCESS) {
            return userFound;
        }

        try {
            deleteUserStat.setString(1, user.getUsername());
            int res = deleteUserStat.executeUpdate();
            if (res > 0) {
                return StatusCode.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return StatusCode.USER_NOT_FOUND;
    }
}
