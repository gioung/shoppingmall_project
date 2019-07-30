package com.cafe24.shoppingmall.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.repository.vo.CartVo;

@Repository
public class CartDao {
	@Autowired
	SqlSession sqlSession;
	// ######### INSERT ############
	public boolean addProductToCart(CartVo cartVo) {
		
		return 1 == sqlSession.insert("cart.addProductToCart", cartVo);
	}
	
	// ######### SELECT ############
	public Long getMaxSeqNo(String id) {
		return sqlSession.selectOne("cart.getMaxSeqNo", id);
			
	}
	
	// 상품이 담겨져 있는가?
	public CartVo getProductInCart(CartVo cartVo) {
		
		return sqlSession.selectOne("cart.getProductInCart", cartVo);
	}	
	
	// ######### UPDATE ############
	public boolean addProductQty(CartVo cartVo) {
		
		return 1 == sqlSession.update("cart.addProductQty", cartVo);
	}

	// ######### DELETE ############
	public boolean deleteCart(String id) {
		
		return 0 < sqlSession.delete("cart.deleteCart", id);
	}

	

	
}
