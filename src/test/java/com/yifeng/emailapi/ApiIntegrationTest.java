package com.yifeng.emailapi;

import com.yifeng.emailapi.exceptions.BadRequestException;
import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;
import com.yifeng.emailapi.services.SendEmailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest

public class ApiIntegrationTest {
    @Autowired
    SendEmailService sendEmailService;

    private EmailRequest validRequest = new EmailRequest("yifeng2019@gmail.com", "elvazhangyf@gmail.com", "testSubject", "testContext");
    private EmailRequest invalidEmailRequest = new EmailRequest("yifeng2019@hotmail.com", "elvazhangyf@gmail.com", "testSubject", "testContext");

    // Test send the valid email via send email service
    @Test
    public void sendEmailWithValidRequest() throws Exception{
        EmailResponse response = sendEmailService.sendEmail(validRequest);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

    // Test send the valid email via send email service
    @Test(expected = BadRequestException.class)
    public void sendEmailWithInvalidEmailRequest() throws Exception{
        sendEmailService.sendEmail(invalidEmailRequest);

    }

}
