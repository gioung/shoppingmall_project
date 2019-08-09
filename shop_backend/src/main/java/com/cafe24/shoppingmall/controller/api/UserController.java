package com.cafe24.shoppingmall.controller.api;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.shoppingmall.dto.JSONResult;
import com.cafe24.shoppingmall.repository.vo.MemberVo;
import com.cafe24.shoppingmall.service.UserService;

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
		
		if(judge)
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("중복된 이메일 존재")); 
	}

	// selectByEmail
		@ApiOperation(value = "회원정보 가져오기")
		@RequestMapping(value = "/info", method = RequestMethod.GET)
		public ResponseEntity<JSONResult> getInfo(@RequestParam(value = "email", required = true, defaultValue = "") String email) {

			MemberVo memberVo = userService.getInfo(email);
			
			if(memberVo!=null)
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(memberVo));
			else
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("해당 회원정보가 없습니다.")); 
		}
	
	// 회원가입 , forwarding
	@ApiOperation(value = "회원가입")
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ResponseEntity<JSONResult> signup(@Valid @RequestBody MemberVo memberVo, BindingResult result) {
		
		if (result.hasErrors()) {
			System.out.println(result.getFieldErrors());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("유효하지 않은 값입니다."));
		}
		
		// Service에 삽입 요청을 하는 code
		return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(userService.registerMember(memberVo)));
		
	}

	// 로그인, redirect
	@ApiOperation(value = "로그인")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "email", value = "이메일주소", required = true, paramType = "body", dataType = "string", defaultValue = ""),
			@ApiImplicitParam(name = "password", value = "비밀번호", required = true, paramType = "body", dataType = "string", defaultValue = "")})
	@PostMapping(value="/login")
	public ResponseEntity<JSONResult> login(@RequestBody MemberVo memberVo) {
		
		Validator validator = 
				Validation.buildDefaultValidatorFactory().getValidator();
		
		Set<ConstraintViolation<MemberVo>> validatorResults = 
				validator.validateProperty(memberVo, "email");
		
		Set<ConstraintViolation<MemberVo>> validatorResults2 = 
				validator.validateProperty(memberVo, "password");
		
		// 아이디 또는 비밀번호 형식이 잘 안맞을 경우
		if(!validatorResults.isEmpty() || !validatorResults2.isEmpty()) 
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("아이디 또는 비밀번호가 올바르지 않은 형식입니다."));
		
		boolean judge = userService.existMember(memberVo);
		// 아이디 또는 비밀번호가 존재하는 경우
		if(judge == true)
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(memberVo));
		else
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("아이디 또는 비밀번호가 존재하지 않거나 틀렸습니다."));
		
	}
	
	//회원정보수정, forward
	  @ApiOperation(value = "회원정보 수정")
	  @ApiImplicitParams({
			@ApiImplicitParam(name = "email", value = "이메일주소", required = true, paramType = "body", dataType = "string", defaultValue = "")
			})
	  @RequestMapping(value = "/modification", method = RequestMethod.PUT) 
	  public ResponseEntity<JSONResult> userModify(@RequestBody @Valid MemberVo memberVo, BindingResult result) { 
		  if (result.hasErrors()) {
			  System.out.println(result.getFieldErrors());
			  return new ResponseEntity<JSONResult>(JSONResult.fail("데이터 유효성에 어긋납니다."),HttpStatus.BAD_REQUEST);
			}
		  
	//유효성 체크
	  boolean judge =  userService.updateMember(memberVo);
	  //유효할시 성공
	  if(judge == true)
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(memberVo));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.success("회원정보 수정 실패")); 
	  }
	 
	  //회원 탈퇴
	  @ApiOperation(value = "회원 탈퇴")
	  @RequestMapping(value = "/out", method = RequestMethod.DELETE)
	  public JSONResult userDelete(@RequestBody MemberVo memberVo) {
		//패스워드 인증은 되었다고 가정.
		//유효성 체크
		  boolean judge =  userService.deleteMember(memberVo);
		//유효할시 성공
		  if(judge == true)
				return JSONResult.success(memberVo);
		  else
				return JSONResult.fail("회원삭제 실패"); 
		  
	  }
	  
	 
		
	
	  
}
