package com.cafe24.shoppingmall.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	  
	  @ApiOperation(value = "장바구니 특정상품 조회")
	  @GetMapping(value="/list/{seq_no}")
	  public ResponseEntity<JSONResult> getProductToOrder(@PathVariable("seq_no")long seq_no,
			  @RequestParam String id) {
		
		  CartVo cartVo = cartService.getProductToOrder(id,seq_no);
		  List<CartVo> cartList = new ArrayList<>();
		  cartList.add(cartVo);
		  
		  if(cartVo != null)
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(cartList));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 조회 실패"));
		
	}
	  
	  @ApiOperation(value = "장바구니 조회")
	  @GetMapping(value="/list")
	  public ResponseEntity<JSONResult> getProductListInCart(@RequestParam("id") String id) {
		  List<CartVo> cartList = cartService.getProductListInCart(id);
		  if(cartList != null)
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(cartList));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 담기 실패"));
		
	}

	@ApiOperation(value = "회원가입시 기존 장바구니 id 변환")
	@PutMapping(value = "/list")
	public ResponseEntity<JSONResult> convertTempIdToUser(@RequestBody CartVo cartVo) {

		
		if (cartService.convertTempIdToUser(cartVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 아이디 수정 실패"));

	}
	
	@ApiOperation(value = "장바구니 수량 및 옵션 수정")
	@PutMapping(value = "/list/{seq_no}")
	public ResponseEntity<JSONResult> updateProductQty(@PathVariable("seq_no") long seq_no,
			@RequestBody CartVo cartVo) {
		System.out.println("CartVo = "+cartVo);
		cartVo.setSeq_no(seq_no);
		if (cartService.updateProductQty(cartVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(cartVo.getQty()));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 아이디 수정 실패"));

	}	
	  
	 @ApiOperation(value = "장바구니 상품 삭제")
	  @DeleteMapping(value="/list/{seq_no}")
	  public ResponseEntity<JSONResult> deleteProductInCart(@PathVariable("seq_no") long seq_no,
				@RequestBody CartVo cartVo) {
		 cartVo.setSeq_no(seq_no);
		 
		  if(cartService.deleteProductInCart(cartVo))
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 삭제 실패"));
		
	}	
	  @ApiOperation(value = "장바구니 삭제")
	  @DeleteMapping(value="/list")
	  public ResponseEntity<JSONResult> deleteCart(@RequestBody String Id) {
		  
		  if(cartService.deleteCart(Id))
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 삭제 실패"));
		
	} 
	  
}
