## 카테고리 등록

 요구 사항

	1. 카테고리는 상위카테고리와 하위카테고리로 나뉘어 진다.
 	2. 하위카테고리는 상위카테고리 중 하나에 종속된다.



■ 실제동작코드 

```java
@ApiOperation(value = "카테고리 생성")
	@RequestMapping(value="/category", method = RequestMethod.POST)
	public ResponseEntity<JSONResult> addCategory(@RequestBody List<CategoryVo> categoryList){
		
		if(shopService.addCategory(categoryList))
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(true));
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("카테고리 등록 실패"));
		}
		
	}
```

[서비스코드](https://github.com/gioung/shoppingmall_project/blob/master/shop_backend/src/main/java/com/cafe24/shoppingmall/service/ShopService.java)



### 테스트케이스

#### case1. 성공케이스

**Request**

HTTP Method = POST<br>
      Request URI = /api/admin/product/category<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = [{"main_no":1,"sub_no":0,"name":"TOP"},{"main_no":1,"sub_no":1,"name":"반팔티"}]

**Response**      

​    Status = 201<br>
​    Error message = null<br>
​          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
​     Content type = application/json;charset=UTF-8<br>
​             Body = {"result":"success","data":true}



■  테스트코드

```java
//# 카테고리 등록 테스트
		@Test
		public void testB_0() throws Exception {
			System.out.println("카테고리 등록 테스트");
			// 메인 카테고리 1
			CategoryVo categoryVo1 = new CategoryVo(1L, "TOP");
			
			//서브 카테고리 1-1
			CategoryVo subCategoryVo1 = new CategoryVo(1L, 1L, "반팔티");
		
			
			List<CategoryVo> categoryList = new ArrayList<>();
			categoryList.add(categoryVo1);
			categoryList.add(subCategoryVo1);
		
			
			ResultActions resultActions = mockMvc.perform(post(SHOPADMINURL+"/category")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(categoryList))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isCreated());
					
		}
```

