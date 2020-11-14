package com.yifeng.emailapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailServiceProviderException extends RuntimeException{

    public EmailServiceProviderException() {
        super();
    }

    public EmailServiceProviderException(String message) {
        super(message);
    }

    public EmailServiceProviderException(Throwable cause) {

    }
}
