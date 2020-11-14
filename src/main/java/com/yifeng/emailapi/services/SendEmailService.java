package com.yifeng.emailapi.services;

import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;



public interface SendEmailService {
    EmailResponse sendEmail(EmailRequest email) throws Exception;
}
