## 장바구니 담기 

 **요구 사항**

 1. 회원일 경우 회원 ID를  이용해 장바구니를 식별한다.

 2. 비회원일 경우 임시 ID를 이용해 장바구니를 식별한다.

 3. 비회원일 경우 백엔드 단에서 임시 ID를 생성하여 프론트엔드의 쿠키값으로 넘겨준다.

 4. 장바구니에 이미 등록된 상품을 또 담을시 해당 수량만큼 수량을 추가한다.

 5. 장바구니의 순서번호는 ID별로 독립적이여야 한다. 

    ex) 회원 A 가 장바구니 순서번호 1,2,3 회원 B도 장바구니 순서번호 1,2,3 이런식으로 가질수있다.



<b>개발시 발생한 issue</b>

1. **장바구니에 어떤 사용자가 담을 때 그것을 식별하기 위한 컬럼을 무엇으로 두어야될까?**

   => 맨 처음에는 회원은 회원 ID, 비회원은 임시 ID 컬럼 각각을 두어 관리하기로 함

2. **회원ID와 비회원ID가 겹치게 된다면 어떻게 할것인가?**

   =>회원ID와 비회원 임시ID 컬럼을 하나의 컬럼 ID로 합치고 회원유무 컬럼을 추가해서 해결

3. **임시 ID를 어떻게 생성할 것인가?**

   => 비회원이 장바구니 등록API를 요청할 경우 생성하기로 결정.

   => Service단 코드에서 TempId의 유무를 검사한 후 없을경우 랜덤함수를 이용해 영숫자 20자 조합으로 생성하기로 함

4. **하나의 회원의 장바구니 순서번호는 어떻게 해결할 것인가?**

   => 장바구니 max(seq_no)를 이용하여 그 값을 받아와서 해결

■ 실제동작코드 

- Controller

```java
 @ApiOperation(value = "장바구니 담기")
	  @PostMapping(value="/list")
	  public ResponseEntity<JSONResult> addProductToCart(@Valid @RequestBody CartVo cartVo) {
		  
		  if(cartService.addProductToCart(cartVo))
				return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(cartVo));
		  else
			  	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("장바구니 담기 실패"));
		
	}

```

- Service

```java
//장바구니 담기
	public boolean addProductToCart(CartVo cartVo) {
		System.out.println("cartVoId = " + cartVo.getId());
		if(null == cartVo.getId()) {
			TempId = createTempId();
			System.out.println("TempId = " + TempId);
			cartVo.setId(TempId);
		}
	
	// 이미 담은 상품인지 확인
	// 이미 담겨있다면 수량만 증가 시켜준다.
	CartVo existCartVo = cartDao.getProductInCart(cartVo);
	if(null != existCartVo) {
		cartVo.setQty(existCartVo.getQty()+cartVo.getQty());
		cartVo.setSeq_no(existCartVo.getSeq_no());
		return cartDao.addProductQty(cartVo);
	}
		
	//seq_no 정하기
	Long seq_no = cartDao.getMaxSeqNo(cartVo.getId());
	if(null==seq_no) {
		cartVo.setSeq_no(1L);
	}
	else
		cartVo.setSeq_no(seq_no+1);
	
	//장바구니 insert
	
	return cartDao.addProductToCart(cartVo);
		
	}

//TempId 생성
private String createTempId() {
		StringBuffer strbuff = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < 20; i++) {
		    int rIndex = random.nextInt(3);
		    switch (rIndex) {
		    case 0:
		    	//소문자 a-z
		        strbuff.append((char) ((int) (random.nextInt(26)) + 97));
		        break;
		    case 1:
		        //대문자 A-Z
		    	strbuff.append((char) ((int) (random.nextInt(26)) + 65));
		        break;
		    case 2:
		        //숫자 0-9
		    	strbuff.append((random.nextInt(10)));
		        break;
		    }
		}
		
		return strbuff.toString();
	}
```

- Repository

```java
//장바구니 담기
public boolean addProductToCart(CartVo cartVo) {
		
		return 1 == sqlSession.insert("cart.addProductToCart", cartVo);
	}
//가장 큰 순서번호는?
public Long getMaxSeqNo(String id) {
	
   	 	return sqlSession.selectOne("cart.getMaxSeqNo", id);
	}

// 상품이 담겨져 있는가?
public CartVo getProductInCart(CartVo cartVo) {
		return sqlSession.selectOne("cart.getProductInCart", cartVo);
	}	

//수량 추가
public boolean addProductQty(CartVo cartVo) {
		return 1 == sqlSession.update("cart.addProductQty", cartVo);
	}

```



### 테스트케이스

#### case1. 비회원 장바구니 담기 성공

**Request**

 HTTP Method = POST<br>
      Request URI = /api/cart/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"seq_no":0,"qty":3,"pd_detail_no":1,"product_no":1,"ismember":false}

**Response**

 Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"id":"**31sVpmXCq6D0OW94J87u**",<br>"seq_no":1,"qty":3,"pd_detail_no":1,"product_no":1,"ismember":false}}



■  테스트코드

```java
// # 장바구니 담기 회원일 경우
	@Test
	public void TestA4() throws Exception {

		System.out.println("회원 장바구니 담기 ");
		String id = "ska2253@naver.com";
		CartVo cartVo = new CartVo(3L, 1L, 1L, true);
		cartVo.setId(id);

		ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

	}
```



#### case2. 회원 장바구니 담기 성공

**Request**

 HTTP Method = POST<br>
      Request URI = /api/cart/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"id":"ska2253@naver.com",<br>"seq_no":0,"qty":3,"pd_detail_no":1,"product_no":1,"ismember":true}

**Response**Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"id":"ska2253@naver.com"<br>,"seq_no":1,"qty":3,"pd_detail_no":1,"product_no":1,"ismember":true}}

■  테스트코드

```java
// # 장바구니 담기 회원일 경우
	@Test
	public void TestA4() throws Exception {

		System.out.println("회원 장바구니 담기 ");
		String id = "ska2253@naver.com";
		CartVo cartVo = new CartVo(3L, 1L, 1L, true);
		cartVo.setId(id);

		ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

	}
```





#### case3. 장바구니에 같은 상품을 담을 경우

수량만 증가한다. 기존의 3개에서 2개 추가

**Request** 

HTTP Method = POST<br>
      Request URI = /api/cart/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"id":"ska2253@naver.com",<br>"seq_no":0,**"qty":2**,"pd_detail_no":1,"product_no":1,"ismember":true}



**Response**

Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"id":"ska2253@naver.com",<br>"seq_no":1,**"qty":5**,"pd_detail_no":1,"product_no":1,"ismember":true}}

■  테스트코드

```java
// # 장바구니에 같은 상품을 담을 경우
	@Test
	public void TestA5() throws Exception {

		System.out.println("장바구니에 같은 상품을 담을 경우 ");
		String id = "ska2253@naver.com";
		CartVo cartVo = new CartVo(2L, 1L, 1L, true); //앞의 TEST에서 3개의 수량이 있었고 2개가 추가되므로 총 5개여야 한다. 
		cartVo.setId(id);

		ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

	}
```



- <b>개발시 발생한 issue</b>

  1. **장바구니에 어떤 사용자가 담을 때 그것을 식별하기 위한 컬럼을 무엇으로 두어야될까?**

     => 맨 처음에는 회원은 회원 ID, 비회원은 임시 ID 컬럼 각각을 두어 관리하기로 함

  2. **회원ID와 비회원ID가 겹치게 된다면 어떻게 할것인가?**

     =>회원ID와 비회원 임시ID 컬럼을 하나의 컬럼 ID로 합치고 회원유무 컬럼을 추가해서 해결

  3. **임시 ID를 어떻게 생성할 것인가?**

     => 비회원이 장바구니 등록API를 요청할 경우 생성하기로 결정.

     => Service단 코드에서 TempId의 유무를 검사한 후 없을경우 랜덤함수를 이용해 영숫자 20자 조합으로 생성하기로 함

  4. **하나의 회원의 장바구니 순서번호는 어떻게 해결할 것인가?**

     => 장바구니 max(seq_no)를 이용하여 그 값을 받아와서 해결