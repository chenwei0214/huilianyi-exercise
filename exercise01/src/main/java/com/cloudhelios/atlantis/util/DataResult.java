package com.cloudhelios.atlantis.util;

/**
 * author: chenwei
 * createDate: 18-8-27 下午5:54
 * description:
 */
public class DataResult {
    private Integer status;
    private String message;
    private Object data;

    public DataResult() {

    }

    public DataResult(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public DataResult(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static DataResult ok(){
        return new DataResult(200,"ok");
    }

    public static DataResult ok(Object data){
        return new DataResult(200,"ok",data);
    }

    public static DataResult build(Integer status,String message){
        return new DataResult(status,message);
    }

    public static DataResult build(Integer status,String message,Object data){
        return new DataResult(status,message,data);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "DataResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
