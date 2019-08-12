package com.cafe24.shoppingmall.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.dto.OptionListDTO;
import com.cafe24.shoppingmall.repository.vo.OptionVo;
import com.cafe24.shoppingmall.service.OptionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.swagger.annotations.ApiOperation;

@RestController("optionAPIController")
@RequestMapping(value = "/api/option")
public class OptionController {
	
	@Autowired
	OptionService optionService;
	
	//옵션 등록
	@ApiOperation(value = "옵션 리스트 조회")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<JSONResult> addOption(@RequestBody OptionListDTO optionListDTO) {
		
//		Gson gson = new GsonBuilder().create();
//		OptionVo option1 = gson.fromJson(String.valueOf(map.get("option1")), OptionVo.class);
//		OptionVo option2 = gson.fromJson(String.valueOf(map.get("option2")), OptionVo.class);
		
		List<OptionVo> optionList = optionListDTO.getOptionList();
		
		List<String> optionValList = optionService.addOptionValList(optionList);
	
		if(optionValList != null){
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(optionValList));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("옵션 리스트 생성 실패"));
			
		}
	}
}
