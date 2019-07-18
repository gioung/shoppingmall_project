package com.cafe24.shoppingmall.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.service.CartService;
import com.cafe24.shppingmall.repository.vo.CartVo;

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
	
	
	 //장바구니 담기, ajax 
	  /*  ====== 가장 기본적인 상황 ======
	   *  1. 옵션정보가 없다고 가정 
	   *  2. 멤버아이디 , temp_id, seq_no, qty, product_no 데이터만 insert 
	   *  */
	  @ApiOperation(value = "장바구니 담기")
	  @RequestMapping(value = "", method = RequestMethod.POST)
	  @ResponseStatus(HttpStatus.CREATED) 
	  public JSONResult addProductToCart(@ModelAttribute CartVo cartVo) {
		
		  boolean judge = cartService.addProductToCart(cartVo);
		  //유효할시 성공
		  if(judge == true)
				return JSONResult.success(cartVo);
		  else
				return JSONResult.fail("장바구니 담기 실패"); 
		
	}
	  
	//장바구니 목록, forward 
	//member_id, tempid 값을 받아서 나열
	@ApiOperation(value = "장바구니 목록")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONResult addProductToCart(@RequestParam String member_id,
			@RequestParam String temp_id) {
		
		List<CartVo> list = cartService.getProductList(member_id, temp_id);
		if(list.size()==0)
			return JSONResult.fail("장바구니에 담은 상품이 없습니다.");
		else
			return JSONResult.success(list);
		
	}
	
	@ApiOperation(value = "장바구니 목록 삭제")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.DELETE)
	public JSONResult deleteProductsToCart(@RequestParam String member_id,
			@RequestParam String temp_id, @PathVariable long no) {
		
		boolean judge = cartService.deleteProducts(member_id, temp_id, no);
		
		if(judge == true)
			return JSONResult.success(judge);
		else
			return JSONResult.fail("장바구니 목록 삭제 실패");
	}
}
