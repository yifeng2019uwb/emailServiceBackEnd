package com.yifeng.emailapi.services;

import com.yifeng.emailapi.exceptions.BadRequestException;
import com.yifeng.emailapi.exceptions.EmailServiceProviderException;
import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;
import com.yifeng.emailapi.services.providers.EmailServiceProvider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @MockBean
    @Qualifier("primaryEmailServiceProvider")
    EmailServiceProvider primaryEmailServiceProvider;

    @MockBean
    @Qualifier("secondaryEmailServiceProvider")
    EmailServiceProvider secondaryEmailServiceProvider;

    @Mock
    private EmailRequest request;

    @Autowired
    private SendEmailService sendEmailService;

    private static final EmailResponse EMAIL_RESPONSE = new EmailResponse(202, "");


    // primary email provider working properly - accepted
    @Test
    public void primaryEmailProviderAcceptedTest() throws Exception {
        when(primaryEmailServiceProvider.sendEmail(request)).thenReturn(EMAIL_RESPONSE);

        EmailResponse response = sendEmailService.sendEmail(request);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    // primary email provider working properly - throw bad request exception
    @Test(expected = BadRequestException.class)
    public void primaryEmailProviderThrowingBadRequestExceptionTest() throws Exception {
        when(primaryEmailServiceProvider.sendEmail(request)).thenThrow(BadRequestException.class);
        sendEmailService.sendEmail(request);

    }

    // if primary provider down, seondary email provider working properly - accept
    @Test
    public void secondaryEmailProviderAcceptedTest() throws Exception {
        when(primaryEmailServiceProvider.sendEmail(request)).thenThrow(EmailServiceProviderException.class);
        when(secondaryEmailServiceProvider.sendEmail(request)).thenReturn(EMAIL_RESPONSE);

        EmailResponse response = sendEmailService.sendEmail(request);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
    }

    // if primary provider down, seondary email provider working properly - throw expception
    @Test(expected = BadRequestException.class)
    public void secondaryEmailProviderThrowingBadRequestExceptionTest() throws Exception {
        when(primaryEmailServiceProvider.sendEmail(request)).thenThrow(EmailServiceProviderException.class);
        when(secondaryEmailServiceProvider.sendEmail(request)).thenThrow(BadRequestException.class);

        sendEmailService.sendEmail(request);

    }

    // Both  email provider down - throw 500/ or other exception
    @Test(expected = EmailServiceProviderException.class)
    public void secondaryEmailProviderDownTest() throws Exception {
        when(primaryEmailServiceProvider.sendEmail(request)).thenThrow(EmailServiceProviderException.class);
        when(secondaryEmailServiceProvider.sendEmail(request)).thenThrow(EmailServiceProviderException.class);

        sendEmailService.sendEmail(request);
    }

}
