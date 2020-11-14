package com.yifeng.emailapi.services.providers;


import com.sendgrid.*;
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
@Qualifier("secondaryEmailServiceProvider")
@Slf4j
public class SendGridEmailProvider implements EmailServiceProvider {

    private final SendGrid sendGrid;

    @Autowired
    public SendGridEmailProvider(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }


    @Override
    public EmailResponse sendEmail(EmailRequest email) throws EmailServiceProviderException {
        try {
            Request request = sendSendgridRequest(email);
            Response response = sendGrid.api(request);
            return new EmailResponse(response.getStatusCode(), response.getBody());
        } catch (IOException e) {
            log.error("An error occurred while sending via sendgrid: " + e.getMessage());
//            error: badrequest or ServiceUnavailable
            if (e.getMessage().contains("status Code 400") || e.getMessage().contains("status Code 403" )) {
                throw new BadRequestException();
            }else {
                throw new EmailServiceProviderException(e.getMessage());
            }
        }

    }


    private Request sendSendgridRequest(EmailRequest email) throws IOException {
        Email from = new Email(email.getSender());
        Email to = new Email(email.getRecipient());
        Content content = new Content("text/plain", email.getContext());
        Mail mail = new Mail(from, email.getSubject(), to, content);

        Request request = new Request();

        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        return request;
    }
}
