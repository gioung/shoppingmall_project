package com.cafe24.shoppingmall.frontend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cafe24.shoppingmall.frontend.dto.JSONResult;
import com.cafe24.shoppingmall.frontend.dto.OptionListDTO;
import com.cafe24.shoppingmall.frontend.vo.OptionVo;

@Service
public class OptionService {
	
	@Autowired
	RestTemplate restTemplate;

	public List<String> createOptionList(List<OptionVo> optionList) {
		
		String endpoint = "http://localhost:8888/v1/api/option/list";
		OptionListDTO options = new OptionListDTO();
		options.setOptionList(optionList);
		
		OptionVoList optionVoList = restTemplate.postForObject(endpoint, options, OptionVoList.class);
		return optionVoList.getData();
	}
	
	private static class OptionVoList extends JSONResult<List<String>>{
		
	}
}
