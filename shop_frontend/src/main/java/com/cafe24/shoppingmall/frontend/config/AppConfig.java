package com.cafe24.shoppingmall.frontend.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.cafe24.shoppingmall.frontend.config.app.AppSecurityConfig;
import com.cafe24.shoppingmall.frontend.config.app.OAuth2ClientConfig;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan({"com.cafe24.shoppingmall.frontend.security", "com.cafe24.shoppingmall.frontend.service",  "com.cafe24.shoppingmall.frontend.repository"})
@Import({ AppSecurityConfig.class, OAuth2ClientConfig.class })
public class AppConfig {
}