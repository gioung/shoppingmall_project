package com.cafe24.shoppingmall.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.shoppingmall.frontend.service.CategoryService;
import com.cafe24.shoppingmall.frontend.service.FileuploadService;
import com.cafe24.shoppingmall.frontend.service.ProductService;
import com.cafe24.shoppingmall.frontend.service.UserSerivce;
import com.cafe24.shoppingmall.frontend.vo.CategoryVo;
import com.cafe24.shoppingmall.frontend.vo.MemberVo;
import com.cafe24.shoppingmall.frontend.vo.OptionVo;
import com.cafe24.shoppingmall.frontend.vo.ProductDetailVo;
import com.cafe24.shoppingmall.frontend.vo.ProductVo;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserSerivce userService;
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	FileuploadService fileuploadService;
	
	//ADMIN 몌인
	@GetMapping({"", "/main"})
	public String index() {
		return "admin/index";
	}
	
	//회원 관리 페이지
	@GetMapping("/user")
	public String userPage(Model model) {
		//현재 가입한 모든 회원정보를 가져오는 서비스 코드
		List<MemberVo> memberList = userService.getAllMebmer();
		model.addAttribute("memberList", memberList);
		
		return "admin/user";
	}
	
	//상품 관리 페이지
	@GetMapping("/product")
	public String productPage(Model model) {
		//상품목록 조회
		List<ProductVo> productList = productService.getAdminProductList();
		System.out.println("productList = "+ productList);
		model.addAttribute("productList", productList);
		return "admin/product";
	}
	
	//상품 등록 페이지
	@GetMapping("/product/registration")
	public String productCreatePage(Model model) {
		//상품등록 페이지
		List<CategoryVo> categoryList = categoryService.getMainCategoryList();
		model.addAttribute("categoryList", categoryList);
		return "admin/product_create";
	}
	
	//상품 등록
	@PostMapping("/product/registration")
	public String productCreate(@ModelAttribute ProductVo productVo, BindingResult bindingResult,
			@RequestParam(value ="option1")String[] option1, 
			@RequestParam(value="option2")String[] option2
			/*@RequestParam("upload-image")MultipartFile multipartFile*/) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			return "redirect:/admin/product?result=fail";
		}
		
		//file저장하기 
//		if(multipartFile!=null) {
//			String url = fileuploadService.restore(multipartFile); 
//			System.out.println("이미지 url = "+url);
//			
//			productVo.setImage(url);
//		}
		
		//리스트로 옵션값 변환
		List<OptionVo> optionList = new ArrayList<>();
		optionList.add(new OptionVo("사이즈",Arrays.asList(option1)));
		optionList.add(new OptionVo("컬러",Arrays.asList(option2)));
		
		List<ProductDetailVo> productDetailList = new ArrayList<>();
		
		Map<String,Object> map = new HashMap<>();
		map.put("product", productVo);
		map.put("productDetailList", productDetailList);
		map.put("optionList",optionList);
		
		//addProduct
		String result = productService.addProduct(map);
		
		
		
		if(result.equals("success")) {
			return "redirect:/admin/product";
		}
		else 
			return "redirect:/admin/product?result=fail";
		
	}
	
	//해당 메인카테고리의 서브 카테고리
	@GetMapping("/category/list/{main_no}")
	@ResponseBody
	public List<CategoryVo> getSubCategoryList(@PathVariable("main_no")long main_no){
		List<CategoryVo> subCategoryList = categoryService.getSubCategoryList(main_no);
		
		return subCategoryList;
	}
}
