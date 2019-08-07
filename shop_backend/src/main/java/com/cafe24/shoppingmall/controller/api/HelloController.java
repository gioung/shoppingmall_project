package com.cafe24.shoppingmall.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;



@RestController
public class HelloController {
	
	@GetMapping("/hello")
	public ResponseEntity<JSONResult> hello(){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JSONResult.success("Hello World"));
	}

	@GetMapping("/hello2")
	public ResponseEntity<JSONResult> hello2(){
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(JSONResult.success("Hello World2"));
	}
	
}