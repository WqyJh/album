package com.wqy.album;

/**
 * Created by wqy on 16-11-30.
 */
public class StatusCode {

    /**
     * 用户名被占用
     */
    public static final int USERNAME_TAKEN = 101;

    /**
     * 用户名为空
     */
    public static final int USERNAME_MISSING = 201;

    /**
     * 密码为空
     */
    public static final int PASSWORD_MISSING = 202;

    /**
     * 用户名或密码错误
     */
    public static final int USERNAME_PASSWORD_MISMATCH = 203;

    /**
     * 用户名不存在
     */
    public static final int USER_NOT_FOUND = 204;

    public static final int SUCCESS = 200;

    public static final int SERVER_ERROR = 500;

    public static final int UNKNOWN_ERROR = 501;
}
