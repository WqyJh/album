package com.wqy.album;

// TODO: 16-12-6 To Use Enum to implement it.
public class StatusCode {

    /**
     * 用户名被占用
     */
    public static final int USERNAME_TAKEN = 1;

    /**
     * 用户名为空
     */
    public static final int USERNAME_MISSING = 2;

    /**
     * 密码为空
     */
    public static final int PASSWORD_MISSING = 3;

    /**
     * 用户名或密码错误
     */
    public static final int USERNAME_PASSWORD_MISMATCH = 4;

    /**
     * 用户名不存在
     */
    public static final int USER_NOT_FOUND = 5;

    public static final int SUCCESS = 6;

    public static final int SERVER_ERROR = 7;

    public static final int UNKNOWN_ERROR = 8;

    /**
     * 已登录
     */
    public static final int LOGIN_SUCCESS = 9;

    /**
     * 未登录
     */
    public static final int LOGIN_FAILED = 10;

    public static final int FILE_DUPLICATED = 11;
}
