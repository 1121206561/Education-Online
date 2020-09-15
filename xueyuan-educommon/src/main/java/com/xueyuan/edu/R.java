package com.xueyuan.edu;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
//定义所有接口返回数据格式的规范
public class R {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(RestfulCode.SUCCESS);
        r.setMessage("请求成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(RestfulCode.ERROR);
        r.setMessage("请求失败");
        return r;
    }

    public static R auth() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(RestfulCode.AUTH);
        r.setMessage("请求权限不足");
        return r;
    }

    //可以进行链式编程 , 使代码更加优雅
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
