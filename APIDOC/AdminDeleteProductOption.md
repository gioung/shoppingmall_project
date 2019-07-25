## 관리자 특정 상품 옵션  삭제

 요구 사항

  1. 현재 상품에 등록되어 있는 옵션만을 삭제 할 수 있다.

     

■ 실제동작코드 

```java
@ApiOperation(value = "관리자 해당 상품 옵션 삭제")
	@RequestMapping(value = "/list/{no}/{d_no}", method = RequestMethod.DELETE)
	public ResponseEntity<JSONResult> deleteProductOption(@PathVariable("no") Long no, @PathVariable("d_no") Long d_no){
		ProductDetailVo pdv = new ProductDetailVo(d_no, no);
		
		if(shopService.deleteProductDetail(pdv)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("잘못된 삭제 요청입니다."));
		}
		
	}
```

- [서비스코드](https://github.com/gioung/shoppingmall_project/blob/master/shop_backend/src/main/java/com/cafe24/shoppingmall/service/ShopService.java)

### 스트케이스

#### case1.  성공케이스

**Request**

HTTP Method = DELETE<br>
      Request URI = /api/admin/product/list/1/2



**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}



■  테스트코드

```java
//#6 특정 상품 옵션 삭제
		//case1. 성공 케이스
		@Test
		public void testF_1() throws Exception{
			// 상품번호가 1인 상품의 옵션중 2번째 옵션을 삭제한다.
			long no = 1L;
			long p_no = 2L;
			
			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/list/{no}/{p_no}", no , p_no)
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
		}
```



#### case2. 잘못된 옵션삭제 요청

존재하지 않는 옵션번호 삭제 요청을 url을 통해서 할 때

**Request**

HTTP Method = DELETE<br>
      Request URI = /api/admin/product/list/1/5

**Response**

Status = 400<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"fail","message":"잘못된 삭제 요청입니다."}

■  테스트코드

```java
//case2. 실패 케이스 : 없는 옵션삭제를 요청
		@Test
		public void testF_2() throws Exception{
			// 상품번호가 1인 상품의 옵션중 5번째 옵션을 삭제한다.
			// 5번째 옵션은 없는 옵션이다.
			long no = 1L;
			long p_no = 5L;
			
			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/list/{no}/{p_no}", no , p_no)
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isBadRequest());
		}
```

