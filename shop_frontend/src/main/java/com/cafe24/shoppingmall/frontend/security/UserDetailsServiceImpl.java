package com.cafe24.shoppingmall.frontend.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.repository.UserDao;
import com.cafe24.shoppingmall.frontend.vo.MemberVo;




@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String endpoint = "http://localhost:8888/v1/api/user/info?email="+email;
		JSONResultMember jResultMember = restTemplate.getForObject(endpoint, JSONResultMember.class);
		
		MemberVo memberVo = jResultMember.getData();
		boolean admin = memberVo.isAdmin();
		
		SecurityUser securityUser = new SecurityUser();
		
		if(admin == true) {
			System.out.println("관리자 로그인");
			String role = "ROLE_ADMIN";
			securityUser.setAuthorities(Arrays.asList(new SimpleGrantedAuthority(role)));
		}
		
		
		if(memberVo != null) {
		
			securityUser.setName(memberVo.getName());
			securityUser.setUsername(memberVo.getEmail());
			securityUser.setPassword(memberVo.getPassword());
			
		}
		
		return securityUser;
	}	
	
	// DTO Class
	private static class JSONResultMember extends JSONResult<MemberVo> {
			
	}
}
