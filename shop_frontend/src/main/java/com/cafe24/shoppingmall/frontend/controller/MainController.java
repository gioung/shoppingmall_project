package com.cafe24.shoppingmall.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.shoppingmall.frontend.service.CategoryService;
import com.cafe24.shoppingmall.frontend.service.ProductService;
import com.cafe24.shoppingmall.frontend.vo.CategoryVo;
import com.cafe24.shoppingmall.frontend.vo.ProductVo;

@Controller
@RequestMapping( {"/", "/main"} )
public class MainController {
	
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("")
	public String main(Model model) {
		
		// 메인화면 - 상품 출력
		List<ProductVo> productList = productService.getProductList();
		model.addAttribute("productList", productList);
		
		// 메인화면 - 메인카테고리 출력
		List<CategoryVo> mainCategoryList = categoryService.getMainCategoryList();
		model.addAttribute("mainCategoryList", mainCategoryList);
		
		
		return "main/index";
	}
	
	
}
