## 주문 하기

 **요구 사항**

	1. 주문자가 회원일경우 추가 정보를 회원 정보에 추가한다.
 	2. 주문번호를 조회할수 있도록 주문자를 식별할수 있는 정보를 필요로 한다.
 	3. 주문자와 받는자가 일치할 경우, 그렇지 않을 경우 두가지를 모두 고려해야 한다.
 	4. 주문은 장바구니에서 여러개의 상품들을 한꺼번에 주문 할 수 있어야 된다.
 	5. 상품페이지에서도 하나의 상품을 주문 할 수 있다.
	6. 주문시 장바구니의 물품과 상품의 재고 수량을 변경하여야 한다.

<b>개발시 발생한 issue</b>

 1. 비회원이 주문한 후 다시 조회를 하기위해서 식별할 수 있는 식별자가 필요로 한데 그것을 무엇으로 할까?

    ​	=> 처음엔 주문번호로 하기로 했지만 url로 접근 할 우려가 있어서 TempId값으로 식별하기로 함. TempId값은 백엔드에서 임의로 생성하여 사용자에게 알리는 식.

2.  상품페이지에서는 상품 하나만 주문하지만 장바구니에서는 여러개를 동시에 주문 할 수 있다. DB접근을 최소화하기 위해 한번에 Insert하는 방법을 어떻게 구현할 것인가?

     => mybatis에서 for-each구문을 이용해서 List를 받아서 한번에 insert하는 방법을 구현.

3.  주문시 주문자정보와 받는자정보가 일치할때 이를 어떻게 표시할 것인가? 

     => 주문자와 받는자가 일치한지를 판별하는 컬럼(boolean)을 추가해서 해결

4.  주문시 재고 관리를 어떻게 할 것인가?

     =>장바구니 담기는 재고에 영향을 주지 않음, 주문이 insert 된 후 장바구니를 삭제하고 재고를 감량 



■ 실제동작코드 

- Controller

```java
// 주문 하기
	@ApiOperation(value = "주문 하기")
	@PostMapping(value = "/list")
	public ResponseEntity<JSONResult> doOrder(@Valid @RequestBody OrderVo orderVo, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
		}
		
		if(orderService.doOrder(orderVo)) {
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(orderVo));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("주문 실패"));
		}
	}
```

- Service

```java
//주문하기
	public boolean doOrder(OrderVo orderVo) {
		//주문한 자와 받는 자가 일치하는지 확인
		// 일치하면 recv_name, tel을 set하기
		if(orderVo.isIs_same()) {
			orderVo.setRecv_name(orderVo.getOrd_name());
			orderVo.setRecv_tel(orderVo.getOrd_tel());
		}
		
		//회원유무 및 주소 비어있는지 검사
		if(orderVo.isIsmember()) {
			if(userDao.existMemberAddress(orderVo.getId())) {
			//member table update
			MemberVo memberVo = new MemberVo();
			memberVo.setAddress(orderVo.getDestination());
			memberVo.setEmail(orderVo.getId());
			userDao.addMemberAddress(memberVo);
			}
		}
		else {
			//앞선 테스트에서 임시아이디가 만들어져 있으므로 그대로 쓰면된다.<테스트용>
			orderVo.setId(cartService.getTempId());
		}
		//장바구니 유무 검사
		if(orderVo.isIscart()) {
			//add OrderedProductList from cartList
			System.out.println(orderVo.getId());
			List<CartVo> cartList = cartDao.getProductListInCart(orderVo.getId());
			System.out.println("CartList = "+cartList);
			if(null == cartList)
				return false;
			//orderList 생성
			List<OrderedProductVo> orderList= new ArrayList<>(cartList.size());
			orderVo.setOrderList(orderList);
			
			for(int i=0; i<cartList.size(); i++) {
				CartVo cartVo = cartList.get(i);
				//가격은 프론트 단에서 계산 할 것이므로 임의로 넣음.
				orderList.add(new OrderedProductVo(orderVo.getOrder_no(), cartVo.getProduct_no(), cartVo.getPd_detail_no()
						, cartVo.getQty(), 10000L));
			}
			
			//delete cart
			cartDao.deleteCart(orderVo.getId());
			
		}
		
		//재고 확인 및 재고 차감
		if(isEnoughQty(orderVo.getOrderList())) {
			//order table insert
			orderDao.doOrder(orderVo);
			//orderList table insert
			return orderDao.addOrderList(orderVo.getOrderList());
			}
		
		return false;
	}

//주문 수량이 재고 수량보다 작거나 같은지
	private boolean isEnoughQty(List<OrderedProductVo> orderList) {
		int size = orderList.size();
		List<ProductDetailVo> pdvList = new ArrayList<>(size);
		for(int i=0; i<size; i++) {
			ProductDetailVo pdv = new ProductDetailVo();
			pdv.setProduct_no(orderList.get(i).getProduct_no());
			pdv.setPd_detail_no(orderList.get(i).getPd_detail_no());
			pdvList.add(pdv);
		}
		//수정하기
		List<Long> qtyList = shopDao.getQtyByOrderList(pdvList);
		
		for(int i=0; i<size; i++) {
			if(qtyList.get(i)<orderList.get(i).getQty())
				return false;
		}
		
		//작다면 재고 차감
		for(int i=0; i<size; i++) {
			long result = qtyList.get(i)-orderList.get(i).getQty();
			pdvList.get(i).setInventory(result);
		}
		return true;
	}

//재고 리스트 조회
	public List<Long> getQtyByOrderList(List<ProductDetailVo> list) {
		System.out.println("size = " + list.size());
		System.out.println("productdetailvoList = "+list);
		List<Long> qtyList = new ArrayList<>(list.size());
		for(int i=0; i<list.size(); i++) {
			Long qty = sqlSession.selectOne("product.getQtyByOrderList", list.get(i));
			System.out.println("Qty = "+qty);
			qtyList.add(qty);
		}
		System.out.println("qtyList = "+qtyList);
		return qtyList;
	}

//임시 아이디 생성 코드
private static String createTempId() {
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
//멤버 주소가 존재하지 않는가?
	public boolean existMemberAddress(String id) {
		String address = sqlSession.selectOne("member.existMemberAddress", id);
		System.out.println("address = "+address);
		return null==address;
	}

//카트리스트 조회
	public List<CartVo> getProductListInCart(String id) {
			
		return sqlSession.selectList("cart.getProductListInCart", id);
}

//장바구니 삭제
	public boolean deleteCart(String id) {
		
		return 0 < sqlSession.delete("cart.deleteCart", id);
	}

//주문 하기
	public boolean doOrder(@Valid OrderVo orderVo) {
		return 1 == sqlSession.insert("order.doOrder", orderVo);
		
	}
```



### 테스트케이스

#### case1. 비회원 주문 + 상품페이지에서 주문

비회원 TempId 생성은 진한색으로 표시.

**Request**

HTTP Method = POST<br>
      Request URI = /api/order/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]v
             Body = {"order_no":1,"destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678"<br>,"is_same":true,"pay":0,"ismember":false,"iscart":false,"orderList":[{"product_no":1,"pd_detail_no":1,"order_no":1,"qty":4,"pay":168000}]}

**Response**

Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"order_no":1,"recv_name":"남기웅","recv_tel":"010-1234-5678","destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678"<br>,"is_same":true,"pay":0,"ismember":false,"iscart":false,"id":"**31S5desxU2703QkFtPs6**","orderList":[{"product_no":1,"pd_detail_no":1,"order_no":1,"qty":4,"pay":168000}]}}

■  테스트코드

```java
// # 주문하기(비회원) + 상품페이지에서 주문 
	@Test
	public void TestA04() throws Exception{
		System.out.println("비회원 주문하기");
		long order_no = 1L;
		List<OrderedProductVo> orderList = new ArrayList<>();
		//상품 주문
		orderList.add(new OrderedProductVo(order_no, 1L, 1L, 4L,42000L*4L));
		//주문자와 받는자가 같으므로 두번째 , 세번째는 null값
		OrderVo orderVo = new OrderVo(order_no, null, null, "서울", "남기웅", 
				"010-1234-5678", true, false, null);
		orderVo.setIsmember(false);
		orderVo.setOrderList(orderList);
		
		ResultActions resultActions = mockMvc.perform(post(ORDERURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo))
				.characterEncoding("utf-8"));
				
				resultActions
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.result", is("success")));	
	}
```



#### case2. 회원 주문 + 회원 추가정보 insert

**Request**

 HTTP Method = POST<br>
      Request URI = /api/order/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"order_no":2,"destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":true,"pay":0,"ismember":true,"iscart":false,"id":"ska2253@naver.com","orderList":[{"product_no":1,"pd_detail_no":1,"order_no":2,"qty":4,"pay":168000}]}

**Response**

Status = 201
    Error message = null
          Headers = [Content-Type:"application/json;charset=UTF-8"]
     Content type = application/json;charset=UTF-8
             Body = {"result":"success","data":{"order_no":2,"recv_name":"남기웅","recv_tel":"010-1234-5678",<br>"destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":true,"pay":0,"ismember":true,"iscart":false,"id":"ska2253@naver.com","orderList":<br>[{"product_no":1,"pd_detail_no":1,"order_no":2,"qty":4,"pay":168000}]}}



■  테스트코드

```java
 // # 주문하기(회원) + 회원 추가정보 insert
	@Test
	public void TestA05() throws Exception{
		System.out.println("회원 주문하기");
		long order_no = 2L;
		String id = "ska2253@naver.com";
		List<OrderedProductVo> orderList = new ArrayList<>();
		//상품 주문
		orderList.add(new OrderedProductVo(order_no, 1L, 1L, 4L,42000L*4L));
		//주문자와 받는자가 같으므로 두번째 , 세번째는 null값
		OrderVo orderVo = new OrderVo(order_no, null, null, "서울", "남기웅", 
				"010-1234-5678", true, false, id);
		//회원임을 표시
		orderVo.setIsmember(true);
		orderVo.setOrderList(orderList);
		
		ResultActions resultActions = mockMvc.perform(post(ORDERURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo))
				.characterEncoding("utf-8"));
		
		resultActions
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.result", is("success")));
	}
```





#### case3. 주문자와 받는자가 다를 경우

**Request**

HTTP Method = POST<br>
      Request URI = /api/order/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"order_no":3,"recv_name":"문상수","recv_tel":"111-1111-1111","destination":"서울",<br>"ord_name":"남기웅","ord_tel":"010-1234-5678",**"is_same":false**,"pay":0,"ismember":true,"iscart":false,"id":"ska2253@naver.com",<br>"orderList":[{"product_no":1,"pd_detail_no":1,"order_no":3,"qty":4,"pay":168000}]}

**Response**

Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"order_no":3,"recv_name":"남기웅","recv_tel":"010-1234-5678",<br>"destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678",<br>**"is_same":false**,"pay":0,"ismember":true,"iscart":false,"id":"ska2253@naver.com",<br>"orderList":[{"product_no":1,"pd_detail_no":1,"order_no":3,"qty":4,"pay":168000}]}}

■  테스트코드

```java
// # 주문자와 받는자가 다를 경우
	@Test
	public void TestA06() throws Exception {
		System.out.println("주문자와 받는자가 다를 경우");
		long order_no = 3L;
		//회원 id
		
		String id = "ska2253@naver.com";
		List<OrderedProductVo> orderList = new ArrayList<>();
		// 상품 주문
		orderList.add(new OrderedProductVo(order_no, 1L, 1L, 4L, 42000L * 4L));
		// 주문자와 받는자가 다르다. 두번재는 받는자의 이름, 세번째는 받는자의 전화번호
		OrderVo orderVo = new OrderVo(order_no, "문상수", "111-1111-1111", "서울", "남기웅", "010-1234-5678", true, false, id);
		// 회원임을 표시
		orderVo.setIsmember(true);
		orderVo.setOrderList(orderList);

		ResultActions resultActions = mockMvc.perform(post(ORDERURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));
	}
```





#### case4. 장바구니로 주문 할 경우

**Request**

HTTP Method = POST<br>
      Request URI = /api/order/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"order_no":5,"recv_name":"문상수","recv_tel":"111-1111-1111","destination":"서울",<br>"ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":true,"pay":0,"ismember":true,"iscart":true,"id":"ska2253@naver.com"}



**Response**

 Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"order_no":5,"recv_name":"남기웅","recv_tel":"010-1234-5678",<br>"destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":true,"pay":0,"ismember":true,"iscart":true,"id":"ska2253@naver.com","orderList":<br>[**{"product_no":1,"pd_detail_no":3,"order_no":5,"qty":3,"pay":10000},<br>{"product_no":1,"pd_detail_no":4,"order_no":5,"qty":3,"pay":10000}**]}}

■  테스트코드

```java
// # 장바구니로 주문할 경우 장바구니 삭제
	@Test
	public void TestA08() throws Exception {
		System.out.println("장바구니로 주문시 장바구니 삭제");
		long order_no = 5L;
		String id = "ska2253@naver.com";
		OrderVo orderVo = new OrderVo(order_no, "문상수", "111-1111-1111", "서울", "남기웅", "010-1234-5678", true, id);
		// 회원임을 표시
		orderVo.setIsmember(true);
		// 장바구니 사용 표시
		orderVo.setIscart(true);

		ResultActions resultActions = mockMvc.perform(post(ORDERURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));
	}
```



#### case5. 재고보다 주문 수량이 많을 경우

- 수량 900개 (재고보다 넘치게) 입력해서 테스트

**Request**

HTTP Method = POST<br>
      Request URI = /api/order/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"order_no":6,"destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":true,"pay":0,"ismember":true,"iscart":false,"id":"ska2253@naver.com",<br>"orderList":[{"product_no":1,"pd_detail_no":2,"order_no":6,"**qty":900,**"pay":168000}]}

**Response**

Status = 400<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"fail","message":"**주문 실패**"}



■  테스트코드

```java
// # 재고보다 주문 수량이 많을 경우
	@Test
	public void TestA09() throws Exception {
		System.out.println("재고보다 주문 수량이 많을 경우");
		long order_no = 6L;
		String id = "ska2253@naver.com";
		List<OrderedProductVo> orderList = new ArrayList<>();
		//상품 주문시 상품번호가 1이고 상품 디테일번호가 2인 상품을 900개 (재고 초과)주문 한다고 가정
		orderList.add(new OrderedProductVo(order_no, 1L, 2L, 900L,42000L*4L));
		//주문자와 받는자가 같으므로 두번째 , 세번째는 null값
		OrderVo orderVo = new OrderVo(order_no, null, null, "서울", "남기웅", 
				"010-1234-5678", true, false, id);
		//회원임을 표시
		orderVo.setIsmember(true);
		orderVo.setOrderList(orderList);
		
		ResultActions resultActions = mockMvc.perform(post(ORDERURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo))
				.characterEncoding("utf-8"));
		//fail이 떠야된다.
		resultActions
		.andDo(print())
		.andExpect(status().isBadRequest()) 
		.andExpect(jsonPath("$.result", is("fail")));
	}
```

