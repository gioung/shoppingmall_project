## 카테고리 조회

 요구 사항

 	1. 카테고리 전체 조회
      	1. 상위카테고리와 하위카테고리 둘 다 조회해야 한다.
	2. 하위 카테고리 조회
    	1. url로 들어온 번호에 해당하는 상위카테고리의 하위카테고리들을 조회해야 한다.

■ 실제동작코드 

- 카테고리 전체 조회

```java
@ApiOperation(value = "카테고리 리스트 조회")
	@RequestMapping(value="/category/list", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getCategoryList(){		
		List<CategoryVo> categoryList = shopService.getCategoryList();
		
		if(!categoryList.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(categoryList));
		else
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("존재하는 카테고리가 없습니다."));
	}
```

- 하위 카테고리 조회

```java
@ApiOperation(value= "특정 하위카테고리 조회")
	@RequestMapping(value = "/category/list/{no}", method = RequestMethod.GET)
	public ResponseEntity<JSONResult> getSubCategoryList(@PathVariable("no") long no){
		List<CategoryVo> subCategoryList = shopService.getSubCategoryList(no);
		
		if(!subCategoryList.isEmpty())
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(subCategoryList));
		else
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.fail("존재하는 카테고리가 없습니다."));
	}
```





### 테스트케이스

#### case1.카테고리 전체 조회 성공케이스

**Request**

HTTP Method = GET<br>
      Request URI = /api/product/category/list

**Response**

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":[{"main_no":1,"sub_no":0,"name":"TOP"},<br>{"main_no":2,"sub_no":0,"name":"PANTS"},{"main_no":1,"sub_no":1,"name":"반팔티"},<br>{"main_no":1,"sub_no":2,"name":"나시"},{"main_no":2,"sub_no":1,"name":"반바지"},<br>{"main_no":2,"sub_no":2,"name":"슬렉스"}]}



■  테스트코드

```java
		@Test
		public void testB_01() throws Exception{
			System.out.println("카테고리 전체 조회 테스트");
			//상품 등록 view에서 카테고리를 고르는데 쓰일 수 있다.
			ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/category/list"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
```



#### case2. 하위 카테고리 조회 성공 테스트

2번 상위 카테고리의 하위카테고리 목록을 조회

**Request**

 HTTP Method = GET<br>
      Request URI = /api/product/category/list/2

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":[{"main_no":2,"sub_no":1,"name":"반바지"},<br>{"main_no":2,"sub_no":2,"name":"슬렉스"}]}

■  테스트코드

```java
@Test
		public void testB_02() throws Exception{
			System.out.println("특정 카테고리 리스트(상위 - 하위,하위..)");
			//유저가 화면에서 상위옵션에 마우스 커서를 올렸을때 특정 상위옵션의 하위옵션들을 조회하여야 하는 경우
			ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/category/list/{no}",2L));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
			
		}
```



