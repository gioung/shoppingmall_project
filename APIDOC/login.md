## 로그인

로그인 요구 사항

1. 존재하는 아이디와 패스워드여야 한다.
  1. 아이디 또는 비밀번호 형식이 올바르게 되야 한다.



■ 실제동작코드 

```java
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
```



### 테스트케이스

#### case1. 성공 케이스

**Request**

HTTP Method = POST<br>
      Request URI = /api/user/login<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"email":"gioung9833@gmail.com","password":"namgioung123!"}



**Response**

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"email":"gioung9833@gmail.com","password":"namgioung123!"}}



■  테스트코드

```java
// case1. 성공케이스
	  @Test 
	  public void TestB_1() throws Exception { 
	  System.out.println("로그인 테스트");
	  MemberVo memberVo = new MemberVo();
	  String email = "gioung9833@gmail.com"; 
	  String password = "namgioung123!";
	  memberVo.setEmail(email);
	  memberVo.setPassword(password);
	  
	  ResultActions resultActions =
	  mockMvc.perform(post(SHAREDURL+"/login")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(new Gson().toJson(memberVo))
	  .characterEncoding("utf-8"));
	  
	  resultActions.andDo(print())
	  .andExpect(status().isOk()) 
	  .andExpect(jsonPath("$.result",is("success"))); 
	  }
```



#### case2. 존재하지 않는 아이디 또는 패스워드 인 경우

**Request**

HTTP Method = POST<br>
      Request URI = /api/user/login<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"email":"fkegme@gmail.com","password":"namgioung1234!"}



**Response**       

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"fail","message":"아이디 또는 비밀번호가 존재하지 않거나 틀렸습니다."}



■  테스트코드

```java
@Test 
	  public void TestB_2() throws Exception { 
	  MemberVo memberVo = new MemberVo();
	  String email = "fkegme@gmail.com"; 
	  String password = "namgioung1234!";
	  memberVo.setEmail(email);
	  memberVo.setPassword(password);
	  
	  ResultActions resultActions =
	  mockMvc.perform(post(SHAREDURL+"/login")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(new Gson().toJson(memberVo))
	  .characterEncoding("utf-8"));
	  
	  resultActions.andDo(print())
	  .andExpect(status().isOk()) 
	  .andExpect(jsonPath("$.result",is("fail"))); 
	  }
```



#### case3. 아이디 또는 패스워드 형식이 올바르지 않을경우

**Request**

HTTP Method = POST<br>
      Request URI = /api/user/login<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"email":"namgioung","password":"123456789"}



**Response**    

Status = 400<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"fail","message":"아이디 또는 비밀번호가 올바르지 않은 형식입니다."}



■  테스트코드

```java
@Test 
	  public void TestB_3() throws Exception { 
	  MemberVo memberVo = new MemberVo();
	  String email = "namgioung"; 
	  String password = "123456789"; //비밀번호는 영어 + 숫자 + 문자 조합
	  memberVo.setEmail(email);
	  memberVo.setPassword(password);
	  
	  ResultActions resultActions =
	  mockMvc.perform(post(SHAREDURL+"/login")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(new Gson().toJson(memberVo))
	  .characterEncoding("utf-8"));
	  
	  resultActions.andDo(print())
	  .andExpect(status().isBadRequest()) 
	  .andExpect(jsonPath("$.result",is("fail"))); 
	  }
```



- <b>개발시 발생한 issue</b>
  	1. 로그인시 Controller 단에서 @Valid를 이용하면 MemberVo 객체의 모든 property의 유효성을 검사하게 된다. 따라서 @Valid를 없애고 명시적으로 지정한 property만 검사하도록 코드를 작성하였다. 

