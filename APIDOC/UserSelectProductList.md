## 유저 상품목록 조회

 요구 사항

	1.   현재 진열상태가 true인 상품들만 보여주어야 한다.



■ 실제동작코드 

```java
//상품목록 조회
	@ApiOperation(value = "상품목록 조회")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public JSONResult getProductList() {
		//display가 true인 상품조회
		List<ProductVo> productList = shopService.getProductList();
		
		if(productList != null)
			return JSONResult.success(productList);
		else
			return JSONResult.fail("상품 조회 실패");
	}
```



### 테스트케이스

#### case1. 상품 조회 테스트 (성공테스트)

**Request**

 HTTP Method = GET<br>
      Request URI = /api/shop/list<br>
       Parameters = {}<br>
          Headers = []

**Response**

  Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":[{"product_no":18,"name":"티셔츠",<br>"price":42000,"image":"imageURL","summary_desc":"요약설명","detail_desc":"상세설명"<br>,**"display":true,**"material":"원자재","provider":"공급사","manufacturer":"제조사","origin":"원산지"}]}

**display= false인 데이터는 나오지 않는다.**



■  테스트코드

```java
//# 상품목록 조회 테스트 (사용자가 보는 화면에 뿌려주는 데이터)
	@Test
	public void testC() throws Exception{
		System.out.println("상품목록 조회 테스트");
		
		ResultActions resultActions = mockMvc.perform(get(SHOPURL+"/list"));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
		
	}
```



