package com.cafe24.shoppingmall.repository;

import java.util.List;

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
	//카트리스트 조회
	public List<CartVo> getProductListInCart(String id) {
			
		return sqlSession.selectList("cart.getProductListInCart", id);
	}
	
	//특정 카트 조회
	public CartVo getProductToOrder(String id,long seq_no) {
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		cartVo.setSeq_no(seq_no);
		
		return sqlSession.selectOne("cart.getProductToOrder", cartVo);
	}
	
	// 상품이 담겨져 있는가?
	public CartVo getProductInCart(CartVo cartVo) {
		
		return sqlSession.selectOne("cart.getProductInCart", cartVo);
	}	
	
	// ######### UPDATE ############
	public boolean addProductQty(CartVo cartVo) {
		
		return 1 == sqlSession.update("cart.addProductQty", cartVo);
	}
	
	//회원가입시 tempId -> 회원 Id
	public boolean convertTempIdToUser(CartVo cartVo) {

		return 0 < sqlSession.update("cart.convertTempIdToUser", cartVo);
	}
	
	//상품 수량 수정
	public boolean updateProductQty(CartVo cartVo) {
		return 1 == sqlSession.update("cart.updateProductQty", cartVo);
	}
	
	// ######### DELETE ############
	//장바구니 삭제
	public boolean deleteCart(String id) {
		
		return 0 < sqlSession.delete("cart.deleteCart", id);
	}
	
	//장바구니 특정 물품 삭제
	public boolean deleteOneCart(String id, Long seq_no) {
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		cartVo.setSeq_no(seq_no);
		
		return 1 == sqlSession.delete("cart.deleteProductInCart", cartVo);
	}
	
//	장바구니 상품 삭제
	public boolean deleteProductInCart(CartVo cartVo) {
		
		return 1 == sqlSession.delete("cart.deleteProductInCart", cartVo);
	}

	

	

	
	

	

	
}
