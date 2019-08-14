package com.cafe24.shoppingmall.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
		JSONResultCart jsonResult = restTemplate.postForObject(endpoint, cartVo, JSONResultCart.class);
		System.out.println("jsonResult = "+jsonResult);
		if(null == jsonResult.getData())
			return new CartVo();
		
		CartVo result = jsonResult.getData();
		return result;
	}
	

	//카트특정 정보 가져오기
	public List<CartVo> getCart(String id, long seq_no) {
		
		String endpoint = "http://localhost:8888/v1/api/cart/list/"+seq_no+"?id="+id;
		JSONResultCartList jsonResult = restTemplate.getForObject(endpoint, JSONResultCartList.class);
		
		return jsonResult.getData();
	}
	
	public List<CartVo> getCart(String id) {
		
		String endpoint = "http://localhost:8888/v1/api/cart/list?id="+id;
		JSONResultCartList jsonResult = restTemplate.getForObject(endpoint, JSONResultCartList.class);
		
		return jsonResult.getData();
	}
	
	//상품 옵션 및 수량 변경
	public Long updateCart(long seq_no,String id, long qty) {
		//보낼 데이터
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		cartVo.setQty(qty);
		
		String url = "http://localhost:8888/v1/api/cart/list/"+seq_no;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CartVo> entity = new HttpEntity<CartVo>(cartVo, headers);
		
		ResponseEntity<JSONResultQty> response = restTemplate.exchange(url, HttpMethod.PUT, entity, JSONResultQty.class);
		
		return response.getBody().getData();
	}
	
	//상품 개별 삭제
	public Boolean deleteCart(long seq_no, String id) {
		//보낼 데이터
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		
		String url = "http://localhost:8888/v1/api/cart/list/"+seq_no;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<CartVo> entity = new HttpEntity<CartVo>(cartVo, headers);
		
		ResponseEntity<JSONResultBoolean> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, JSONResultBoolean.class);
		
		return response.getBody().getData();
	}
	//상품 전체 삭제
	public Boolean deleteCartAll(String id) {
				
		String url = "http://localhost:8888/v1/api/cart/list";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(id, headers);
				
		ResponseEntity<JSONResultBoolean> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, JSONResultBoolean.class);
				
		return response.getBody().getData();
	}

	// DTO Class
		private static class JSONResultCart extends JSONResult<CartVo> {
			
		}
		private static class JSONResultCartList extends JSONResult<List<CartVo>> {
			
		}
		private static class JSONResultQty extends JSONResult<Long> {
			
		}
		private static class JSONResultBoolean extends JSONResult<Boolean> {
			
		}
		
		
		
		
}
