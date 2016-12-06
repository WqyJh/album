package com.wqy.album.util;

import java.util.Random;

/**
 * Created by wqy on 16-11-30.
 */
public class Common {
    private static String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // TODO: 16-12-6 Find a more efficient way to implement it.
    public static String makeString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(possible.charAt((int) (random.nextFloat() * possible.length())));
        }
        return sb.toString();
    }
}
