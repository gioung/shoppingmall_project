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

	public List<CategoryVo> getMainCategoryList(){
		String endpoint = "http://localhost:8888/v1/api/product/category/list";
		JSONResultMainCategoryList jsonResult = restTemplate.getForObject(endpoint, JSONResultMainCategoryList.class);
		
		List<CategoryVo> categoryList = jsonResult.getData();
		List<CategoryVo> resultList = new ArrayList<>(categoryList.size());
		
		for(CategoryVo categoryVo : categoryList) {
			//메인 카테고리만 add
			if(categoryVo.getSub_no() == 0)
				resultList.add(categoryVo);
		}
		return resultList;
	}
	
	// DTO Class
	private static class JSONResultMainCategoryList extends JSONResult<List<CategoryVo>> {
	}
}
