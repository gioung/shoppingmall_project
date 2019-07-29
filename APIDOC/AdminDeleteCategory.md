## 카테고리 삭제	

 **요구 사항**

 	1. 상위 카테고리 삭제
     - 상위 카테고리 삭제 시 포함하고 있던 모든 하위 카테고리를 삭제 해야 한다.
	2. 하위 카테고리 삭제
    - 하위 카테고리를 별도로 삭제 할 수 있어야 한다.



■ 실제동작코드 

```java
@ApiOperation(value = "하위 카테고리 삭제")
	@DeleteMapping(value="/category/list/{no}/{s_no}")
	public ResponseEntity<JSONResult> deleteSubCategory(@PathVariable("no") Long main_no,
			@PathVariable("s_no") Long sub_no){
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setMain_no(main_no);
		categoryVo.setSub_no(sub_no);
		
		if(shopService.deleteSubCategory(categoryVo)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("하위 카테고리 삭제 실패"));
		}
	}
```

```java
@ApiOperation(value = "상위 카테고리 삭제")
	@DeleteMapping(value="/category/list/{no}")
	public ResponseEntity<JSONResult> deleteMainCategory(@PathVariable("no") Long main_no
		){
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setMain_no(main_no);
			
		if(shopService.deleteMainCategory(categoryVo)) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상위 카테고리 삭제 실패"));
			}
	
	}
```



### 테스트케이스

#### case1. 하위 카테고리 삭제 성공

**Request**

HTTP Method = DELETE<br>
      Request URI = /api/admin/product/category/list/1/1

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}

■  테스트코드

```java
// 하위 카테고리 삭제
		@Test
		public void TestF_3() throws Exception{
			System.out.println("하위 카테고리 삭제");
			long main_no = 1L; //main_no = 1 이고
			long sub_no = 1L; //sub_no = 1 인 카테고리 삭제
			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/category/list/{no}/{s_no}",main_no,sub_no)
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
		}
```



#### case2. 상위 카테고리 삭제 성공

**Request**

HTTP Method = DELETE<br>
      Request URI = /api/admin/product/category/list/2

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}

■  테스트코드

```java
// 메인 카테고리 삭제 
		@Test
		public void TestF_4() throws Exception{
			System.out.println("상위 카테고리 삭제");
			long main_no = 2L; // main_no = 2 에 속하는 모든 카테고리 삭제
			
			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/category/list/{no}",main_no)
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
			
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setMain_no(1L);
			shopService.deleteMainCategory(categoryVo);
		}
```

