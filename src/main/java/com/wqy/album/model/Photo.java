package com.wqy.album.model;

/**
 * Created by wqy on 16-12-1.
 */
public class Photo {
    private int _id;
    private int _userId;
    private String filename;
    private User user;

    public Photo() {}

    public Photo(String filename, User user) {
        this.filename = filename;
        this.user = user;
        this._userId = user.getId();
    }

    public Photo(String filename, int userId) {
        this.filename = filename;
        this._userId = userId;
    }

    public Photo(int id, String filename) {
        this._id = id;
        this.filename = filename;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getUserId() {
        return _userId;
    }

    public void setUserId(int userId) {
        this._userId = userId;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this._userId = user.getId();
    }
}
