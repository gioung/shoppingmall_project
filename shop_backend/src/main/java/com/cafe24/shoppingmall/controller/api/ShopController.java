package com.cafe24.shoppingmall.controller.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController("shopAPIController")
@RequestMapping(value = "/api/shop")
public class ShopController {

	@Autowired
	ShopService shopService;
	
	/* api 목록 
	 * 상품 등록       (POST /list)
	 * 상품 선택       (GET /list/{no})
	 * 상품 가격 계산 (GET /list/price/{no}/{qty})
	 *  
	 * */
	
	// 상품 등록
	@ApiOperation(value = "상품 등록")
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
	
	//상품목록 조회
//	@ApiOperation(value = "상품  조회")
//	@RequestMapping(value = "/list/{no}", method = RequestMethod.GET)
//	public JSONResult getProductList(@PathVariable("no")long productNo) {
//		
//		//해당 productNo의 상품이 있는가?
//		boolean judge = shopService.getProduct(productNo);
//		
//		if(judge)
//			return JSONResult.success(judge);
//		else
//			return JSONResult.fail("상품 조회 실패");
//	}
//	
//	// 상품가격 계산, ajax
//	@ApiOperation(value = "상품 가격 계산")
//	@RequestMapping(value = "/list/price/{no}/{qty}", method = RequestMethod.GET)
//	public JSONResult getProductPrice(@PathVariable("no")long productNo,
//			@PathVariable("qty")long quantity) {
//		
//		//가격 구하기
//		long price = shopService.getProductPrice(productNo, quantity);
//		
//		if(price <=0)
//			return JSONResult.fail("상품이 존재하지 않습니다.");
//		else
//			return JSONResult.success(price+"원");
//	}
	
	
	// 상품삭제 
	
	
		
	
	
	
}
