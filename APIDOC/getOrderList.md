## 주문내역조회

 **요구 사항**

​	1. 해당 ID를 가진 회원(또는 비회원)이 주문한 주문 내역을 전체 조회한다.



■ 실제동작코드 

- Controller

```java
// 전체 주문 내역조회
	@ApiOperation(value = "전체주문 내역조회")
	@GetMapping(value = "/list")
	public ResponseEntity<JSONResult> getOrderList(@RequestBody String id) {
		List<OrderVo> orderList = orderService.getOrderList(id);
		if(orderList!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(orderList));
		}
		else {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("주문 내역이 존재하지 않습니다."));
		}
	}
```

- Service

```java
//전체 주문내역 조회
	public List<OrderVo> getOrderList(String id) {

		return orderDao.getOrderList(id);
	}
```

- Repository

```java
//해당 id의 주문내역 조회
	public List<OrderVo> getOrderList(String id) {
		List<OrderVo> orderList = sqlSession.selectList("order.getOrderList", id);
		return orderList;
	}
```



### 테스트케이스

#### case1.  성공 케이스

ska2253@naver.com 이라는 ID를 가진 회원의 주문내역 조회

**Request**

 HTTP Method = GET<br>
      Request URI = /api/order/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = "ska2253@naver.com"

**Response**           

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":[{<br>**"order_no":2,"**recv_name":"남기웅","recv_tel":"010-1234-5678","destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":true,"order_date":"8월 1, 2019","pay":0,"ismember":false,"iscart":false,"id":"ska2253@naver.com"},{<br>**"order_no":3,"**recv_name":"문상수","recv_tel":"111-1111-1111","destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":false,"order_date":"8월 1, 2019","pay":0,"ismember":false,"iscart":false,"id":"ska2253@naver.com"},{<br>**"order_no":5,"**recv_name":"남기웅","recv_tel":"010-1234-5678","destination":"서울","ord_name":"남기웅","ord_tel":"010-1234-5678","is_same":true,"order_date":"8월 1, 2019","pay":0,"ismember":false,"iscart":false,"id":"ska2253@naver.com"}]}



■  테스트코드

```java
// # 주문내역 조회
		@Test
		public void TestB0() throws Exception{
			System.out.println("주문내역 조회");
			//ska2253@naver.com 의 주문내역 조회
			String id = "ska2253@naver.com";
			
			ResultActions resultActions = mockMvc.perform(get(ORDERURL+"/list")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(id))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
```



