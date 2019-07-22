## 회원정보 수정

회원정보 수정 요구 사항

	1. 변경될 정보는 유효성 검사를 통과해야 한다.



■ 실제동작코드 

```java
//회원정보수정
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
```



### 테스트케이스

#### case1. 유효성 검사 실패

**Request**

HTTP Method = PUT<br>
      Request URI = /api/user/modification<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"email":"gioung9833@gmail.com","name":"남기웅","password":"**1234**","birth":"1993-12-22","gender":"M","phone_num":"**123456**"}



**Response**

Status = 400<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"fail","message":"데이터 유효성에 어긋납니다."}

■  테스트코드

```java
	  // case1. 유효성 검사 실패
	  @Test 
	  public void TestC_1() throws Exception {
		  System.out.println("회원정보 수정 테스트");
		  MemberVo vo = new MemberVo(); 
		  vo.setEmail("gioung9833@gmail.com");
		  vo.setName("남기웅");
		  vo.setPassword("gioung1234!"); //비밀번호 4자리로 변경시도
		  vo.setBirth("1993-12-22"); 
		  vo.setGender(Gender.M);
		  vo.setPhone_num("123456"); //번호 형식 무시 
		  
		  ResultActions resultActions = mockMvc.perform(put(SHAREDURL+"/modification")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content(new Gson().toJson(vo))
		  .characterEncoding("utf-8"));	  
		  
		  //응답은 200, success, 메시지가 나와야됨.
		  resultActions.andExpect(status().isBadRequest())
		  .andExpect(jsonPath("$.result",is("fail")))
		  .andDo(print()); }
```



#### case2. 성공케이스

**Request**

HTTP Method = PUT<br>
      Request URI = /api/user/modification<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"email":"gioung9833@gmail.com","name":"남기웅","password":"gioung1234!","birth":"1993-12-22","gender":"M","phone_num":"010-9958-9833"}

**Response**       

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"email":"gioung9833@gmail.com","name":"남기웅","password":"gioung1234!","birth":"1993-12-22","gender":"M","phone_num":"010-9958-9833"}}



■  테스트코드

```java
      // case2. 성공케이스
	  @Test 
	  public void TestC_2() throws Exception {
		  MemberVo vo = new MemberVo(); 
		  vo.setEmail("gioung9833@gmail.com");
		  vo.setName("남기웅");
		  vo.setPassword("gioung1234!"); // ######### 변경부분 ##########
		  vo.setBirth("1993-12-22");
		  vo.setGender(Gender.M);
		  vo.setPhone_num("010-9958-9833");
		  
		  ResultActions resultActions = mockMvc.perform(put(SHAREDURL+"/modification")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content(new Gson().toJson(vo))
		  .characterEncoding("utf-8"));	  
		  
		  //응답은 200, success, 메시지가 나와야됨.
		  resultActions.andExpect(status().isOk())
		  .andExpect(jsonPath("$.result",is("success")))
		  .andDo(print()); 
	}
```



#### case3. 수정후 로그인 테스트

**Request**

 HTTP Method = POST<br>
      Request URI = /api/user/login<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"email":"gioung9833@gmail.com","password":"gioung1234!"}

**Response**      

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"email":"gioung9833@gmail.com","password":"gioung1234!"}}



■  테스트코드

```java
 // case3. 정보수정후 로그인 테스트
	  @Test 
	  public void TestC_3() throws Exception {
		  System.out.println("수정 후 로그인 테스트"); 
		  MemberVo membervo = new MemberVo();
		  membervo.setEmail("gioung9833@gmail.com");
		  membervo.setPassword("gioung1234!");
		  
		  ResultActions resultActions =
		  			 	mockMvc.perform(post(SHAREDURL+"/login").contentType(MediaType.APPLICATION_JSON) 
		  .content(new Gson().toJson(membervo))
		  .characterEncoding("utf-8"));
		  resultActions.andExpect(status().isOk())
		  .andExpect(jsonPath("$.result",is("success")))
		  .andDo(print()); 
	  }
```



- <b>개발시 발생한 issue</b>

  1.  수정이 되었는지를 확인을 어떻게 할 것인가? 

     => 수정 후 로그인 테스트(case3 )를 추가하여 이를 해결.

