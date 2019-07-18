package com.cafe24.shoppingmall.controller.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.service.UserService;
import com.cafe24.shppingmall.repository.vo.MemberVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController("userAPIController")
@RequestMapping(value = "/api/user")
public class UserController {

	@Autowired
	UserService userService;
	/* api 리스트
	 * 회원가입들
	 * 장바구니 담기
	 * 장바구니 목록 (GET /cart)
	 * 
	 *  */
	
	// 이메일 존재 여부
	// 회원 가입

	// 이메일 존재여부 , ajax
	@ApiOperation(value = "이메일 존재 여부")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "email", value = "이메일주소", required = true, paramType = "query", dataType = "string", defaultValue = "") })
	@RequestMapping(value = "/checkemail", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> checkEmail(@RequestParam(value = "email", required = true, defaultValue = "") String email) {

		boolean judge = userService.existEmail(email);
		
		if(judge == false)
			return new ResponseEntity<JSONResult>(JSONResult.success(judge),HttpStatus.OK);
		else
			return new ResponseEntity<JSONResult>(JSONResult.fail("중복된 이메일 존재"),HttpStatus.OK);
	}

	
	// 회원가입 , forwarding
	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<JSONResult> signup(@Valid @RequestBody MemberVo memberVo, BindingResult result) {
		
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			return new ResponseEntity<JSONResult>(JSONResult.fail("데이터가 유효하지 않습니다."),HttpStatus.BAD_REQUEST);
		}
		// Service에 삽입 요청을 하는 code
		return new ResponseEntity<JSONResult>(JSONResult.success(userService.registerMember(memberVo)),HttpStatus.CREATED);
	}

	// 로그인, redirect
	@ApiOperation(value = "로그인")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "email", value = "이메일주소", required = true, paramType = "body", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "password", value = "비밀번호", required = true, paramType = "body", dataType = "string", defaultValue = "")})
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public JSONResult login(@RequestParam(value = "email", required = true, defaultValue = "") String email,
			@RequestParam(value = "password", required = true, defaultValue = "") String password) {

		// 특수문자 검출
		// 특수문자 검출시 Redirect

		// 특수문자가 없을시 서비스로 value 넘김
		//회원정보가 유효할 경우 TRUE 반환
		boolean judge = userService.existMember(email, password);
		if(judge == true)
			return JSONResult.success(judge);
		else
			return JSONResult.fail("로그인 실패");
	}

	
	//회원정보수정, forward
	  @ApiOperation(value = "회원정보 수정")
	  @ApiImplicitParams({
			@ApiImplicitParam(name = "email", value = "이메일주소", required = true, paramType = "body", dataType = "string", defaultValue = "")
			})
	  @RequestMapping(value = "/info", method = RequestMethod.PUT) 
	  public JSONResult userModify(@Valid MemberVo memberVo, BindingResult result) { 
	  //유효성 체크
	  boolean judge =  userService.updateMember(memberVo);
	  //유효할시 성공
	  if(judge == true)
			return JSONResult.success(judge);
		else
			return JSONResult.fail("회원정보 수정 실패"); 
	  }
	 
	  //회원 탈퇴, redirect
	  @ApiOperation(value = "회원 탈퇴")
	  @RequestMapping(value = "/out", method = RequestMethod.DELETE)
	  public JSONResult userDelete(@RequestParam(value = "email", required = true, defaultValue = "") String email) {
		//패스워드 인증은 되었다고 가정.
		//유효성 체크
		  boolean judge =  userService.deleteMember(email);
		//유효할시 성공
		  if(judge == true)
				return JSONResult.success(judge);
		  else
				return JSONResult.fail("회원삭제 실패"); 
		  
	  }
	  
	 
		
	
	  
}
