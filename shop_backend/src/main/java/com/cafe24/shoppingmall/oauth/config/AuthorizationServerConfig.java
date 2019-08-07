package com.cafe24.shoppingmall.oauth.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClientDetailsService clientDetailsService;
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		// password or authorization code
//		clients.inMemory() 
//			.withClient("pjmall2")
//			.authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//			.authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//			.scopes("read", "write", "trust")
//			.resourceIds("sparklr")
//			.accessTokenValiditySeconds(60);

		// client credentials
//		clients.inMemory() 
//			.withClient("shoppingmall")
//			.authorizedGrantTypes("client_credentials")
//			.authorities("ROLE_CLIENT")
//			.scopes("read", "write", "trust")
//			.resourceIds("v1")
//			.secret("1234");
		
			//.accessTokenValiditySeconds(60);
//		
//		clients
//			.jdbc(dataSource())
//			.withClient("shoppingmall")
//			.authorizedGrantTypes("client_credentials")
//			.authorities("ROLE_CLIENT")
//			.scopes("read","write","trust") 
//			.resourceIds("v1")
//			.secret("1234").and().build();  
		
		clients.withClientDetails(clientDetailsService);
	}
	
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	// OAuth2 서버가 작동하기 위한 Endpoint에 대한 정보를 DB에 저장
        endpoints
        	.tokenStore( new JdbcTokenStore(dataSource()) )
        		.authenticationManager(authenticationManager);
    }
    
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource dataSource() throws SQLException {
        return new BasicDataSource();
    }
    
    /*
     * 새로운 클라이언트 등록을 위한 빈
     */
    @Bean
    public ClientRegistrationService clientRegistrationService() throws SQLException {
        return new JdbcClientDetailsService(dataSource());
    }


    
}
