package com.bootdo.shop;

/**
 * Created by Administrator on 2017/11/29 0029.
 */
public class Constan {

    public static final Constan LOGIN = new Constan(1, "登录");
    public static final Constan REGISTER = new Constan(5, "注册");
    public static final Constan SUBMITORDER = new Constan(2, "提交订单");
    private int code;
    private String msg;

    public Constan() {
    }

    public Constan(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public Constan setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Constan setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
