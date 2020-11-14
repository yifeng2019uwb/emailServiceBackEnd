package com.yifeng.emailapi.config.emailprovider;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Component
@Validated
public class AmazonSESConfiguration {

    @NotEmpty
    private final String awsAccessKeyId = "";
//    private String awsAccessKeyId;

    @NotEmpty
    private final String awsSecretKey = "";
//    private String awsSecretKey;

    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

//    public void setAwsAccessKeyId(String awsAccessKeyId) {
//        this.awsAccessKeyId = awsAccessKeyId;
//    }

    public String getAwsSecretKey() {
        return awsSecretKey;
    }

//    public void setAwsSecretKey(String awsSecretKey) {
//        this.awsSecretKey = awsSecretKey;
//    }

}

