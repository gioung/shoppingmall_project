package com.cafe24.shoppingmall.frontend.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.shoppingmall.frontend.service.OptionService;
import com.cafe24.shoppingmall.frontend.vo.OptionVo;

@Controller
@RequestMapping("/option")
public class OptionController {
	@Autowired
	OptionService optionService;

	@GetMapping(value="/list")
	@ResponseBody
	public List<String> createOptionList(@RequestParam("option1")String[] option1,
			@RequestParam("option2")String[] option2){
		
			List<OptionVo> optionVoList = new ArrayList<>();
			optionVoList.add(new OptionVo("색상", Arrays.asList(option1)));
			optionVoList.add(new OptionVo("사이즈", Arrays.asList(option2)));
			
			List<String> optionList = optionService.createOptionList(optionVoList);
//			System.out.println("optionList = "+optionList);
			
			return optionList;
	}
}
