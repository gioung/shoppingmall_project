## 주문내역 상세조회

 **요구 사항**

	1. uri의 끝에 주문 번호가 들어간다.
 	2. 만약에 잘못된 번호가 들어가면 잘못된 요청응답을 보내야 한다. 
 	3. 해당 주문번호의 주문 상품내역들을 조회한다.



<b>개발시 발생한 issue</b>

	1. 해당 주문번호가 존재는 하지만 해당 회원의 주문번호가 아닐경우. 즉, 다른회원이 주문번호에 접근할 때 어떻게 처리를 할것인가?<br>=>DB단에서 where절을 주문 테이블과 주문상세테이블을 id와 주문번호에 대해 동등조인을 수행한다.



■ 실제동작코드 

- Controller

```java
// 주문 상세내역 조회
	@ApiOperation(value = "주문 상세내역 조회")
	@GetMapping(value = "/list/{no}")
	public ResponseEntity<JSONResult> getOrderDetailList(@PathVariable("no")long order_no,
			@RequestBody OrderVo orderVo) {
		orderVo.setOrder_no(order_no);
		List<OrderedProductVo> orderDetailList = orderService.getOrderDetailList(orderVo);
		
		if(orderDetailList!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(orderDetailList));
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("해당 주문 상세내역이 유효하지 않습니다."));
		}
	}
```

- Service

```java
//주문 상세내역 조회
	public List<OrderedProductVo> getOrderDetailList(OrderVo orderVo) {
		
		return orderDao.getOrderDetailList(orderVo);
	}
```

- Repository

```java
//주문 상세 내역 조회
	public List<OrderedProductVo> getOrderDetailList(OrderVo orderVo) {
		List<OrderedProductVo> orderDetailList = sqlSession.selectList("order.getOrderDetailList", orderVo);
		return orderDetailList;
	}
```



### 테스트케이스

#### case1.  성공케이스

ID가 ska2253@naver.com인 회원의 주문번호 2번의 상세내역을 조회

**Request**

 HTTP Method = GET<br>
      Request URI = /api/order/list/**2**<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"order_no":0,"is_same":false,"pay":0,"ismember":false,"iscart":false,**"id":"ska2253@naver.com"**}

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":[{"product_no":1,"pd_detail_no":1,**"order_no":2**,"qty":4,"pay":168000}]}

■  테스트코드

```java
// # 주문내역 상세 조회
		@Test
		public void TestB1() throws Exception{
			System.out.println("주문내역 상세 조회");
			String id = "ska2253@naver.com";
			long order_no = 2L;
			
			OrderVo orderVo = new OrderVo();
			orderVo.setId(id);
			
			ResultActions resultActions = mockMvc.perform(get(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
```

