package com.cafe24.shoppingmall.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.dto.OrderProductDTO;
import com.cafe24.shoppingmall.frontend.vo.OrderVo;


@Service
public class OrderService {
	
	@Autowired
	RestTemplate restTemplate;
	
	public Boolean doOrder(OrderVo orderVo) {
		String endpoint = "http://localhost:8888/v1/api/order/list";
		JSONResultBoolean jsonResult = restTemplate.postForObject(endpoint, orderVo,JSONResultBoolean.class);
		
		return jsonResult.getData();
	}
	
	//전체 주문목록 조회
	public List<OrderVo> getOrderList() {
		String endpoint = "http://localhost:8888/v1/api/admin/order/list";
		JSONResultOrderList jsonResult = restTemplate.getForObject(endpoint, JSONResultOrderList.class);
		return jsonResult.getData();
	}
	//특정 주문번호의 주문 조회
	public OrderProductDTO getOrderListForOrderNo(long order_no) {
		String endpoint = "http://localhost:8888/v1/api/admin/order/list/"+order_no;
		JSONResultOrderProductDTO jsonResult = restTemplate.getForObject(endpoint, JSONResultOrderProductDTO.class);
		return jsonResult.getData();
	}
	//DTO 객체
	private static class JSONResultBoolean extends JSONResult<Boolean>{
			
	}
	private static class JSONResultOrderList extends JSONResult<List<OrderVo>>{
		
	}
	
	private static class JSONResultOrderProductDTO extends JSONResult<OrderProductDTO>{
		
	}
	

	
}


