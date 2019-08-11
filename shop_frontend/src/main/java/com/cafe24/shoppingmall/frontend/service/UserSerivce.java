package com.cafe24.shoppingmall.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.vo.MemberVo;
import com.cafe24.shoppingmall.frontend.vo.ProductVo;

@Service
public class UserSerivce {

	@Autowired
	RestTemplate restTemplate;

	// 전체 회원 조회
	public List<MemberVo> getAllMebmer() {
		String endpoint = "http://localhost:8888/v1/api/admin/user/list";
		JSONResultProductList jsonResult = restTemplate.getForObject(endpoint, JSONResultProductList.class);

		return jsonResult.getData();
	}
	
	// DTO Class
	private static class JSONResultProductList extends JSONResult<List<MemberVo>> {
	}
}
