# Simple Email Service 

Create a service that accepts the necessary information and sends emails. 
It should provide an abstraction between two different email service providers. If one of the services goes down, your service can quickly failover to a different provider without affecting your customers.

## Example Email Providers(Choose 2 popular email providers): 
SendGrid - Simple Send Documentation 
Amazon SES - Simple Send Documentation

## Prerequisites 
What things you need to install the software and how to setup your system 
    Java 11 - Latest 
    Maven 
    IntelliJDEA 
    SendGrid email service credentials 
    Amazon SES email service credentials

## Project Framework 
In this project, using Spring boot framework with dependencies: amazon SES, sendgrid

## Architecture: 
    Model: 
        EmailRequest: email format - accept the request from the front-end 
        EmailResponse: email response - return json data for front-end 
    Service: 
        SendEmailService: Interface - service for api 
        SendEmailServiceImpl: Service Implmentation with 2 email providers 
        EmailServiceProvider: Interface - specification for email service provider 
        AmazonSESEmailProvider: Service, which is primary Email Service Provider by using annotation @Qualifier("primaryEmailServiceProvider") 
        SendGridEmailProvider: Service, which is secondary Email Service Provider by using annotation @Qualifier("secondaryEmailServiceProvider") 
    <Note: It's OOP polymorphism - using Interface, so you can change your implementation/email provider without rewrite the other code(loose coupling) > 
    Controller:
        SendEmailController: Api/endpoint 
    Exception: 
        SendEmailExceptionHandler(@ControllerAdvice): handle all exceptions 
        BadRequestException: handle BadRequestException with details of the error 
        EmailServiceProviderException: handle the other service provider exceptions 
    Config: 
        ProviderConfiguration: all Emailproviders configuration(beans) 
        AmazonSESConfiguration: config amazonses service provider 
        SendGridConfiguration: config sendgrid service provider

## Test: 
    1. Unit Test: 
        ControllerTest: Test controller 
        EmailServiceTest: Test SendEmailService 
    2. Integration Test: 
        ApiIntegrationTest: api integration

## Development and further improvements
1.  Modify the email request with more data: ccs, bccs and attachment files
2.  Add email history into database
3.  Add the front-end part
4.  Security
5.  Actuator
   