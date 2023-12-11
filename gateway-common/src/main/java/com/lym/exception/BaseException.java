package com.lym.exception;


import com.lym.enums.ResponseCode;

public class BaseException extends RuntimeException {

    public BaseException() {
    }

    protected ResponseCode code;

    public BaseException(String message, ResponseCode code) {
        super(message);
        this.code = code;
    }

    public BaseException(String message, Throwable cause, ResponseCode code) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(ResponseCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public BaseException(String message, Throwable cause,
                         boolean enableSuppression, boolean writableStackTrace, ResponseCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public ResponseCode getCode() {
        return code;
    }

}