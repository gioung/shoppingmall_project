package com.cafe24.shoppingmall.controller.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.CategoryVo;
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
	public ResponseEntity<JSONResult> addProducts(@RequestBody Map<String,Object> map, BindingResult bindingResult) {
			
			
		
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
	
	@ApiOperation(value = "관리자 상품 수정")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.PUT)
	public ResponseEntity<JSONResult> updateProduct(@PathVariable("no") Long no,
			@RequestBody Map<String,Object> map) {
		
		System.out.println("map = " + map);
		
		Gson gson = new GsonBuilder().create();
		Type listType = new TypeToken<ArrayList<ProductDetailVo>>(){}.getType();
		
		ProductVo productVo = gson.fromJson(String.valueOf(map.get("product")), ProductVo.class);
		List<ProductDetailVo> productDetailVoList = gson.fromJson(String.valueOf(map.get("productDetailList")), listType);
		
		Validator validator = 
				Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ProductVo>> validatorResults = validator.validate(productVo);
		if(!validatorResults.isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(validatorResults.toString()));
		
		Set<ConstraintViolation<ProductDetailVo>> validatorResults2;
		for(ProductDetailVo pdv:productDetailVoList) {
			validatorResults2 = validator.validate(pdv);
			if(!validatorResults2.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(validatorResults2.toString()));
		}
		
		boolean judge = shopService.updateProduct(productVo, productDetailVoList);
		
		if(judge) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(map));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 수정 실패"));
		}
	}
	
	@ApiOperation(value = "관리자 해당 상품 삭제")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResult> deleteProduct(@PathVariable("no") Long no) {
		
		boolean judge = shopService.deleteProduct(no);
		
		if(judge) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 삭제 실패"));
		}
	}
	
	@ApiOperation(value = "관리자 해당 상품 옵션 삭제")
	@RequestMapping(value = "/list/{no}/{d_no}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResult> deleteProductOption(@PathVariable("no") Long no, @PathVariable("d_no") Long d_no){
		ProductDetailVo pdv = new ProductDetailVo(d_no, no);
		
		if(shopService.deleteProductDetail(pdv)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("잘못된 삭제 요청입니다."));
		}
		
	}
	
	@ApiOperation(value = "카테고리 생성")
	@RequestMapping(value="/category", method = RequestMethod.POST)
	public ResponseEntity<JSONResult> addCategory(@RequestBody List<CategoryVo> categoryList){
		
		if(shopService.addCategory(categoryList))
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(true));
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("카테고리 등록 실패"));
		}
		
	}
	
	@ApiOperation(value= "카테고리 수정")
	@PutMapping(value="/category/list/{no}")
	public ResponseEntity<JSONResult> updateMainCategory(@PathVariable("no") long no,
			@RequestBody CategoryVo categoryVo){
		
		categoryVo.setMain_no(no);
		if(shopService.updateMainCategory(categoryVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("메인 카테고리 수정 실패"));
	}
	
	@ApiOperation(value= "하위 카테고리 수정")
	@PutMapping(value="/category/list/{no}/{s_no}")
	public ResponseEntity<JSONResult> updateMainCategory(@PathVariable("no") long no,
			@PathVariable("s_no") long s_no, @RequestBody CategoryVo categoryVo){
		
		categoryVo.setMain_no(no);
		categoryVo.setSub_no(s_no);
		
		if(shopService.updateSubCategory(categoryVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("서브 카테고리 수정 실패"));
	}
	
	@ApiOperation(value = "하위 카테고리 삭제")
	@DeleteMapping(value="/category/list/{no}/{s_no}")
	public ResponseEntity<JSONResult> deleteSubCategory(@PathVariable("no") Long main_no,
			@PathVariable("s_no") Long sub_no){
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setMain_no(main_no);
		categoryVo.setSub_no(sub_no);
		
		if(shopService.deleteSubCategory(categoryVo)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("하위 카테고리 삭제 실패"));
		}
	}
	@ApiOperation(value = "상위 카테고리 삭제")
	@DeleteMapping(value="/category/list/{no}")
	public ResponseEntity<JSONResult> deleteMainCategory(@PathVariable("no") Long main_no
		){
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setMain_no(main_no);
			
		if(shopService.deleteMainCategory(categoryVo)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상위 카테고리 삭제 실패"));
			}
	
	}
	
	
}
