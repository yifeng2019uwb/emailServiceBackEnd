package com.yifeng.emailapi.config.emailprovider;


import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


@Component
@Validated
public class SendGridConfiguration {

    @NotEmpty
    private final String sendgridApiKey = "";

//    private String sendgridApiKey;


    public String getApiKey() {
        return sendgridApiKey;
    }

//    public void setApiKey(String apiKey) {
//        this.sendgridApiKey = apiKey;
//    }


}
