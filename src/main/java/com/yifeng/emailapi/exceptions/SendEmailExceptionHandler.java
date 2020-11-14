package com.yifeng.emailapi.exceptions;

import com.yifeng.emailapi.model.EmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice("com.yifeng")
@Slf4j
public class SendEmailExceptionHandler {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex, WebRequest request) {

        String errorMessage;
        if (ex.getMessage() != null) {
            errorMessage = ex.getMessage();
        } else {
            errorMessage = "Your request was not valid (check your sender or recipient address) ";
        }

        return new ResponseEntity<Object>(new EmailResponse(400, errorMessage), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = EmailServiceProviderException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex, WebRequest request) {
        String errorMessage = "Email Service Unavailable";
        // Hiding the detail of the internal error
//        if (ex.getMessage() != null) {
//            errorMessage = ex.getMessage();
//        }

        return new ResponseEntity<Object>(new EmailResponse(500, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //This exception handler for all other exceptions
    @ExceptionHandler(value = ServiceUnavailableException.class)
    public ResponseEntity<Object> handleExecutionException(Exception ex, WebRequest request) {
        log.error("Error while processing request " + request.getDescription(true), ex);
        String errorMessage = "Internal exception";

        return new ResponseEntity<Object>(new EmailResponse(500, errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
