package com.wqy.album.dao;

import com.wqy.album.StatusCode;
import com.wqy.album.model.Photo;
import com.wqy.album.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlbumDA {
    private static final Logger logger = LogManager.getLogger(AlbumDA.class);
    private static final String SQL_CREATE_PHOTO = "INSERT INTO Album(userId, filename) VALUES (?, ?)";
    private static final String SQL_FIND_BY_USER = "SELECT _id, filename FROM Album WHERE userId = ?";
    private static final String SQL_FIND_BY_USER_LIMIT_OFFSET = "SELECT _id, filename FROM Album WHERE userId = ? LIMIT ? OFFSET ?";
    private static final String SQL_FIND_BY_ID = "SELECT userId, filename FROM Album WHERE _id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM Album WHERE _id = ?";
    private static final String SQL_FIND_BY_FILENAME = "SELECT _id, userId FROM Album WHERE filename = ?";

    private static PreparedStatement createPhotoStat;
    private static PreparedStatement findByUserStat;
    private static PreparedStatement findByUserLimitOffsetStat;
    private static PreparedStatement findByIdStat;
    private static PreparedStatement deleteByIdStat;
    private static PreparedStatement findByFilenameStat;

    public static void initialize() throws SQLException {
        logger.debug("initialize");
        Connection connection = DBManager.getConnection();
        createPhotoStat = connection.prepareStatement(SQL_CREATE_PHOTO);
        findByUserStat = connection.prepareStatement(SQL_FIND_BY_USER);
        findByUserLimitOffsetStat = connection.prepareStatement(SQL_FIND_BY_USER_LIMIT_OFFSET);
        findByIdStat = connection.prepareStatement(SQL_FIND_BY_ID);
        deleteByIdStat = connection.prepareStatement(SQL_DELETE_BY_ID);
        findByFilenameStat = connection.prepareStatement(SQL_FIND_BY_FILENAME);
    }

    public static void terminate() throws SQLException {
        logger.debug("terminate");
        createPhotoStat.close();
        findByUserStat.close();
        findByUserLimitOffsetStat.close();
        findByIdStat.close();
        deleteByIdStat.close();
        findByFilenameStat.close();
    }

    public static int create(Photo photo) {
        try {
            createPhotoStat.setInt(1, photo.getUserId());
            createPhotoStat.setString(2, photo.getFilename());
            int res = createPhotoStat.executeUpdate();
            if (res > 0) {
                return StatusCode.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return StatusCode.FILE_DUPLICATED;
        }
        return StatusCode.UNKNOWN_ERROR;
    }

    private static List<Photo> find(PreparedStatement stat) throws SQLException {
        ResultSet rs = stat.executeQuery();
        List list = new ArrayList(rs.getFetchSize());
        while (rs.next()) {
            int id = rs.getInt("_id");
            String filename = rs.getString("filename");
            Photo photo = new Photo(id, filename);
            list.add(photo);
        }
        rs.close();
        return list;
    }

    public static List<Photo> find(User user) {
        try {
            findByUserStat.setInt(1, user.getId());
            List<Photo> list =  find(findByUserStat);
            list.stream().forEach(photo -> photo.setUser(user));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Photo> find(User user, int limit, int offset) {
        try {
            findByUserLimitOffsetStat.setInt(1, user.getId());
            findByUserLimitOffsetStat.setInt(2, limit);
            findByUserLimitOffsetStat.setInt(3, offset);
            List<Photo> list =  find(findByUserLimitOffsetStat);
            list.forEach(photo -> photo.setUser(user));
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Photo find(int id) {
        try {
            findByIdStat.setInt(1, id);
            ResultSet rs = findByIdStat.executeQuery();
            if (rs.first()) {
                int userId = rs.getInt("userId");
                String filename = rs.getString("filename");
                Photo photo = new Photo(id, filename);
                photo.setUserId(userId);
                return photo;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Photo find(String filename) {
        try {
            findByFilenameStat.setString(1, filename);
            ResultSet rs = findByFilenameStat.executeQuery();
            if (rs.first()) {
                int _id = rs.getInt("_id");
                int userId = rs.getInt("userId");
                Photo photo = new Photo(filename, userId);
                photo.setId(_id);
                return photo;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int delete(int id) {
        try {
            deleteByIdStat.setInt(1, id);
            int res = deleteByIdStat.executeUpdate();
            if (res > 0) {
                return StatusCode.SUCCESS;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return StatusCode.UNKNOWN_ERROR;
    }
}
