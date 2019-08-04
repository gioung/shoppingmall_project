## 카테고리 수정

 **요구 사항**

 	1.  메인카테고리 수정	
      - 메인 카테고리의 이름을 바꿀 수 있어야 한다.
      - URL을 통해 접근할 때 존재하는 카테고리 번호여야 한다.
	2.  서브카테고리 수정
     - 서브 카테고리의 이름을 바꿀 수 있어야 한다.
     - URL을 통해 접근할 때 존재하는 서브카테고리 번호여야 한다.



■ 실제동작코드 

```java
@ApiOperation(value= "카테고리 수정")
	@PutMapping(value="/category/list/{no}")
	public ResponseEntity<JSONResult> updateMainCategory(@PathVariable("no") long no,
			@RequestBody CategoryVo categoryVo){
		
		categoryVo.setMain_no(no);
		if(shopService.updateMainCategory(categoryVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("메인 카테고리 수정 실패"));
	}
```

```java
@ApiOperation(value= "하위 카테고리 수정")
	@PutMapping(value="/category/list/{no}/{s_no}")
	public ResponseEntity<JSONResult> updateMainCategory(@PathVariable("no") long no,
			@PathVariable("s_no") long s_no, @RequestBody CategoryVo categoryVo){
		
		categoryVo.setMain_no(no);
		categoryVo.setSub_no(s_no);
		
		if(shopService.updateSubCategory(categoryVo))
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(true));
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("서브 카테고리 수정 실패"));
	}
```



### 테스트케이스

#### case1.  메인카테고리 수정 성공

**Request**

 HTTP Method = PUT<br>
      Request URI = /api/admin/product/category/list/1<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"main_no":0,"sub_no":0,"name":"BOTTOM"}



**Response**

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}

■  테스트코드

```java
//# 메인 카테고리 수정
		@Test
		public void testB_03() throws Exception{
			System.out.println("메인 카테고리 수정");
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setName("BOTTOM");
			
			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/category/list/{no}", 1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(categoryVo))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
```



#### case2. 서브카테고리 수정 성공

**Request**

HTTP Method = PUT<br>
      Request URI = /api/admin/product/category/list/1/1<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"main_no":0,"sub_no":0,"name":"셔츠"}

**Response**

 Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":true}

■  테스트코드

```java
//# 하위 카테고리 수정
		@Test
		public void testB_04() throws Exception{
			System.out.println("서브 카테고리 수정");
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setName("셔츠");
			
			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/category/list/{no}/{s_no}",1L,1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(categoryVo))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
```



- <b>개발시 발생한 issue</b>

  1. 카테고리를 어느정도까지 확장해야되는가? 
  <br>  => 대부분의 쇼핑몰이 2차 카테고리까지만 사용하므로 메인카테고리, 서브카테고리 테이블을 두어서 관리하기로 하였습니다.
