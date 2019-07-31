package com.cafe24.shoppingmall.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.CartDao;
import com.cafe24.shoppingmall.repository.OrderDao;
import com.cafe24.shoppingmall.repository.UserDao;
import com.cafe24.shoppingmall.repository.vo.CartVo;
import com.cafe24.shoppingmall.repository.vo.MemberVo;
import com.cafe24.shoppingmall.repository.vo.OrderVo;
import com.cafe24.shoppingmall.repository.vo.OrderedProductVo;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	@Autowired
	CartService cartService;
	@Autowired
	CartDao cartDao;
	@Autowired
	UserDao userDao;
	/* ####### CREATE ###### */
	//주문하기
	public boolean doOrder(OrderVo orderVo) {
		//주문한 자와 받는 자가 일치하는지 확인
		// 일치하면 recv_name, tel을 set하기
		if(orderVo.isIs_same()) {
			orderVo.setRecv_name(orderVo.getOrd_name());
			orderVo.setRecv_tel(orderVo.getOrd_tel());
		}
		
		//회원유무 및 주소 비어있는지 검사
		if(orderVo.isIsmember()) {
			if(userDao.existMemberAddress(orderVo.getId())) {
			//member table update
			MemberVo memberVo = new MemberVo();
			memberVo.setAddress(orderVo.getDestination());
			memberVo.setEmail(orderVo.getId());
			userDao.addMemberAddress(memberVo);
			}
		}
		else {
			//앞선 테스트에서 임시아이디가 만들어져 있으므로 그대로 쓰면된다.<테스트용>
			orderVo.setId(cartService.getTempId());
		}
		//장바구니 유무 검사
		if(orderVo.isIscart()) {
			//add OrderedProductList from cartList
			System.out.println(orderVo.getId());
			List<CartVo> cartList = cartDao.getProductListInCart(orderVo.getId());
			System.out.println("CartList = "+cartList);
			if(null == cartList)
				return false;
			//orderList 생성
			List<OrderedProductVo> orderList= new ArrayList<>(cartList.size());
			orderVo.setOrderList(orderList);
			
			for(int i=0; i<cartList.size(); i++) {
				CartVo cartVo = cartList.get(i);
				//가격은 프론트 단에서 계산 할 것이므로 임의로 넣음.
				orderList.add(new OrderedProductVo(orderVo.getOrder_no(), cartVo.getProduct_no(), cartVo.getPd_detail_no()
						, cartVo.getQty(), 10000L));
			}
			
			//delete cart
			cartDao.deleteCart(orderVo.getId());
			
		}
		
		//order table insert
		if(orderDao.doOrder(orderVo)) {
			//orderList table insert
			System.out.println("OrderList = "+ orderVo.getOrderList());
			return orderDao.addOrderList(orderVo.getOrderList());
			}
		
		return false;
	}
	
	
	
	/* ####### DELETE ###### */
	public boolean cancelOrder(OrderVo orderVo) {
		boolean isOwner=orderDao.isOwner(orderVo);
		System.out.println("isOwner = " + isOwner);
		//주문번호에 대응하는 회원인지 확인
		if(isOwner) {
			orderDao.cancelOrderList(orderVo);
			return orderDao.cancelOrder(orderVo);
		}
		return false;
		
	}

}
