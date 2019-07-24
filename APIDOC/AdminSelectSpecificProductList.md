## 관리자 특정 상품조회

 요구 사항

​	1. 특정 상품에 해당하는 정보와 상세정보를 모두 출력해야 한다.



■ 실제동작코드 

```java
@ApiOperation(value = "관리자 상품 조회")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getProduct(@PathVariable("no") Long no) {
		//관리자 상품 조회
		Map<String,Object> map = shopService.getProduct(no);
		
		if(map != null) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(map));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("없는 상품입니다."));
		}
	}
```

- [서비스코드]()

### 테스트케이스

#### case1. 성공케이스

1번 상품을 조회한다고 가정

**Request** 

HTTP Method = GET<br>
      Request URI = /api/admin/product/list/1

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":<br>{**"product"**:{**"product_no":1**,"name":"티셔츠",<br>"price":42000,"image":"imageURL","summary_desc":"요약설명","detail_desc":"상세설명",<br>"display":true,"material":"원자재","provider":"공급사","manufacturer":"제조사","origin":"원산지"},<br>**"productDetailList"**:[{"pd_detail_no":1,"product_no":1,"option":"레드100","inventory":100},<br>{"pd_detail_no":2,"product_no":1,"option":"레드105","inventory":90},<br>{"pd_detail_no":3,"product_no":1,"option":"블루100","inventory":95},<br>{"pd_detail_no":4,"product_no":1,"option":"블루105","inventory":77}]}}

■  테스트코드

```java
//#4. 관리자 상품 조회
		// case1. 성공케이스 
		@Test
		public void testD() throws Exception{
			System.out.println("관리자 상품 조회 테스트");
			
			ResultActions resultActions = mockMvc.perform(get(SHOPADMINURL+"/list/{no}",1L));
			
			resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")))
			.andDo(print());
		}
```

