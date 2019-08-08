package com.cafe24.shoppingmall.frontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.vo.MemberVo;

@Service
public class MemberService {
	@Autowired
	private RestTemplate restTemplate;
	
	
	public String join(MemberVo memberVo) {
		String endpoint = "http://localhost:8888/v1/api/user/signup";
		JSONResultMember jsonResult = restTemplate.postForObject(endpoint, memberVo, JSONResultMember.class);
		
		return jsonResult.getResult();
	}
	
	// DTO Class
	private static class JSONResultMember extends JSONResult<MemberVo> {
		
	}

}
