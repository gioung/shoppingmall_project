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
import com.cafe24.shoppingmall.dto.OrderProductDTO;
import com.cafe24.shoppingmall.repository.vo.OrderVo;
import com.cafe24.shoppingmall.repository.vo.OrderedProductVo;
import com.cafe24.shoppingmall.service.OrderService;

import io.swagger.annotations.ApiOperation;

@RestController("adminOrderAPIController")
@RequestMapping(value = "/api/admin/order")
public class AdminOrderController {
	
	@Autowired
	OrderService orderService;
	
	@ApiOperation(value = "관리자 전체 주문 조회")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getAllOrderList() {
		
		// 관리자 상품 목록 조회
		List<OrderVo> orderList= orderService.getAllOrderList();
		if(orderList != null)
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(orderList));
			 
		// Service에 삽입 요청을 하는 code
		return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("상품 목록이 존재하지 않습니다."));
	}
	
	@ApiOperation(value = "관리자 주문 상세 조회")
	@RequestMapping(value = "/list/{order_no}", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getOrderDetailList(@PathVariable("order_no")long order_no) {
		// 해당 주문번호 고객정보
		OrderVo orderVo = orderService.getOrder(order_no);
		System.out.println(order_no+"의 주문정보 = "+orderVo);
		// 관리자 상품 목록 조회
		List<OrderedProductVo> orderProductList = orderService.getOrderDetailList(orderVo);
		System.out.println(order_no+"의 주문상품 정보 = "+orderProductList);
		
		OrderProductDTO orders = new OrderProductDTO();
		orders.setOrderVo(orderVo);
		orders.setOrderProductList(orderProductList);
		
		if(orders.getOrderProductList() != null && orders.getOrderVo() != null)
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(orders));
		else
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("상품 목록이 존재하지 않습니다."));
	}

}
