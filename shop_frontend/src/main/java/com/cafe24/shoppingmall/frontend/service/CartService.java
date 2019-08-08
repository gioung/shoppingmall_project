package com.cafe24.shoppingmall.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.vo.CartVo;

@Service
public class CartService {

	@Autowired
	private RestTemplate restTemplate;
	
	public CartVo addCart(CartVo cartVo) {
		String endpoint = "http://localhost:8888/v1/api/cart/list";
		JSONResultCartList jsonResult = restTemplate.postForObject(endpoint, cartVo, JSONResultCartList.class);
		System.out.println("jsonResult = "+jsonResult);
		if(null == jsonResult.getData())
			return new CartVo();
		
		CartVo result = jsonResult.getData();
		return result;
	}
	
	// DTO Class
	private static class JSONResultCartList extends JSONResult<CartVo> {
		
	}

}
