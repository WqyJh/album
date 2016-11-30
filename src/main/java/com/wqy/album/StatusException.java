package com.wqy.album;

/**
 * Created by wqy on 16-11-30.
 */
public class StatusException extends Exception {
    private int code;

    public StatusException(int code) {
        super();
        this.code = code;
    }

    public StatusException(String message, int code) {
        super(message);
        this.code = code;
    }

    public StatusException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
