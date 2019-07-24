package com.cafe24.shoppingmall.controller.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;
import com.cafe24.shoppingmall.service.ShopService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.swagger.annotations.ApiOperation;

@RestController("adminShopAPIController")
@RequestMapping(value = "/api/admin/product")
public class AdminShopController {

	@Autowired
	ShopService shopService;
	// 상품 등록
	@ApiOperation(value = "관리자 상품 등록")
	@RequestMapping(value = "/list", method = RequestMethod.POST) 
	public ResponseEntity<JSONResult> addProducts(@RequestBody Map<String,Object> map) {
			
			Gson gson = new GsonBuilder().create();
			Type listType = new TypeToken<ArrayList<ProductDetailVo>>(){}.getType();
			
			ProductVo productVo = gson.fromJson(String.valueOf(map.get("product")), ProductVo.class);
			List<ProductDetailVo> productDetailVoList = gson.fromJson(String.valueOf(map.get("productDetailList")), listType);

			System.out.println(productVo);
			System.out.println(productDetailVoList);
			// 상품 등록
			boolean judge = shopService.addProduct(productVo, productDetailVoList);
			if(!judge)
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 등록 실패")); 
			
			
			// Service에 삽입 요청을 하는 code
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(map));
		}
	
	@ApiOperation(value = "관리자 상품 목록")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getAllProductList() {
		
		// 관리자 상품 목록 조회
		List<ProductVo> productList= shopService.getAllProductList();
		if(productList != null)
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(productList));
			 
		// Service에 삽입 요청을 하는 code
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 등록 실패"));
	}
	
	@ApiOperation(value = "관리자 상품 조회")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getProduct(@PathVariable("no") Long no) {
		//관리자 상품 조회
		Map<String,Object> map = shopService.getProduct(no);
		
		if(map != null) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(map));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("없는 상품입니다."));
		}
	}
	
}
