package com.cafe24.shoppingmall.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.ProductVo;
import com.cafe24.shoppingmall.service.ShopService;

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
	
	// 상품 등록, forward
	@ApiOperation(value = "상품 등록")
	@RequestMapping(value = "/list", method = RequestMethod.POST) 
	public ResponseEntity<JSONResult> addProducts(@RequestBody List<ProductVo> productVoList) {
		//상품을 여러개 등록 할 때
		// 상품 등록
		boolean judge = shopService.addProducts(productVoList);
		// Service에 삽입 요청을 하는 code
		if(judge)
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(productVoList));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 등록 실패"));
	}
	
	//상품목록 조회, forward
	@ApiOperation(value = "상품 목록 조회")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.GET)
	public JSONResult getProductList(@PathVariable("no")long productNo) {
		
		//해당 productNo의 상품이 있는가?
		boolean judge = shopService.getProduct(productNo);
		
		if(judge)
			return JSONResult.success(judge);
		else
			return JSONResult.fail("상품 조회 실패");
	}
	
	// 상품가격 계산, ajax
	@ApiOperation(value = "상품 가격 계산")
	@RequestMapping(value = "/list/price/{no}/{qty}", method = RequestMethod.GET)
	public JSONResult getProductPrice(@PathVariable("no")long productNo,
			@PathVariable("qty")long quantity) {
		
		//가격 구하기
		long price = shopService.getProductPrice(productNo, quantity);
		
		if(price <=0)
			return JSONResult.fail("상품이 존재하지 않습니다.");
		else
			return JSONResult.success(price+"원");
	}
	
	
		
	
	
	
}
