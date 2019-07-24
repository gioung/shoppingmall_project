## 관리자 상품목록조회

 요구 사항

1. 관리자가 등록한 모든 상품을 조회한다.



■ 실제동작코드 

```java
@ApiOperation(value = "관리자 상품 목록")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getAllProductList() {
		
		// 관리자 상품 목록 조회
		List<ProductVo> productList= shopService.getAllProductList();
		if(productList != null)
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(productList));
			 
		// Service에 삽입 요청을 하는 code
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(JSONResult.fail("상품 등록 실패"));
	}
```

- [서비스코드](https://github.com/gioung/shoppingmall_project/blob/master/shop_backend/src/main/java/com/cafe24/shoppingmall/service/ShopService.java)

### 테스트케이스

#### case1. 성공케이스

**Request**

HTTP Method = GET<br>
      Request URI = /api/admin/product/list

**Response** (들어있는 데이터 1개)

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":[{"product_no":1,"name":"티셔츠"<br>,"price":42000,"image":"imageURL","summary_desc":"요약설명","detail_desc":"상세설명",<br>"display":true,"material":"원자재","provider":"공급사","manufacturer":"제조사","origin":"원산지"}]}

■  테스트코드

```java
//관리자 상품목록 조회
		// case1. 성공케이스 
		@Test
		public void testC() throws Exception{
			System.out.println("관리자 상품목록 조회 테스트");
			
			ResultActions resultActions = mockMvc.perform(get(SHOPADMINURL+"/list"));
			
			resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")))
			.andDo(print());
		}
```

