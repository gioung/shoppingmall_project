package com.cafe24.shoppingmall.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping({"", "/main"})
	public String index() {
		return "admin/index";
	}
	
}
