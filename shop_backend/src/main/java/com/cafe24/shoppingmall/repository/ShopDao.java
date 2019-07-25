package com.cafe24.shoppingmall.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.repository.vo.CategoryVo;
import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;

@Repository
public class ShopDao {
	@Autowired
	private SqlSession sqlSession;

	/*  CREATE  */
	// 상품 생성
	public long addProduct(ProductVo productVo) {
		
		sqlSession.insert("product.addproduct", productVo);
		
		return 1L;
	}

	// 카테고리 생성
	public boolean addCategory(List<CategoryVo> categoryList) {
		int num = categoryList.size();
		for(CategoryVo categoryVo:categoryList) {
		if(categoryVo.getSub_no()==0L) {
			if(1 == sqlSession.insert("category.addMainCategory", categoryVo))
				num--;
			}
		else {
			if(1 == sqlSession.insert("category.addSubCategory", categoryVo))
				num--;
			}
		}
		System.out.println("num = "+num);
		return num == 0;
	}

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
	
	//특정 상품 조회
	public ProductVo getSpecificProduct(long productNo) {
		return sqlSession.selectOne("product.getSpecificProduct",productNo);
		
	}

	//특정 상품상세 조회
	public List<ProductDetailVo> getSpecificProductDetail(long productNo) {
		return sqlSession.selectList("product.getSpecificProductDetail", productNo);
	}

	/*  UPDATE  */
	//특정 상품 수정
	public boolean updateProduct(ProductVo productVo) {
		return 1==sqlSession.update("product.updateProduct", productVo);
		
	}

	//특정 상품상세 수정
	public boolean updateProductDetail(ProductDetailVo pdv) {
		return 1==sqlSession.update("product.updateProductDetail",pdv);
		
	}
	
	/*  DELETE  */
	
	//해당 상품 특정 옵션 삭제
	public boolean deleteProductOption(ProductDetailVo pdv) {
		return 1 == sqlSession.delete("product.deleteProductOption", pdv);
	}
	
	//해당 상품삭제 전 옵션 삭제
	public boolean deleteProductDetailInProduct(long product_no) {
		return 0<sqlSession.delete("product.deleteProductDetailInProduct",product_no);
	}
	
	//해당 상품 삭제
	public boolean deleteProduct(long product_no) {
		return 1 == sqlSession.delete("product.deleteProduct",product_no);
	}

	
	
	
	
	
}
