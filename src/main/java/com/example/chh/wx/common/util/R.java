package com.example.chh.wx.common.util;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * @author apple
 * 对web返回消息的封装，为了防止多种格式消息码太混乱
 */
public class R extends HashMap<String, Object> {
    public R() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public static R ok() {
        return new R();
    }
    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }
    public static R ok(Map<String, Object> maps) {
        R r = new R();
        r.putAll(maps);
        return r;
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }
    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }
    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知错误，请联系开发者");
    }
}
