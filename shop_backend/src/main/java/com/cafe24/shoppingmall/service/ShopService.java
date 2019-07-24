package com.cafe24.shoppingmall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.ShopDao;
import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;

@Service
public class ShopService {
	@Autowired
	private ShopDao shopDao;
	private List<ProductVo> list;
	
	
	public boolean addProduct(ProductVo productVo, List<ProductDetailVo> productDetailVoList) {
	
	//상품 등록
	long product_no = shopDao.addProduct(productVo);
	//###상품 상세 등록###
	//등록된 상품 번호로 product_no 설정
	for(int i=0; i< productDetailVoList.size(); i++) {
		ProductDetailVo productDetailVo = productDetailVoList.get(i); 
		productDetailVo.setProduct_no(product_no);
		productDetailVo.setPd_detail_no(i+1);
	}
	
	return shopDao.addProductDetail(productDetailVoList); 
		
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
	public boolean getProduct(long productNo) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
