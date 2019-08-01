package com.cafe24.shoppingmall.repository;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.shoppingmall.repository.vo.OrderVo;
import com.cafe24.shoppingmall.repository.vo.OrderedProductVo;

@Repository
public class OrderDao {
	@Autowired
	private SqlSession sqlSession;

	// INSERT
	//주문 하기
	public boolean doOrder(@Valid OrderVo orderVo) {
		return 1 == sqlSession.insert("order.doOrder", orderVo);
		
	}
	//주문 내역 삽입
	public boolean addOrderList(List<OrderedProductVo> list) {
		return 0< sqlSession.insert("order.addOrderList", list);
		
	}
	
	//SELECT
	//주문번호에 대응하는 회원인지 확인
	public boolean isOwner(OrderVo orderVo) {
		OrderVo result = sqlSession.selectOne("order.isOwner", orderVo);
		if(null == result)
			return false;
		return orderVo.getId().equals(result.getId());
	}
	
	//해당 id의 주문내역 조회
	public List<OrderVo> getOrderList(String id) {
		List<OrderVo> orderList = sqlSession.selectList("order.getOrderList", id);
		return orderList;
	}
	
	//주문 상세 내역 조회
	public List<OrderedProductVo> getOrderDetailList(OrderVo orderVo) {
		List<OrderedProductVo> orderDetailList = sqlSession.selectList("order.getOrderDetailList", orderVo);
		return orderDetailList;
	}
	
	
	
	// DELETE
	//주문 취소
	public boolean cancelOrder(OrderVo orderVo) {

		return 1==sqlSession.delete("order.cancelOrder", orderVo);
	}
	
	//주문 내역 취소
	public boolean cancelOrderList(OrderVo orderVo) {
		
		return 0 < sqlSession.delete("order.cancelOrderList", orderVo);
	}
	
	
	
	
}
