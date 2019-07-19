package com.cafe24.shoppingmall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.ShopDao;
import com.cafe24.shoppingmall.repository.vo.ProductVo;

@Service
public class ShopService {
	@Autowired
	private ShopDao shopDao;
	private List<ProductVo> list;
	
	public boolean addProducts(List<ProductVo> list) {
		Map<String, List<ProductVo>> map = new HashMap<>();
		map.put("productList", list);
		return shopDao.addProducts(map);
	}
	
	// 특정 productNo를 가진 상품 조회
	public boolean getProduct(long productNo) {
		for(ProductVo vo:list) {
			if(vo.getProduct_no() == productNo)
				return true;
		}
		return false;
		
	}
	public long getProductPrice(long productNo, long quantity) {
		long price = 0;
		for(int i=0; i<list.size(); i++) {
			if (list.get(i).getProduct_no() == productNo) {
				price = list.get(i).getPrice();
				break;
			}
		}
		return price*quantity;
		
	}

}
