package com.yifeng.emailapi.config;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.amazonaws.services.simpleemail.waiters.AmazonSimpleEmailServiceWaiters;
import com.sendgrid.SendGrid;
import com.yifeng.emailapi.config.emailprovider.AmazonSESConfiguration;
import com.yifeng.emailapi.config.emailprovider.SendGridConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;



@Configuration
public class ProviderConfiguration {

    @Bean
    public SendGrid sendGrid(SendGridConfiguration properties) {
        return new SendGrid(properties.getApiKey());
    }

    @Bean
    public AmazonSimpleEmailService amazonSimpleEmailService(AmazonSESConfiguration properties) {
        AWSCredentialsProvider credentials = new AWSCredentialsProvider() {
            @Override
            public void refresh() {}
            @Override
            public AWSCredentials getCredentials() {
                return new AWSCredentials() {
                    @Override
                    public String getAWSSecretKey() {
                        return properties.getAwsSecretKey();
                    }
                    @Override
                    public String getAWSAccessKeyId() {
                        return properties.getAwsAccessKeyId();
                    }
                };
            }
        };
        return AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(credentials)
                .withRegion(Regions.US_WEST_2).build();
    }


}
