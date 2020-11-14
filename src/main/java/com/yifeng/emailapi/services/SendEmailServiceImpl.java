package com.yifeng.emailapi.services;

import com.yifeng.emailapi.exceptions.BadRequestException;
import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;
import com.yifeng.emailapi.services.providers.EmailServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SendEmailServiceImpl implements SendEmailService {

    private final EmailServiceProvider primaryEmailServiceProvider;
    private final EmailServiceProvider secondaryEmailServiceProvider;


    @Autowired
    public SendEmailServiceImpl( @Qualifier("primaryEmailServiceProvider") EmailServiceProvider primaryEmailServiceProvider,
                                 @Qualifier("secondaryEmailServiceProvider") EmailServiceProvider secondaryEmailServiceProvider) {
        this.primaryEmailServiceProvider = primaryEmailServiceProvider;
        this.secondaryEmailServiceProvider = secondaryEmailServiceProvider;
    }

    @Override
    public EmailResponse sendEmail(EmailRequest email) throws Exception {
        EmailResponse response = null;

        try {
            response = sendEmailViaServiceProvider(primaryEmailServiceProvider, email);
            return response;
        }catch (Exception e) {
            if (e.getClass().equals(BadRequestException.class)) {
                throw new BadRequestException(e.getMessage());
            }
            log.warn("Primary provider has failed, falling back to secondary provider");
            return sendEmailViaServiceProvider(secondaryEmailServiceProvider, email);
        }

    }

    private EmailResponse sendEmailViaServiceProvider(EmailServiceProvider provider, EmailRequest email) {
        return provider.sendEmail(email);
    }
}
