package com.cafe24.shoppingmall.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.shoppingmall.frontend.service.CartService;
import com.cafe24.shoppingmall.frontend.service.OrderService;
import com.cafe24.shoppingmall.frontend.vo.CartVo;
import com.cafe24.shoppingmall.frontend.vo.OrderVo;

@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	CartService cartService;
	@Autowired
	OrderService orderService;

	@GetMapping("/{seq_no}")
	public String orderPage(@PathVariable("seq_no")long seq_no,
			Authentication authentication,
			Model model) {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		String id = userDetails.getUsername();
		List<CartVo> cartList = cartService.getCart(id, seq_no);
		
		long pay = 0;
		for(CartVo cartVo:cartList) {
			pay += cartVo.getPrice();
		}
		
		model.addAttribute("cartList", cartList);
		model.addAttribute("pay", pay);
		return "user/order";
	}
	
	@GetMapping("")
	public String orderPageForAllCart(Authentication authentication,
			Model model) {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		String id = userDetails.getUsername();
		List<CartVo> cartList = cartService.getCart(id);
		
		System.out.println("cartList = "+cartList);
		long pay = 0;
		for(CartVo cartVo:cartList) {
			pay += cartVo.getPrice();
		}
		
		model.addAttribute("cartList", cartList);
		model.addAttribute("pay", pay);
		return "user/order";
	}
	
//	@GetMapping("/product")
//	public String orderPageFromProductPage(@ModelAttribute CartVo cartVo,
//			Model model) {
//		System.out.println("CartVo = "+cartVo);
//		cartVo.setPrice(cartVo.getPrice()*cartVo.getQty());
//		model.addAttribute("cartList", cartVo);
//		return "user/order";
//	}
	
	@PostMapping("")
	public String order(@ModelAttribute OrderVo orderVo,Authentication authentication) {
		//유저 아이디 유무 검사
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		if(userDetails.getUsername()!=null) {
			orderVo.setIsmember(true);
			orderVo.setId(userDetails.getUsername());
		}
		
		System.out.println("seq_no 확인 ="+orderVo);
		//service 단으로 보낸다.
		boolean isOrdered = orderService.doOrder(orderVo);
		
		return "redirect:/";
	}
}

