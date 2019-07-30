package com.cafe24.shoppingmall.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.CartVo;
import com.cafe24.shoppingmall.service.CartService;

import io.swagger.annotations.ApiOperation;

@RestController("cartAPIController")
@RequestMapping(value = "/api/cart")
public class CartController {
	@Autowired
	CartService cartService;
	/* api 목록
	 * 장바구니 담기(/cart/api)
	 * 장바구니 목록(/cart/api/list)
	 * 장바구니 목록삭제(/cart/api/list/{no})
	 */
	
	
	  @ApiOperation(value = "장바구니 담기")
	  @PostMapping(value="/list")
	  public ResponseEntity<JSONResult> addProductToCart(@Valid @RequestBody CartVo cartVo) {
		  
		  if(cartService.addProductToCart(cartVo))
				return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(cartVo));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 담기 실패"));
		
	}
	  
	  @ApiOperation(value = "장바구니 삭제")
	  @DeleteMapping(value="/list")
	  public ResponseEntity<JSONResult> deleteCart(@RequestBody String Id) {
		  
		  if(cartService.deleteCart(Id))
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 삭제 실패"));
		
	} 
	  
	//장바구니 목록, forward 
	//member_id, tempid 값을 받아서 나열
//	@ApiOperation(value = "장바구니 목록")
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public JSONResult addProductToCart(@RequestParam String member_id,
//			@RequestParam String temp_id) {
//		
//		List<CartVo> list = cartService.getProductList(member_id, temp_id);
//		if(list.size()==0)
//			return JSONResult.fail("장바구니에 담은 상품이 없습니다.");
//		else
//			return JSONResult.success(list);
//		
//	}
//	
//	@ApiOperation(value = "장바구니 목록 삭제")
//	@RequestMapping(value = "/list/{no}", method = RequestMethod.DELETE)
//	public JSONResult deleteProductsToCart(@RequestParam String member_id,
//			@RequestParam String temp_id, @PathVariable long no) {
//		
//		boolean judge = cartService.deleteProducts(member_id, temp_id, no);
//		
//		if(judge == true)
//			return JSONResult.success(judge);
//		else
//			return JSONResult.fail("장바구니 목록 삭제 실패");
//	}
}
