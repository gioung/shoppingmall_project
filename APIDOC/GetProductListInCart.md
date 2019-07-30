## 장바구니 목록

 **요구 사항**

	1. 어떤 ID를 가진 사용자(회원 또는 비회원)의 장바구니를 조회할 수 있어야 한다.



■ 실제동작코드 

- Controller

```java
@ApiOperation(value = "장바구니 조회")
	  @GetMapping(value="/list")
	  public ResponseEntity<JSONResult> getProductListInCart(@RequestBody String id) {
		  
		  List<CartVo> cartList = cartService.getProductListInCart(id);
		  if(cartList != null)
				return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(cartList));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 담기 실패"));
		
	}
```

- Service

```java
// 카트리스트 조회
		public List<CartVo> getProductListInCart(String id) {
			return cartDao.getProductListInCart(id);
		}
```

- Repository

```java
//카트 리스트 조회
public List<CartVo> getProductListInCart(String id) {
		
		return sqlSession.selectList("cart.getProductListInCart", id);
	}
```



### 테스트케이스

#### case1. 성공케이스

**Request**

HTTP Method = GET<br>
      Request URI = /api/cart/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = "ska2253@naver.com"

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":[{"id":"ska2253@naver.com"<br>,"seq_no":1,"qty":8,"reg_date":"7월 30, 2019","pd_detail_no":1,"product_no":1,"ismember":true}]}

■  테스트코드

```java
//# 장바구니 조회
	@Test
	public void TestB1() throws Exception {
		System.out.println("장바구니 조회");
		String id = "ska2253@naver.com";
		
		ResultActions resultActions = mockMvc.perform(get(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(id)).characterEncoding("utf-8"));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")));
	}
```





