package com.example.chh.wx.config.xss;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author apple
 * 抵御XSS攻击
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String val = super.getParameter(name);
        if (!StrUtil.hasEmpty(val)) {
            val = HtmlUtil.filter(val);
        }
        return val;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                String temp = values[i];
                if (!StrUtil.hasEmpty(temp)) {
                    temp = HtmlUtil.filter(temp);
                }
                values[i] = temp;
            }
        }
        return values;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> results = new LinkedHashMap<>();
        if (map != null) {
            for (String key : map.keySet()) {
                String[] values = map.get(key);
                for (int i = 0; i < values.length; i++) {
                    String temp = values[i];
                    if (!StrUtil.hasEmpty(temp)) {
                        temp = HtmlUtil.filter(temp);
                    }
                    values[i] = temp;
                 }
                results.put(key, values);
            }
        }
        return results;
    }

    @Override
    public String getHeader(String name) {
        String val = super.getHeader(name);
        if (!StrUtil.hasEmpty(val)) {
            val = HtmlUtil.filter(val);
        }
        return val;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        InputStream in = super.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, Charset.forName("UTF-8"));
        BufferedReader buffer = new BufferedReader(reader);
        StringBuffer body = new StringBuffer();
        String line = buffer.readLine();
        while (line != null) {
            body.append(line);
            line = buffer.readLine();
        }
        buffer.close();
        reader.close();
        in.close();
        Map<String, Object> map = JSONUtil.parseObj(body.toString());
        Map<String, Object> result = new LinkedHashMap<>();
        if (map != null) {
            for (String key : map.keySet()) {
                Object val = map.get(key);
                if (val instanceof String) {
                    if (!StrUtil.hasEmpty(val.toString())) {
                        result.put(key, HtmlUtil.filter(val.toString()));
                    }
                } else {
                    result.put(key, val);
                }
            }
        }

        String json = JSONUtil.toJsonStr(result);
        ByteArrayInputStream bain = new ByteArrayInputStream(json.getBytes());
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bain.read();
            }
        };
    }
}
