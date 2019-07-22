## 회원탈퇴

 요구 사항

​	1. 입력된 아이디와 비밀번호와 일치하는 회원을 삭제한다.



■ 실제동작코드 

```java
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
```



### 테스트케이스

#### case1. 해당 아이디의 비밀번호가 일치하지 않을경우

**Request**

 HTTP Method = DELETE<br>
      Request URI = /api/user/out<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"email":"gioung9833@gmail.com","password":"**111111**"}

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"fail","message":"**회원삭제 실패**"}

■  테스트코드

```java
 // case1. 해당 아이디의 비밀번호가 일치하지 않을경우
	  @Test 
	  public void TestD_1() throws Exception { 
		  System.out.println("회원 탈퇴 테스트");
		  MemberVo memberVo = new MemberVo();
		  String email = "gioung9833@gmail.com";
		  String password= "111111";
		  memberVo.setEmail(email);
		  memberVo.setPassword(password);
		  
		  ResultActions resultActions =
		  mockMvc.perform(delete(SHAREDURL+"/out")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content(new Gson().toJson(memberVo))
		  .characterEncoding("utf-8"));
		  
		  resultActions.andExpect(status().isOk())
		  //fail인지 확인
		  .andExpect(jsonPath("$.result",is("fail"))) 
		  .andDo(print()); 
	  }	  
```



#### case2. 성공테스트

**Request**

 HTTP Method = DELETE<br>
      Request URI = /api/user/out<br>
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
 @Test 
	  public void TestD_2() throws Exception { 
		  MemberVo memberVo = new MemberVo();
		  String email = "gioung9833@gmail.com";
		  String password= "gioung1234!";
		  memberVo.setEmail(email);
		  memberVo.setPassword(password);
		  
		  ResultActions resultActions =
		  mockMvc.perform(delete(SHAREDURL+"/out")
		  .contentType(MediaType.APPLICATION_JSON)
		  .content(new Gson().toJson(memberVo))
		  .characterEncoding("utf-8"));
		  
		  resultActions.andExpect(status().isOk())
		  //success인지 확인
		  .andExpect(jsonPath("$.result",is("success"))) 
		  .andDo(print()); 
	  }	  
```





#### case3. 탈퇴 후 이메일 체크 테스트

**Request**

HTTP Method = GET<br>
      Request URI = /api/user/checkemail<br>
       Parameters = {email=[gioung9833@gmail.com]}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]

**Response**          

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8



■  테스트코드

```java
 // case3. 탈퇴 후 이메일 체크 테스트
	  @Test 
	  public void TestD_3() throws Exception {
	  System.out.println("탈퇴 후 이메일 체크 테스트"); 
	  //탈퇴한 아이디
	  String email = "gioung9833@gmail.com";
	  ResultActions resultActions =
	  mockMvc.perform(get(SHAREDURL+"/checkemail")
	  .param("email", email)
	  .contentType(MediaType.APPLICATION_JSON)
	  .characterEncoding("utf-8"));
	  
      //ok인지 확인
	  resultActions.andExpect(status().isOk())
	  .andDo(print());
	  }
```



