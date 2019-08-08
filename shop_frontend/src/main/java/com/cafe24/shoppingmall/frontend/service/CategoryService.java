package com.cafe24.shoppingmall.frontend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.vo.CategoryVo;

@Service
public class CategoryService {

	@Autowired
	private RestTemplate restTemplate;
	//메인카테고리 조회 요청
	public List<CategoryVo> getMainCategoryList(){
		String endpoint = "http://localhost:8888/v1/api/product/category/list";
		JSONResultMainCategoryList jsonResult = restTemplate.getForObject(endpoint, JSONResultMainCategoryList.class);
		if(null == jsonResult.getData())
			return new ArrayList<CategoryVo>();
		List<CategoryVo> categoryList = jsonResult.getData();
		List<CategoryVo> resultList = new ArrayList<>(categoryList.size());
		
		for(CategoryVo categoryVo : categoryList) {
			//메인 카테고리만 add
			if(categoryVo.getSub_no() == 0)
				resultList.add(categoryVo);
		}
		return resultList;
	}
	
	public List<CategoryVo> getSubCategoryList(Long main_no) {
		String endpoint = "http://localhost:8888/v1/api/product/category/list/"+main_no;
		JSONResultMainCategoryList jsonResult = restTemplate.getForObject(endpoint, JSONResultMainCategoryList.class);
		if(null == jsonResult.getData())
			return new ArrayList<CategoryVo>();
		List<CategoryVo> categoryList = jsonResult.getData();
		List<CategoryVo> resultList = new ArrayList<>(categoryList.size());
		
		for(CategoryVo categoryVo : categoryList) {
			//메인 카테고리만 add
			if(categoryVo.getSub_no() != 0)
				resultList.add(categoryVo);
		}
		return resultList;
	}
	// DTO Class
	private static class JSONResultMainCategoryList extends JSONResult<List<CategoryVo>> {
	}
	
	


}
