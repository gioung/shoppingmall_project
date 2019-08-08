package com.cafe24.shoppingmall.frontend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.shoppingmall.frontend.dto.JSONResult2;
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
	
	//카테고리 클릭시 
	  @RequestMapping("/category/{no}")
	  public String selectProductsByCategory(@PathVariable("no") Long main_no, Model model) {
		  //카테고리 클릭 - 해당 카테고리 상품 출력
		  List<ProductVo> productList = productService.getProductList(main_no);
		  
		  //카테고리 클릭 - 해당 카테고리의 서브 카테고리 출력
		  List<CategoryVo> subCategoryList = categoryService.getSubCategoryList(main_no);
		  
		  // 메인화면 - 메인카테고리 출력
		  List<CategoryVo> mainCategoryList = categoryService.getMainCategoryList();
		  model.addAttribute("mainCategoryList", mainCategoryList);
		  
		  model.addAttribute("productList", productList);
		  model.addAttribute("subCategoryList", subCategoryList);
		  model.addAttribute("mainCategoryList", mainCategoryList);
		  
		 return "main/index";
	}
	  
	//서브 카테고리 클릭시 
	  @GetMapping("/category/{no}/{sub_no}")
	  @ResponseBody public JSONResult2 selectProductsBySubCategory(@PathVariable("no") Long main_no,
			  @PathVariable("sub_no") Long sub_no) {
		  //카테고리 클릭 - 해당 카테고리 상품 출력
		  List<ProductVo> productList = productService.getProductList(main_no, sub_no);
		  
		  return JSONResult2.success(productList);
		 
	}
	  
	 
	
}
