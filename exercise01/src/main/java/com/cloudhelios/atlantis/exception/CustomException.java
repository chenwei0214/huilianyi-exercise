package com.cloudhelios.atlantis.exception;

/**
 * author: chenwei
 * createDate: 18-8-27 下午7:07
 * description:
 */
public class CustomException extends RuntimeException{
    private String code;

    private String message;

    public CustomException(){

    }

    public CustomException(String code,String message){
        super(message);
        this.code=code;
        this.message=message;
    }

    public CustomException(String message,Throwable cause){
        super(message,cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
