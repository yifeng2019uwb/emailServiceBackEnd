package com.yifeng.emailapi;

import com.yifeng.emailapi.config.emailprovider.SendGridConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(SendGridConfiguration.class)
public class EmailApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailApiApplication.class, args);
	}

}
