package com.cafe24.shoppingmall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.vo.CartVo;

@Service
public class CartService {
	private final List<CartVo> cartList = new ArrayList<>();
	
	public boolean addProductToCart(CartVo cartVo) {
		if(cartVo == null)
			return false;
		
		cartList.add(cartVo);
		return true;
		
	}

	public List<CartVo> getProductList(String member_id, String temp_id) {
		List<CartVo> list = new ArrayList<>();
		for(CartVo vo : cartList) {
			if(vo.getMember_id().equals(member_id) && vo.getTemp_id().equals(temp_id))
				list.add(vo);
		}
		
		return list;
	}

	public boolean deleteProducts(String member_id, String temp_id, long no) {
		
		for(int i=0; i< cartList.size(); i++) {
			CartVo vo = cartList.get(i);
			if(vo.getMember_id().equals(member_id) && vo.getTemp_id().equals(temp_id))
				if(vo.getSeq_no() == no) {
					cartList.remove(i);
					return true;
				}
		}
		return false;
		
	}
}
