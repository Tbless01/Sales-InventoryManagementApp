package com.tbless.inventoryManagementApp.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor

public class securityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(httpSecurityHeadersConfigurer ->
                        httpSecurityHeadersConfigurer.addHeaderWriter(new StaticHeadersWriter("Access-Control-Allow-Origin", "http://localhost:3000")))
                .authorizeHttpRequests((auth -> {
                    auth
                            .requestMatchers("/api/v1/auth/register","/api/v1/register/user","/api/v1/register/verify", "/api/v1/auth/login", "/api/v1/verifyEmail", "/api/v1/test",
                                    "/api/v1/verifyEmail/sendOtp", "/api/v1/user/getUserDetails/**","/api/v1/user/profilePixUpdate/**", "/api/v1/product/addProduct", "/api/v1/product/getAddedProduct/**","api/v1/product/products/**",
                                    "/api/v1/user/contact","/api/v1/product/count/**","/api/v1/payment/initiate-payment","/api/v1/payment/verify-payment",
                                    "/api/v1/product/availableByEmail/**", "/api/v1/product/delete/**","/api/v1/product/deleteWhenZero/**", "/api/v1/product/update/**", "/api/v1/verifyEmail/sendOtp/confirmOtp",
                                    "api/v1/product/search/**","/api/v1/product/availableForOrder", "/api/v1/product/availableExceptOwnersProduct/**","/api/v1/product/searchAvailableExceptOwnersProduct/**", "/api/v1/card/addDebitCard/**",
                                    "/api/v1/orders/owner/**", "/api/v1/orders/place/**", "/api/v1/orders/customer/**", "/api/v1/card/getExistingCardDetails/**", "/api/v1/order/placeOrder/**",
                                    "/api/v1/order/makePayment/**","/api/v1/product/upload-image/**").permitAll();
                }))
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
