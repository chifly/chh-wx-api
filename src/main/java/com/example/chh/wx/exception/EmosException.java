package com.example.chh.wx.exception;

import lombok.Data;


/**
 * @author apple
 * 异常类
 */
@Data
public class EmosException extends RuntimeException{
    private int code;
    private String msg;

    public EmosException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public EmosException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public EmosException(String msg, int code) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public EmosException(String msg, Throwable e, int code) {
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }
}
