package com.cafe24.shoppingmall.frontend.service;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.dto.JSONResult2;
import com.cafe24.shoppingmall.frontend.vo.CartVo;
import com.cafe24.shoppingmall.frontend.vo.MemberVo;

@Service
public class MemberService {
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	
	public String join(MemberVo memberVo) {
		//BCrypt 로 encode
		String password = passwordEncoder.encode(memberVo.getPassword());
		memberVo.setPassword(password);
		
		String endpoint = "http://localhost:8888/v1/api/user/signup";
		JSONResultMember jsonResult = restTemplate.postForObject(endpoint, memberVo, JSONResultMember.class);
		return jsonResult.getResult();
	}
	
	/* SELECT */
	public JSONResult2 checkemail(String email) {
		String endpoint = "http://localhost:8888/v1/api/user/checkemail?email="+email;
		
		
		return restTemplate.getForObject(endpoint, JSONResult2.class);
	}
	

	public List<CartVo> getCartList(String email) {
		String endpoint = "http://localhost:8888/v1/api/cart/list?id="+email;
		JSONResultCartList jsonResult = restTemplate.getForObject(endpoint, JSONResultCartList.class);
		return jsonResult.getData();
	}
	
	// DTO Class
	private static class JSONResultMember extends JSONResult<Boolean> {
		
	}

	private static class JSONResultCartList extends JSONResult<List<CartVo>>{
		
	}

	

}
