package com.cafe24.shoppingmall.frontend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cafe24.shoppingmall.frontend.service.CategoryService;
import com.cafe24.shoppingmall.frontend.service.ProductService;
import com.cafe24.shoppingmall.frontend.vo.CategoryVo;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	//상품상세 조회
	@RequestMapping("/{no}")
	public String productPage(@PathVariable("no")long product_no, Model model) {
		Map<String, Object> product = productService.getProduct(product_no);
		model.addAttribute("product", product.get("product")); 
		model.addAttribute("productDetailList",product.get("productDetailList"));
		
		// 상품 상세화면 - 메인카테고리 출력
		List<CategoryVo> mainCategoryList = categoryService.getMainCategoryList();
		model.addAttribute("mainCategoryList", mainCategoryList);
		
		return "goods/item";
	}
}
