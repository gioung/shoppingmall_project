package com.cafe24.shoppingmall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	// 상품 등록
	public boolean addProduct(ProductVo productVo, List<ProductDetailVo> productDetailVoList) {
		long product_no = shopDao.addProduct(productVo);
		//#상품 상세 등록#
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
	
	
	//########### SELECT ############
	
	//모든 상품 목록 조회
	public List<ProductVo> getAllProductList() {
			
			return shopDao.getAllProductList();
		}
	
	//진열상태가 true인 상품 목록 조회
	public List<ProductVo> getProductList() {
		return shopDao.getProductList();
	}
	
	//관리자 특정 상품 조회
	public Map<String,Object> getProduct(long productNo) {
		ProductVo productVo = shopDao.getSpecificProduct(productNo);
		List<ProductDetailVo> productDetailVoList = shopDao.getSpecificProductDetail(productNo);
		Map<String, Object> map = new HashMap<>();
		map.put("product", productVo);
		map.put("productDetailList", productDetailVoList);
		return map;		
	}
		
		
	//########### DELETE ############	
	public boolean deleteProductDetail() {
		return shopDao.deleteProductDetail();
			
	}	
		
	public boolean deleteProduct() {
		return shopDao.deleteProduct();
		
	}
	
	
	
	

}
