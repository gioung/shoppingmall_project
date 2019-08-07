package com.cafe24.shoppingmall.oauth.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.stereotype.Service;



@Primary
@Service
public class ClientDetailsServiceImpl extends JdbcClientDetailsService {
	
	public ClientDetailsServiceImpl(@Lazy DataSource dataSource) {
	        super(dataSource);
	    }
	 
	    @Override
	    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
	        
	        return super.loadClientByClientId(clientId);
	    }
	 
	    @Override
	    public void addClientDetails(ClientDetails clientDetails) throws ClientAlreadyExistsException {
	        
	        super.addClientDetails(clientDetails);
	    }
	 
	    @Override
	    public void updateClientDetails(ClientDetails clientDetails) throws NoSuchClientException {
	        
	        super.updateClientDetails(clientDetails);
	    }
	 
	    @Override
	    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
	        
	        super.updateClientSecret(clientId, secret);
	    }
	 
	    @Override
	    public void removeClientDetails(String clientId) throws NoSuchClientException {
	        
	        super.removeClientDetails(clientId);
	    }
	 
	    @Override
	    public List<ClientDetails> listClientDetails() {
	        List<ClientDetails> list = super.listClientDetails();
	        return list;
	    }


}
