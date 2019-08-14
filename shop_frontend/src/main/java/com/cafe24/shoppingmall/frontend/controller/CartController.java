package com.cafe24.shoppingmall.frontend.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.shoppingmall.frontend.dto.JSONResult2;
import com.cafe24.shoppingmall.frontend.service.CartService;
import com.cafe24.shoppingmall.frontend.vo.CartVo;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	CartService cartService;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public JSONResult2 addCart(@ModelAttribute CartVo cartVo, HttpServletRequest request, HttpServletResponse response,
			Principal principal) {
//		if(principal.getName() != null)
//			System.out.println("principal = "+principal.getName());
		/* 회원일경우 */
		//아이디 유무 확인 후 아이디값과 ismember 넣기(비회원이면 null)
		if(principal != null) {
			cartVo.setId(principal.getName());
			CartVo myCartVo = cartService.addCart(cartVo);
			return JSONResult2.success(myCartVo);
		}
		
		/* 비회원일경우 */
		//쿠키 존재유무 확인
		boolean isTempId = false;
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("TempId")) {
					System.out.println("쿠키 존재");
					cartVo.setId(cookie.getValue());
					isTempId = true;
					break;
			}
		}
		CartVo myCartVo = cartService.addCart(cartVo);
		//쿠키 값이 없으면
		if(!isTempId) {
			System.out.println("쿠키 생성");
			Cookie TempCookie = new Cookie("TempId", myCartVo.getId());
			TempCookie.setMaxAge(60*60*24*7); //쿠키 일주일간 생존
			response.addCookie(TempCookie);
		}
		
		return JSONResult2.success(myCartVo);
		
	}
	//장바구니 수량변경
	@PostMapping("/update/{seq_no}")
	@ResponseBody
	public Long updateCart(@PathVariable("seq_no") long seq_no,
			@RequestParam("qty")long qty, Authentication authentication) {
		UserDetails userdetails = (UserDetails)authentication.getPrincipal();
		String id = userdetails.getUsername();
		
		Long updatedQty = cartService.updateCart(seq_no,id,qty);
		
		
		return updatedQty;
	}
	
	//장바구니 삭제
	@GetMapping("/delete/{seq_no}")
	@ResponseBody
	public Boolean deleteCart(@PathVariable("seq_no") long seq_no, Authentication authentication) {
		UserDetails userdetails = (UserDetails)authentication.getPrincipal();
		String id = userdetails.getUsername();
		Boolean isDeleted;
		
		isDeleted = cartService.deleteCart(seq_no, id);
		
		if(isDeleted == null)
			isDeleted=false;
		
		return isDeleted;
	}
	
	@GetMapping("/delete")
	public String deleteCartAll(Authentication authentication) {
		UserDetails userdetails = (UserDetails)authentication.getPrincipal();
		String id = userdetails.getUsername();
		Boolean isDeleted;
		isDeleted = cartService.deleteCartAll(id);
		
		if(isDeleted)
			return "redirect:/user/cart";
		else
			return "redirect:/user/cart?result=fail";
	}
}
