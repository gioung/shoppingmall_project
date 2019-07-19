package com.cafe24.shoppingmall.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.repository.vo.ProductVo;

@Repository
public class ShopDao {
	@Autowired
	private SqlSession sqlSession;

	public boolean addProducts(Map<String, List<ProductVo>> Products) {
		return 0 < sqlSession.insert("product.addproducts", Products);
		
	}
	
}
