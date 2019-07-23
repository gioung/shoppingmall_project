package com.cafe24.shoppingmall.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.OptionVo;
import com.cafe24.shoppingmall.service.OptionService;

import io.swagger.annotations.ApiOperation;

@RestController("optionAPIController")
@RequestMapping(value = "/api/option")
public class OptionController {
	
	@Autowired
	OptionService optionService;
	
	//옵션 등록
	@ApiOperation(value = "옵션 등록")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<JSONResult> addOption(@RequestBody OptionVo optionVo) {
		boolean judge = optionService.addOption(optionVo);
	
		if(judge){
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(optionVo));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("옵션 등록이 이미 되어있거나 실패했습니다."));
			
		}
	}
}
