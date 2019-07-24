package com.cafe24.shoppingmall.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;

@Repository
public class ShopDao {
	@Autowired
	private SqlSession sqlSession;

	public long addProduct(ProductVo productVo) {
		
		sqlSession.insert("product.addproduct", productVo);
		
		return 1L;
	}

//	public Long addOption(OptionVo optionVo) {
//		sqlSession.insert("option.addOption", optionVo);
//		System.out.println("lastno = "+optionVo.getOpt_no());
//		return optionVo.getOpt_no();
//	}
//
//	public Long isExistThisOption(OptionVo optionVo) {
//		return sqlSession.selectOne("option.isExistThisOption", optionVo);
//		
//	}
//	
//	public long maxOptionProNoInOptionNo(OptionVo optionVo) {
//		long no = 0;
//		Long result = sqlSession.selectOne("option.maxOptionProNoInOptionNo");
//		if(null != result)
//				no = (Long)result;
//		System.out.println("no = " + no);
//	return no;
//	}
//
//	public boolean addOptionDetail(OptionVo optionVo) {
//			return 1 == sqlSession.insert("option.addOptionDetail", optionVo);
//		
//	}

	public long maxProductDetailNo(ProductVo productVo) {
		Long pd_detail_no = sqlSession.selectOne("product.maxProductDetailNo", productVo); 
		if(null == pd_detail_no)
			pd_detail_no = 0L;
		return pd_detail_no;
	}

	public boolean addProductDetail(List<ProductDetailVo> list) {
		int num = list.size();
		for(ProductDetailVo productDetailVo:list) {
			sqlSession.insert("product.addProductDetail", productDetailVo);
			num--;
		}
			return 0 == num;
	}
	
	/*  SELECT  */
	//모든 상품리스트 조회
	public List<ProductVo> getAllProductList() {
		return sqlSession.selectList("product.getAllProductList");
		
	}
	//진열상태가 true인 상품리스트 조회
	public List<ProductVo> getProductList() {
		
		return sqlSession.selectList("product.getProductList");
	}

	//테스트용 상품디테일 삭제
	public boolean deleteProductDetail() {
		return 0 < sqlSession.delete("product.deleteProductDetail");
		
	}

	public boolean deleteProduct() {

		return 0 < sqlSession.delete("product.deleteProduct");
	}
	
	//특정 상품 조회
	public ProductVo getSpecificProduct(long productNo) {
		return sqlSession.selectOne("product.getSpecificProduct",productNo);
		
	}

	//특정 상품상세 조회
	public List<ProductDetailVo> getSpecificProductDetail(long productNo) {
		return sqlSession.selectList("product.getSpecificProductDetail", productNo);
	}
	
	//특정 상품상세 조회
	

	
	
	
	
	
}
