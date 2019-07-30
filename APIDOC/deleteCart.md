## 장바구니 삭제

 **요구 사항**

	1. 회원이 탈퇴할 때 해당 회원의 장바구니가 먼저 삭제되어야 한다.



■ 실제동작코드 

- Controller

```java
//테스트를 위한 임의의 API 
//실제로는 회원탈퇴시 이루어져야 한다.
@ApiOperation(value = "장바구니 삭제") 
	  @DeleteMapping(value="/list")
	  public ResponseEntity<JSONResult> deleteCart(@RequestBody String Id) {
		  
		  if(cartService.deleteCart(Id))
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 삭제 실패"));
		
	} 
```

- Service

```java
//장바구니 삭제
	public boolean deleteCart(String id) {
		return cartDao.deleteCart(id);
		
	}
```

- Repository

```java
public boolean deleteCart(String id) {
		
		return 0 < sqlSession.delete("cart.deleteCart", id);
	}
```



### 테스트케이스

ID : ska2253@naver.com이 회원 탈퇴한다는 상황을 가정

#### case1. 성공 케이스

**Request**

 HTTP Method = DELETE<br>
      Request URI = /api/cart/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = "ska2253@naver.com"



**Response**

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}

■  테스트코드

```java
//# 장바구니 상품 삭제
	@Test
	public void TestC1() throws Exception{
		//회원이 ska2253@naver.com 이고 seq_no = 1인 상품을 삭제
		System.out.println("장바구니 상품 삭제");
		String id="ska2253@naver.com";
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		cartVo.setIsmember(true);
		
		ResultActions resultActions = mockMvc.perform(delete(CARTURL+"/list/{seq_no}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo))
				.characterEncoding("utf-8"));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")));
	}
```

