package com.cafe24.shoppingmall.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.CategoryVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;
import com.cafe24.shoppingmall.service.ShopService;


import io.swagger.annotations.ApiOperation;

@RestController("shopAPIController")
@RequestMapping(value = "/api/product/")
public class ShopController {

	@Autowired
	ShopService shopService;
	
	/* api 목록 
	 * 상품 등록       (POST /list)
	 * 상품 선택       (GET /list/{no})
	 * 상품 가격 계산 (GET /list/price/{no}/{qty})
	 *  
	 * */
	
	
	//상품목록 조회
	@ApiOperation(value = "상품목록 조회")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONResult getProductList() {
		//display가 true이고 재고가 0이 아닌 상품조회
		List<ProductVo> productList = shopService.getProductList();
		
		if(productList != null)
			return JSONResult.success(productList);
		else
			return JSONResult.fail("상품 조회 실패");
	}
	
	//카테고리
	@ApiOperation(value = "카테고리 리스트 조회")
	@RequestMapping(value="/category/list", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getCategoryList(){		
		List<CategoryVo> categoryList = shopService.getCategoryList();
		
		if(!categoryList.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(categoryList));
		else
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("존재하는 카테고리가 없습니다."));
	}
	
	
	@ApiOperation(value= "특정 하위카테고리 조회")
	@RequestMapping(value = "/category/list/{no}", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getSubCategoryList(@PathVariable("no") long no){
		
		List<CategoryVo> subCategoryList = shopService.getSubCategoryList(no);
		
		if(!subCategoryList.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(subCategoryList));
		else
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("존재하는 카테고리가 없습니다."));
	}
	
	
}
