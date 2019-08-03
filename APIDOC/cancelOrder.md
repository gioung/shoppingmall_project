## 주문취소

 **요구 사항**

	1. uri의 마지막에 취소할 주문번호가 입력된다.
 	2. 해당 주문번호를 취소할 권한이 있는것은 그것을 주문한 사람뿐이다.



<b>개발시 발생한 issue</b>

​	1. 취소할 권한 부여를 어떻게 할 것인가?<br>  =>먼저 그 주문번호를 해당 회원이 가지고 있는지 확인한 후 가지고 있다면 삭제를 한다. 



■ 실제동작코드 

- Controller

```java
// 주문 취소
	@ApiOperation(value = "주문 취소")
	@DeleteMapping(value="/list/{no}")
	public ResponseEntity<JSONResult> cancelOrder(@PathVariable("no") long order_no, @RequestBody OrderVo orderVo) {

		orderVo.setOrder_no(order_no);

		if (orderService.cancelOrder(orderVo)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(orderVo));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("주문 취소 실패"));
		}
	}
```

- Service

```java
//주문취소
	public boolean cancelOrder(OrderVo orderVo) {
		boolean isOwner=orderDao.isOwner(orderVo);
		System.out.println("isOwner = " + isOwner);
		//주문번호에 대응하는 회원인지 확인
		if(isOwner) {
			orderDao.cancelOrderList(orderVo);
			return orderDao.cancelOrder(orderVo);
		}
		return false;
		
	}
```

- Repository

```java
//주문번호에 대응하는 회원인지 확인
	public boolean isOwner(OrderVo orderVo) {
		OrderVo result = sqlSession.selectOne("order.isOwner", orderVo);
		if(null == result)
			return false;
		return orderVo.getId().equals(result.getId());
	}
//주문번호에 대응하는 회원인지 확인
		if(isOwner) {
			orderDao.cancelOrderList(orderVo);
			return orderDao.cancelOrder(orderVo);
		}
```



### 테스트케이스

#### case1. 비회원 주문취소

**Request**

 HTTP Method = DELETE<br>
      Request URI = /api/order/list/1<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"order_no":0,"is_same":false,"pay":0,"ismember":false,"iscart":false,"id":"00nS2AZhLydb4BgQlps5"}



**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":<b{"order_no":1,"is_same":false,"pay":0,"ismember":false,"iscart":false,"id":"00nS2AZhLydb4BgQlps5"}}

■  테스트코드

```java
@Test
		public void TestC00() throws Exception{
			System.out.println("주문 취소(비회원)");
			//비회원 주문 취소
			String id=cartService.getTempId();
			System.out.println("id = "+id);
			long order_no = 1L;
			
			OrderVo orderVo = new OrderVo();
			orderVo.setId(id);
			
			ResultActions resultActions = mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
					
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
			
		}
```



#### case2. 회원 주문취소

**Request**

 HTTP Method = DELETE<br>
      Request URI = /api/order/list/2<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"order_no":0,"is_same":false,"pay":0,"ismember":false,"iscart":false,"id":"ska2253@naver.com"}

**Response**

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"order_no":2,"is_same":false,"pay":0,"ismember":false,"iscart":false,"id":"ska2253@naver.com"}}

■  테스트코드

```java
@Test
		public void TestC01() throws Exception{
			System.out.println("주문 취소(회원)");
			//회원 주문 취소
			String id="ska2253@naver.com";
			long order_no = 2L;
			
			OrderVo orderVo = new OrderVo();
			orderVo.setId(id);
			
			ResultActions resultActions = mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
					
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
			
			order_no = 3L;
			mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
			order_no = 4L;
			mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
			order_no = 5L;
			mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
		}
```







```java

```



