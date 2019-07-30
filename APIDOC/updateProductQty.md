## 장바구니 수량 수정

 **요구 사항**

	1. 회원의 장바구니에서 특정 상품의 수량을 수정 할 수 있어야한다.



■ 실제동작코드 

- Controller

```java
@ApiOperation(value = "장바구니 수량 수정")
	@PutMapping(value = "/list/{seq_no}")
	public ResponseEntity<JSONResult> updateProductQty(@PathVariable("seq_no") long seq_no,
			@RequestBody CartVo cartVo) {
		
		cartVo.setSeq_no(seq_no);
		if (cartService.updateProductQty(cartVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 아이디 수정 실패"));

	}
```

- Service

```java
//장바구니 상품 수량 수정
	public boolean updateProductQty(CartVo cartVo) {

		return cartDao.updateProductQty(cartVo);
	}
```

- Repository

```java
//상품 수량 수정
	public boolean updateProductQty(CartVo cartVo) {
		return 1 == sqlSession.update("cart.updateProductQty", cartVo);
	}
```



### 테스트케이스

#### case1. 성공케이스

**Request**

 HTTP Method = PUT<br>
      Request URI = /api/cart/list/1<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"id":"ska2253@naver.com","seq_no":0,**"qty":10,**"pd_detail_no":0,"product_no":0,"ismember":true}



**Response**

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}

■  테스트코드

```java
//# 장바구니 수량 수정
	@Test
	public void TestB2() throws Exception {
		System.out.println("장바구니 수량 수정");
		String id = "ska2253@naver.com";
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		cartVo.setQty(10L); //수량을 10개로 변경
	
		ResultActions resultActions = mockMvc.perform(put(CARTURL + "/list/{seq_no}",1L).contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success"))); 
	}
```





