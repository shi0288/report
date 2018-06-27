package org.advert.report.util;

/**
 * Created by shiqm on 2018-06-14.
 */
public enum ResultCode implements Code{

    OK(10000, "成功"),
    OVER(9999, "未知错误"),
    ERROR_CLOUD(100, "微服务通信错误"),
    ERROR_PARAMS(101, "参数错误");

    private int code;
    private String msg;

    private ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toString() {
        return "ResultCode{code=" + this.code + ", msg='" + this.msg + '\'' + '}';
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
