package com.cafe24.shoppingmall.frontend.controller;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	public String join(@Valid @ModelAttribute MemberVo memberVo,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			return "redirect:/user/join?result=fail";
		}
		String result = memberService.join(memberVo);
		
		if("success".equals(result))
			return "user/login";
		else
			return "redirect:/user/join?result=fail";
	}
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	
	
	
}
