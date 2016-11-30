package com.wqy.album.util;

/**
 * Created by wqy on 16-11-30.
 */
public class Common {
    private static String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String makeString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(possible.charAt((int) (Math.random() * possible.length())));
        }
        return sb.toString();
    }
}
