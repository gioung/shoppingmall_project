package com.cafe24.shoppingmall.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.MemberVo;
import com.cafe24.shoppingmall.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController("adminUserAPIController")
@RequestMapping(value = "/api/admin/user")
public class AdminUserController {
	
	@Autowired
	UserService userService;
	
	@ApiOperation(value = "회원 조회")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getAllMember() {
		//유저 조회
		List<MemberVo> memberList = userService.getAllMember();
		if(memberList != null) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(memberList));
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("가입한 유저가 존재하지 않습니다."));
		}
	}
}
