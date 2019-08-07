package com.cafe24.shoppingmall.frontend.config.app;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {
	
	//@Autowired 
	//private ClientTokenServices clientTokenService;
	
	//@Autowired
	//private OAuth2ClientContext oauth2ClientContext;
	
	@Bean
	public OAuth2ProtectedResourceDetails resourceDetails() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        System.out.println("resourceDetails 실행");
        resourceDetails.setAccessTokenUri("http://localhost:8888/v1/oauth/token");
	    resourceDetails.setClientId("shoppingmall");
	    resourceDetails.setClientSecret("1234");
//        resourceDetails.setGrantType("client_credentials");
//        resourceDetails.setScope(Arrays.asList("write", "read"));
	    resourceDetails.setAuthenticationScheme(AuthenticationScheme.header);
	    System.out.println("resourceDetails 실행완료"); 
	    return resourceDetails;
	}
	
	@Bean
	public OAuth2RestTemplate oauth2RestTemplate() {
		 System.out.println("oauth2RestTemplate 실행");
		
	    OAuth2RestTemplate restTemplate = new OAuth2RestTemplate( resourceDetails(), new DefaultOAuth2ClientContext() );
	    
        restTemplate.setMessageConverters(Arrays.asList(new MappingJackson2HttpMessageConverter()));
//        System.out.println("access token: " + restTemplate.getAccessToken());
        
//	    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//	    interceptors.add(new LoggingRequestInterceptor());
//	    template.setInterceptors(interceptors);
//	    template.setRetryBadAccessTokens(true);
        System.out.println("oauth2RestTemplate 실행완료");
	    return restTemplate;		
	}
}
