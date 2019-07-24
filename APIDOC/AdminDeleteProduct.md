## 관리자 특정 상품 삭제

 요구 사항

	1. 상품을 삭제하면 해당 상품에 대응되는 옵션데이터를 먼저 삭제한 후 삭제한다.



■ 실제동작코드 

```java
@ApiOperation(value = "관리자 해당 상품 삭제")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResult> deleteProduct(@PathVariable("no") Long no) {
		
		boolean judge = shopService.deleteProduct(no);
		
		if(judge) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 삭제 실패"));
		}
	}
```

- [서비스코드]()

### 테스트케이스

#### case1. 성공케이스

**Request**
      HTTP Method = DELETE<br>
      Request URI = /api/admin/product/list/1<br>

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}

■  테스트코드

```java
//#6 상품삭제
		//case1. 성공 케이스
		@Test
		public void testF() throws Exception{
			long no = 1L;
			
			ResultActions resultActions = 	         mockMvc.perform(delete(SHOPADMINURL+"/list/{no}",no)
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
			
		}
```

