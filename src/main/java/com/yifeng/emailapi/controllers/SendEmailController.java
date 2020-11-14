package com.yifeng.emailapi.controllers;


import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;
import com.yifeng.emailapi.services.SendEmailService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@Slf4j
public class SendEmailController {

    private final SendEmailService emailService;

    @Autowired
    public SendEmailController(SendEmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping(value = "/api/emails",
            method = RequestMethod.POST,
            consumes = "application/json")
    @ApiOperation("send email service")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Your request has been dispatched for sending"),
            @ApiResponse(code = 400, message = "Your request was not well formed (perhaps body was missing?)"),
            @ApiResponse(code = 500, message = "An unexpected error we've failed to handle")
    })
    public ResponseEntity<EmailResponse> sendEmail(@Valid @RequestBody EmailRequest request) throws Exception {

            return new ResponseEntity<>(emailService.sendEmail(request), HttpStatus.ACCEPTED);

    }


}
