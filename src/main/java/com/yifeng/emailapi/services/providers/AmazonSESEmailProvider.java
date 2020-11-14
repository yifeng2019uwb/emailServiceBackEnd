package com.yifeng.emailapi.services.providers;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.simpleemail.model.Content;

import com.yifeng.emailapi.config.emailprovider.AmazonSESConfiguration;
import com.yifeng.emailapi.exceptions.BadRequestException;
import com.yifeng.emailapi.exceptions.EmailServiceProviderException;
import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
@Qualifier("primaryEmailServiceProvider")
@Slf4j
public class AmazonSESEmailProvider implements EmailServiceProvider {

    private final AmazonSimpleEmailService amazonSimpleEmailService;

    @Autowired
    public AmazonSESEmailProvider(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }


    @Override
    public EmailResponse sendEmail(EmailRequest email) throws EmailServiceProviderException {

        try {
            SendEmailResult response = sendAmazonSESRequest(email);
            return new EmailResponse(response.getSdkHttpMetadata().getHttpStatusCode(), response.getMessageId());

        }catch (Exception e) {
            log.error("An error occurred while sending via amazon ses: " + e.getMessage());
//            https://docs.aws.amazon.com/ses/latest/DeveloperGuide/using-ses-api-error-codes.html
//            error: badrequest or ServiceUnavailable
            if (e.getMessage().contains("Status Code: 400") || e.getMessage().contains("Status Code: 404")) {
                throw new BadRequestException(e.getMessage().substring(0, e.getMessage().indexOf(("("))));
            }else {
                throw new EmailServiceProviderException(e.getMessage());
            }
        }

    }


    private SendEmailResult sendAmazonSESRequest(EmailRequest email) throws IOException {

        Destination destination = new Destination().withToAddresses(new String[]{email.getRecipient()});
        Content subject = new Content().withData(email.getSubject());
        Content textBody = new Content().withData(email.getContext());
        Body body = new Body().withText(textBody);
        Message message = new Message().withSubject(subject).withBody(body);
        SendEmailRequest request = new SendEmailRequest()
                .withSource(email.getSender())
                .withDestination(destination)
                .withMessage(message);

        return  amazonSimpleEmailService.sendEmail(request);
    }

}
