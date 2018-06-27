package org.advert.report.util;

/**
 * Created by shiqm on 2018-06-14.
 */
public class Result {

    private int code;
    private String msg;
    private Object data;

    public String toString() {
        return "Result{code=" + this.code + ", msg='" + this.msg + '\'' + ", data=" + this.data + '}';
    }

    public Result() {
        this((Code)ResultCode.OK);
    }

    public Result(Code code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }

    public Result(String massage) {
        this.code = ResultCode.OK.getCode();
        this.msg = massage;
    }

    public Result(Object data) {
        this((Code)ResultCode.OK);
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
