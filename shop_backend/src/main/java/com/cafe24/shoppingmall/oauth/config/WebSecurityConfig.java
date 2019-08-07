package com.cafe24.shoppingmall.oauth.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.cafe24.shoppingmall.dto.JSONResult;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Bean
	    @Override
	    public AuthenticationManager authenticationManagerBean() throws Exception {
	        return super.authenticationManagerBean();
	    }
		
//		 @Override
//		    protected void configure(AuthenticationManagerBuilder auth) 
//		      throws Exception {
//		        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		        auth.inMemoryAuthentication()
//		          .withUser("spring")
//		          .password(encoder.encode("secret"))
//		          .roles("USER");
//		    }
		
		@Bean
		public PasswordEncoder noPasswordEncoder() {
			return NoOpPasswordEncoder.getInstance();
		}
		
		
		//추가
		@Override
	    public void configure(HttpSecurity http) throws Exception {
			// 인터셉터 URL에 접근 제어(Basic ACL)
	        http
	        	.authorizeRequests()
	        	.anyRequest().permitAll()
	        // 예외처리
	        .and()
	       	.exceptionHandling()
	    	.accessDeniedHandler(new AccessDeniedHandler() {
				@Override
				public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
			    	JSONResult jsonResult = JSONResult.fail( "Access Denied" );
			    	
			    	MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
			    	if( jsonConverter.canWrite( jsonResult.getClass(), MediaType.APPLICATION_JSON ) ) {
			        	jsonConverter.write( jsonResult, MediaType.APPLICATION_JSON, new ServletServerHttpResponse( response ) );
			    	}
				}
	    	});
	        
	        // csrf 체크 제외
	        http.csrf().disable();
	 	}
		

		@Override
		public void configure(WebSecurity web) throws Exception {
			super.configure(web);
		}

		

}
