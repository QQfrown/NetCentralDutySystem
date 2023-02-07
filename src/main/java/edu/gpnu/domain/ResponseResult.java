package edu.gpnu.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.net.HttpURLConnection;

/**
 * @Author 丘丘
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息，如果有错误时，前端可以获取该字段进行提示
     */
    private String msg;
    /**
     * 查询到的结果数据，
     */
    private T data;

    public void requestNormal(T data){
        if (data != null){
            this.setCode(HttpURLConnection.HTTP_OK);
            this.setData(data);
            this.setMsg("request success");
        }else {
            this.setCode(HttpURLConnection.HTTP_FORBIDDEN);
            this.setMsg("request error");
        }
    }

    public void operationError(){
        this.setCode(HttpURLConnection.HTTP_FORBIDDEN);
        this.setMsg("request error!!!");
    }

    public void integerRequestNormal(T data){
        if ((Integer) data == 0){
            this.operationError();
        }else {
            this.requestNormal(data);
        }
    }


    public ResponseResult() {
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
