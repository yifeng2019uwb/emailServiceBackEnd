package com.yifeng.emailapi.services.providers;

import com.yifeng.emailapi.exceptions.EmailServiceProviderException;
import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;

public interface EmailServiceProvider {
    EmailResponse sendEmail(EmailRequest email) throws EmailServiceProviderException;
}
