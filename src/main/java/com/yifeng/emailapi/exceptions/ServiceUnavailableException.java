package com.yifeng.emailapi.exceptions;


public class ServiceUnavailableException extends RuntimeException{
    public ServiceUnavailableException() {
    }

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
