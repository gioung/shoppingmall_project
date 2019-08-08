package com.cafe24.shoppingmall.frontend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.vo.ProductVo;

@Service
public class ProductService {

	@Autowired
	private RestTemplate restTemplate;
	
	//전체 상품 조회
	public List<ProductVo> getProductList(){
		String endpoint = "http://localhost:8888/v1/api/product/list";
		JSONResultProductList jsonResult = restTemplate.getForObject(endpoint, JSONResultProductList.class);
		
		return jsonResult.getData();
	}
	
	//특정 메인 카테고리 상품 조회
	public List<ProductVo> getProductList(Long main_no) {
		String endpoint = "http://localhost:8888/v1/api/product/list/"+main_no;
		JSONResultProductList jsonResult = restTemplate.getForObject(endpoint, JSONResultProductList.class);
		
		return jsonResult.getData();
	}
	//특정 서브 카테고리 상품 조회
	public List<ProductVo> getProductList(Long main_no, Long sub_no) {
		String endpoint = "http://localhost:8888/v1/api/product/list/"+main_no+"/"+sub_no;
		JSONResultProductList jsonResult = restTemplate.getForObject(endpoint, JSONResultProductList.class);
		
		return jsonResult.getData();
	}
	
	//특정 상품 상세 조회
	public Map<String, Object> getProduct(long product_no) {
		String endpoint = "http://localhost:8888/v1/api/product/"+product_no;
		JSONResultProduct jsonResult = restTemplate.getForObject(endpoint, JSONResultProduct.class);
		return jsonResult.getData();
	}
	// DTO Class
	private static class JSONResultProductList extends JSONResult<List<ProductVo>> {
	}
	private static class JSONResultProduct extends JSONResult<Map<String, Object>>{
		
	}

	

	
}
