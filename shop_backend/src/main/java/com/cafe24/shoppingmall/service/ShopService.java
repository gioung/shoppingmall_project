package com.cafe24.shoppingmall.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.ShopDao;
import com.cafe24.shoppingmall.repository.vo.CategoryVo;
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
	
	
	public boolean addCategory(List<CategoryVo> categoryList) {
		return shopDao.addCategory(categoryList);
		
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
	
	//카테고리 전체 조회
	public List<CategoryVo> getCategoryList() {
		return shopDao.getCategoryList();
		
	}
	//가격 계산
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
		
		
	//########### DELETE ############	
	public boolean deleteProductDetail(ProductDetailVo pdv) {
		return shopDao.deleteProductOption(pdv);
			
	}	
		
	public boolean deleteProduct(long product_no) {
		if(shopDao.deleteProductDetailInProduct(product_no))
			return shopDao.deleteProduct(product_no);
		
		return false;
	}
	
	/* 서브 카테고리 삭제 */
	public boolean deleteSubCategory(CategoryVo categoryVo) {
		// 
		return shopDao.deleteSubCategory(categoryVo);
	}
	
	/* 메인 카테고리 삭제 */
	public boolean deleteMainCategory(CategoryVo categoryVo) {
		// TODO Auto-generated method stub
		return shopDao.deleteMainCategory(categoryVo);
	}
	
	
	//########### UPDATE ############	
	//관리자 상품 수정
	public boolean updateProduct(ProductVo productVo, List<ProductDetailVo> productDetailVoList) {
		System.out.println("Service 실행");
		int num = productDetailVoList.size();
		if(shopDao.updateProduct(productVo)) {
			for(ProductDetailVo pdv:productDetailVoList)
				if(shopDao.updateProductDetail(pdv))
					num--;
		}
		System.out.println();
		return num==0;
	}
	public List<CategoryVo> getSubCategoryList(long no) {
		
		return shopDao.getSubCategoryList(no);
	}
	
	/* 카테고리 수정 */
	public boolean updateMainCategory(CategoryVo categoryVo) {
			
		return shopDao.updateMainCategory(categoryVo);
	}
	public boolean updateSubCategory(CategoryVo categoryVo) {
		
		return shopDao.updateSubCategory(categoryVo);
	}
	

	
	
	
	
	
	
	
	
	

}
