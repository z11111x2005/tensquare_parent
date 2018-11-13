package entity;

/**
 * 请求返回
 */
public class Result {
    /**
     * 是否成功
     */
    private boolean falg;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private Object data;

    public Result() {
    }

    public Result(boolean falg, Integer code, String message) {
        this.falg = falg;
        this.code = code;
        this.message = message;
    }

    public Result(boolean falg, Integer code, String message, Object data) {
        this.falg = falg;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isFalg() {
        return falg;
    }

    public void setFalg(boolean falg) {
        this.falg = falg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
