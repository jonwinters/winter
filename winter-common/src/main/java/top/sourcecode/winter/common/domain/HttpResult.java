package top.sourcecode.winter.common.domain;

import java.util.HashMap;

/**
 * Created by mountain on 8/6/16.
 */
public class HttpResult {

    private String msg;
    private boolean status;
    private String code;
    private Object data;

    public static HttpResult success(Object data) {
        HttpResult result = new HttpResult();
        result.setStatus(true);
        if(data == null) {
            data = new HashMap<>();
        }
        result.setData(data);
        result.setMsg("");
        result.setCode("");
        return result;
    }

    public static HttpResult success() {
        return success(null);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
