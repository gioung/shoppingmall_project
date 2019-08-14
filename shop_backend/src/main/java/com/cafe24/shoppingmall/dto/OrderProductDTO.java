package com.cafe24.shoppingmall.dto;

import java.util.List;

import com.cafe24.shoppingmall.repository.vo.OrderVo;
import com.cafe24.shoppingmall.repository.vo.OrderedProductVo;



public class OrderProductDTO {
	
	private OrderVo orderVo;
	private List<OrderedProductVo> orderProductList;
	
	public OrderVo getOrderVo() {
		return orderVo;
	}
	public void setOrderVo(OrderVo orderVo) {
		this.orderVo = orderVo;
	}
	public List<OrderedProductVo> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderedProductVo> orderProductList) {
		this.orderProductList = orderProductList;
	}
	
	
}
