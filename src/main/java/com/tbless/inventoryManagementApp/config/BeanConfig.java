package com.tbless.inventoryManagementApp.config;

import com.tbless.inventoryManagementApp.data.models.payStack.PayStackConfiguration;
import com.tbless.inventoryManagementApp.data.models.payStack.PaystackApiClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration

public class BeanConfig {
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.protocol}")
    private String mailProtocol;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;


    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();

    }
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setPort(mailPort);
        javaMailSender.setProtocol(mailProtocol);
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.ssl.enable", "true");
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setUsername(mailUsername);
        javaMailSender.setPassword(mailPassword);

        return javaMailSender;
    }


    @Bean
    public PaystackApiClient paystackApiClient(RestTemplate restTemplate, PayStackConfiguration paystackConfiguration) {
        return new PaystackApiClient(restTemplate, paystackConfiguration);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
