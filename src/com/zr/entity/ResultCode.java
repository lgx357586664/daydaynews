package com.zr.entity;

/**
 * @author: LiGX
 * @Date: 2019-03-19 下午 7:47
 */
public class ResultCode {
    private String code;
    private String message;

    public ResultCode() {
    }

    public ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
