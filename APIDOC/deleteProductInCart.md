## 장바구니 상품 삭제

 **요구 사항**

​	1. 장바구니에서 개별적으로 상품을 삭제 할 수 있어야 한다.



■ 실제동작코드 

- Controller

```java
@ApiOperation(value = "장바구니 상품 삭제")
	  @DeleteMapping(value="/list/{seq_no}")
	  public ResponseEntity<JSONResult> deleteProductInCart(@PathVariable("seq_no") long seq_no,
				@RequestBody CartVo cartVo) {
		 cartVo.setSeq_no(seq_no);
		 
		  if(cartService.deleteProductInCart(cartVo))
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 삭제 실패"));
		
}	
```

- Service

```java
// 장바구니 상품 삭제
public boolean deleteProductInCart(CartVo cartVo) {
		
	return cartDao.deleteProductInCart(cartVo);
}
```

- Repository

```java
//	장바구니 상품 삭제
	public boolean deleteProductInCart(CartVo cartVo) {
		
		return 1 == sqlSession.delete("cart.deleteProductInCart", cartVo);
	}
```



### 테스트케이스

#### case1. 성공 케이스

**Request**

HTTP Method = DELETE<br>
      Request URI = /api/cart/list/**1**<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"id":"ska2253@naver.com","seq_no":0,"qty":0,"pd_detail_no":0,"product_no":0,"ismember":true}

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





