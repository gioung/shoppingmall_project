package com.cafe24.shoppingmall.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.shoppingmall.repository.CartDao;
import com.cafe24.shoppingmall.repository.vo.CartVo;

@Service
public class CartService {

@Autowired
CartDao cartDao;

	//임시 아이디 생성
	private String TempId;

	/* ######## INSERT ######### */
	//장바구니 담기
	public boolean addProductToCart(CartVo cartVo) {
		System.out.println("cartVoId = " + cartVo.getId());
		if(null == cartVo.getId()) {
			TempId = createTempId();
			System.out.println("TempId = " + TempId);
			cartVo.setId(TempId);
		}
	
	// 이미 담은 상품인지 확인
	// 이미 담겨있다면 수량만 증가 시켜준다.
	CartVo existCartVo = cartDao.getProductInCart(cartVo);
	if(null != existCartVo) {
		cartVo.setQty(existCartVo.getQty()+cartVo.getQty());
		cartVo.setSeq_no(existCartVo.getSeq_no());
		return cartDao.addProductQty(cartVo);
	}
		
	//seq_no 정하기
	Long seq_no = cartDao.getMaxSeqNo(cartVo.getId());
	if(null==seq_no) {
		cartVo.setSeq_no(1L);
	}
	else
		cartVo.setSeq_no(seq_no+1);
	
	//장바구니 insert
	
	return cartDao.addProductToCart(cartVo);
		
	}


	private String createTempId() {
		StringBuffer strbuff = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    int rIndex = random.nextInt(3);
		    switch (rIndex) {
		    case 0:
		    	//소문자 a-z
		        strbuff.append((char) ((int) (random.nextInt(26)) + 97));
		        break;
		    case 1:
		        //대문자 A-Z
		    	strbuff.append((char) ((int) (random.nextInt(26)) + 65));
		        break;
		    case 2:
		        //숫자 0-9
		    	strbuff.append((random.nextInt(10)));
		        break;
		    }
		}
		
		return strbuff.toString();
	}
	
	public String getTempId() {
		return TempId;
	}
	/* ######## DELETE ######### */
	public boolean deleteCart(String id) {
		return cartDao.deleteCart(id);
		
	}
}
