package com.cafe24.shoppingmall.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.shoppingmall.frontend.service.MemberService;
import com.cafe24.shoppingmall.frontend.vo.MemberVo;

@Controller
@RequestMapping("/user")
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@GetMapping("/join")
	public String join() {
		return "user/join";
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute MemberVo memberVo) {
		System.out.println("MemberVo = "+memberVo);
//		String result = memberService.join(memberVo);
		
		
		return "user/login";
	}
	
}
