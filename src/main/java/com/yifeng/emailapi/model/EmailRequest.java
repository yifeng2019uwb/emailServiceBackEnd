package com.yifeng.emailapi.model;

import io.swagger.annotations.ApiModel;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


//@ApiModel(description = "JSON body of the send email request")
public class EmailRequest {

    //  Limitations in SendGrid API which refer to
//    https://sendgrid.com/docs/API_Reference/Web_API_v3/Mail/index.html#:~:text=The%20total%20size%20of%20your,include%20in%20the%20personalizations%20array.


    @Email
    @NotBlank
    private final String sender;


    @Email
    @NotBlank
    private final String recipient;

    // this size is recommedated by somewhere?
    // gmail subject limitation is 255
    @NotEmpty
    @Size(max = 78)
    private final String subject;

    @NotEmpty
    @Size(max = 10000)
    private final String context;


    public EmailRequest(@Email @NotBlank String sender, @Email @NotBlank String recipient, @NotEmpty @Size(max = 78) String subject, @NotEmpty @Size(max = 10000) String context) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.context = context;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "EmailRequest{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", context='" + context + '\'' +
                '}';
    }
}
