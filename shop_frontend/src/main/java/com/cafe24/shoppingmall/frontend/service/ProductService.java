package com.cafe24.shoppingmall.frontend.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.dto.ProductDTO;
import com.cafe24.shoppingmall.frontend.vo.OptionVo;
import com.cafe24.shoppingmall.frontend.vo.ProductDetailVo;
import com.cafe24.shoppingmall.frontend.vo.ProductVo;

@Service
public class ProductService {

	@Autowired
	private RestTemplate restTemplate;
	
	/*##### INSERT #####*/
	public String addProduct(Map<String,Object> map) {
		List<ProductDetailVo> productDetailList = (List<ProductDetailVo>)map.get("productDetailList");
		ProductVo productVo = (ProductVo)map.get("product");
		List<String> optionList = (List<String>)map.get("optionList");
		List<Long> inventory = (List<Long>)map.get("inventory");
		
		for(int i=0; i<optionList.size(); i++) {
			productDetailList.add(new ProductDetailVo(optionList.get(i),inventory.get(i)));
		}
		
//		System.out.println("productDetailList 옵션 = "+productDetailList);
		
		ProductDTO product = new ProductDTO(productVo, productDetailList);
		
		
		String endpoint = "http://localhost:8888/v1/api/admin/product/list";
		JSONResultBoolean jsonResult = restTemplate.postForObject(endpoint, product, JSONResultBoolean.class);
		
		return jsonResult.getResult();
		
	}
	
	/*##### SELECT #####*/
	//유저 전체 상품 조회
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
	
	//관리자 전체상품조회
	public List<ProductVo> getAdminProductList() {
		String endpoint = "http://localhost:8888/v1/api/admin/product/list";
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
	private static class JSONResultBoolean extends JSONResult<Boolean>{
		
	}
	

	

	
}
