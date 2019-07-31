package com.cafe24.shoppingmall.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.OrderVo;
import com.cafe24.shoppingmall.service.OrderService;

import io.swagger.annotations.ApiOperation;

@RestController("orderAPIController")
@RequestMapping(value = "/api/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	// 주문 하기
	@ApiOperation(value = "주문 하기")
	@PostMapping(value = "/list")
	public ResponseEntity<JSONResult> doOrder(@Valid @RequestBody OrderVo orderVo, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
		}
		
		if(orderService.doOrder(orderVo)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(orderVo));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("주문 실패"));
		}
	}
	
	// 주문 취소
	@ApiOperation(value = "주문 취소")
	@DeleteMapping(value="/list/{no}")
	public ResponseEntity<JSONResult> cancelOrder(@PathVariable("no") long order_no, @RequestBody OrderVo orderVo) {

		orderVo.setOrder_no(order_no);

		if (orderService.cancelOrder(orderVo)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(orderVo));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("주문 취소 실패"));
		}
	}
}
