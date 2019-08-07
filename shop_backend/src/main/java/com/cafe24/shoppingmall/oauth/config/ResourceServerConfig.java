package com.cafe24.shoppingmall.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().disable();

		// 자원 서버 접근 권한 설정
		http
			.authorizeRequests()
			.anyRequest().permitAll();
//			.anyRequest().access("#oauth2.hasScope('read,write')");
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("v1"); //현재 토큰 리소스 id가 resourceId 의 매개변수값과 같아야 기능 사용가능
	}
	
	
	
	
}
