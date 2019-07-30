## 회원가입시 장바구니 수정

 **요구 사항**

 1. 비회원이  회원가입시 비회원의 장바구니의 id값을 TempId에서 회원 Id로 변환시킨다.

    

<b>개발시 발생한 issue</b>

 1. 장바구니 테이블 컬럼에는 tempId 컬럼이 존재하지 않는다. mybatis를 이용하여 쿼리문을 작성할 때 변경하기 위한 회원id값과 tempId값 두개가 필요한데 이를 어떻게 처리 할 것인가?

    => CartVo 클래스에 tempId 프로퍼티를 추가하고 xml파일에서 ${}문법을 이용하여 값을 매핑시킨다.

    

■ 실제동작코드 

- Controller

```java
@ApiOperation(value = "회원가입시 기존 장바구니 id 변환")
	@PutMapping(value = "/list")
	public ResponseEntity<JSONResult> convertTempIdToUser(@RequestBody CartVo cartVo) {

		
		if (cartService.convertTempIdToUser(cartVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 아이디 수정 실패"));

	}
```

- Service

```java
//회원가입시 TempId가 회원 Id로 변환
	public boolean convertTempIdToUser(CartVo cartVo) {
		return cartDao.convertTempIdToUser(cartVo);
	}
```

- Repository

```java
//회원가입시 tempId -> 회원 Id
	public boolean convertTempIdToUser(CartVo cartVo) {

		return 0 < sqlSession.update("cart.convertTempIdToUser", cartVo);
	}
```



### 테스트케이스

#### case1. 성공케이스

ska2253@naver.com 으로 회원가입 , 기존에 하나의 상품이 장바구니에 담겨있음.

**Request**

 HTTP Method = PUT<br>
      Request URI = /api/cart/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {**"id":"ska2253@naver.com"**,<br>"seq_no":0,"qty":0,"pd_detail_no":0,"product_no":0,"ismember":true,<br>**"tempId":"uANwnK570DCssaaLlQO3"**}

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}<br>

■  테스트코드

```java
@Test
	public void TestA3_1() throws Exception{
		System.out.println("비회원 회원으로 전환시 장바구니 id 변환 테스트");
		String id ="ska2253@naver.com"; // 해당 id로 회원가입
		CartVo cartVo = new CartVo();
		cartVo.setId(id); 
		cartVo.setIsmember(true);
		cartVo.setTempId(cartService.getTempId());
		
		ResultActions resultActions = mockMvc.perform(put(CARTURL + "/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo))
				.characterEncoding("utf-8"));
		
		resultActions.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.result", is("success")));
	}
```



