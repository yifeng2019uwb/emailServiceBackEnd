package com.yifeng.emailapi.controllers;

import com.yifeng.emailapi.exceptions.BadRequestException;
import com.yifeng.emailapi.exceptions.EmailServiceProviderException;
import com.yifeng.emailapi.model.EmailRequest;
import com.yifeng.emailapi.model.EmailResponse;
import com.yifeng.emailapi.services.SendEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    private static final String VALID_REQUEST_BODY = "{ \"sender\":\"yifeng2019@gmail.com\",  \"recipient\":\"elvazhangyf@mail.com\",  \"subject\":\"Subject\",  \"context\":\"Context\" }";
    private static final String INVALID_REQUEST_BODY = "{ }";

    private static final EmailResponse EMAIL_RESPONSE = new EmailResponse(202, "");

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private SendEmailController sendEmailController;

    @MockBean
    private SendEmailService  sendEmailService;

    @BeforeEach
    public void configMock() {
        mockMvc = standaloneSetup(sendEmailController).build();
    }

    // Test the isAccepted Case
    @Test
    public void validEmailRequestTest() throws Exception {
        when(sendEmailService.sendEmail(any(EmailRequest.class))).thenReturn(EMAIL_RESPONSE);
        mockMvc.perform(post("/api/emails")
                .contentType(MediaType.APPLICATION_JSON)
                    .content(VALID_REQUEST_BODY))
                .andExpect(status().isAccepted());

    }


    // Test the Bad request with not well formed request - missing sender, recipient, subject or context
    @Test
    public void invalidEmailRequestWithMissingDataTest() throws Exception {
        mockMvc.perform(post("/api/emails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_REQUEST_BODY))
                .andExpect(status().isBadRequest());

    }

    // Test the service return Bad Request - sender/recipent is not valid
    @Test
    public void invalidEmailRequestWithInvalidSenderOrRecipientTest() throws Exception {
        when(sendEmailService.sendEmail(any(EmailRequest.class)))
                .thenThrow(BadRequestException.class);
        mockMvc.perform(post("/api/emails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_REQUEST_BODY))
                .andExpect(status().isBadRequest());

    }

    // Test the service is Down
    @Test
    public void emailProvidersFailedTest() throws Exception {
        when(sendEmailService.sendEmail(any(EmailRequest.class)))
                .thenThrow(EmailServiceProviderException.class);
        mockMvc.perform(post("/api/emails")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_REQUEST_BODY))
                .andExpect(status().isInternalServerError());

    }


}
