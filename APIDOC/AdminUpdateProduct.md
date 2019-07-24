## 관리자 특정 상품수정

 요구 사항

	1. 수정시 필수 항목이 비어있으면 안된다.



■ 실제동작코드 

```java
@ApiOperation(value = "관리자 상품 수정")
	@RequestMapping(value = "/list/{no}", method = RequestMethod.PUT)
	public ResponseEntity<JSONResult> updateProduct(@PathVariable("no") Long no,
			@RequestBody Map<String,Object> map) {
		
		System.out.println("map = " + map);
		
		Gson gson = new GsonBuilder().create();
		Type listType = new TypeToken<ArrayList<ProductDetailVo>>(){}.getType();
		
		ProductVo productVo = gson.fromJson(String.valueOf(map.get("product")), ProductVo.class);
		List<ProductDetailVo> productDetailVoList = gson.fromJson(String.valueOf(map.get("productDetailList")), listType);
		
		Validator validator = 
				Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<ProductVo>> validatorResults = validator.validate(productVo);
		if(!validatorResults.isEmpty())
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(validatorResults.toString()));
		
		Set<ConstraintViolation<ProductDetailVo>> validatorResults2;
		for(ProductDetailVo pdv:productDetailVoList) {
			validatorResults2 = validator.validate(pdv);
			if(!validatorResults2.isEmpty())
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail(validatorResults2.toString()));
		}
		
		boolean judge = shopService.updateProduct(productVo, productDetailVoList);
		
		if(judge) {
			return ResponseEntity.status(HttpStatus.OK).body(JSONResult.success(map));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 수정 실패"));
		}
	}
```

- [서비스코드]()

### 테스트케이스

#### case1. 성공케이스

**Request**

HTTP Method = PUT<br>
      Request URI = /api/admin/product/list/1<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"**product**":{"product_no":1,"name":"청바지",<br>"price":50000,"image":"imageURL","summary_desc":"요약설명","detail_desc":"상세설명",<br>"display":true,"material":"원자재","provider":"공급사","manufacturer":"제조사","origin":"원산지"},<br>"**productDetailList**":[{"pd_detail_no":1,"product_no":1,"option":"검정95","inventory":100},<br>{"pd_detail_no":2,"product_no":1,"option":"회색105","inventory":90},<br>{"pd_detail_no":3,"product_no":1,"option":"빨강110","inventory":95},<br>{"pd_detail_no":4,"product_no":1,"option":"초록100","inventory":77}]}

**Response**

Status = 200<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
         

■  테스트코드

```java
//#5 관리사 상품 수정
		//case1. 성공케이스
		@Test
		public void testE_1() throws Exception{
			System.out.println("관리자 상품 수정 테스트");
			long no = 1L;
			ProductVo productVo = new ProductVo(no,"청바지", 50000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지");
			long[] inventorys = {100L, 90L, 95L, 77L};
			String[] options = {"검정95", "회색105", "빨강110", "초록100"};
			List<ProductDetailVo> productDetailVoList = new ArrayList<>();
			
			
			for(int i=0; i<inventorys.length; i++) {
				optionValList.set(i, options[i]);
				productDetailVoList.add(new ProductDetailVo(i+1, no, optionValList.get(i), inventorys[i]));
			}
			Map<String, Object> map = new HashMap<>();
			map.put("product", productVo);
			map.put("productDetailList", productDetailVoList);
			
			System.out.println("requestJSON = " + new Gson().toJson(map));
			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/list/{no}",no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(map))
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
			
		}
```



#### case2. 유효성 검사 실패 

수정시 상품이름을 null값으로 넣어본다.

**Request**

 HTTP Method = PUT<br>
      Request URI = /api/admin/product/list/1<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>             Body = {"**product**":{"product_no":1,"price":50000,"image":"imageURL","summary_desc":"요약설명",<br>"detail_desc":"상세설명","display":true,"material":"원자재","provider":"공급사","manufacturer":"제조사","origin":"원산지"},<br>"**productDetailList**":[{"pd_detail_no":1,"product_no":1,"option":"검정95","inventory":100},{"pd_detail_no":2,"product_no":1,"option":"회색105","inventory":90},{"pd_detail_no":3,"product_no":1,"option":"빨강110","inventory":95},{"pd_detail_no":4,"product_no":1,"option":"초록100","inventory":77}]}

**Response**

  Status = 400<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"fail","**message**":"[ConstraintViolationImpl{interpolatedMessage\u003d\u0027**반드시 값이 있어야 합니다.**\u0027,<br> propertyPath\u003dname, rootBeanClass\u003dclass com.cafe24.shoppingmall.repository.vo.ProductVo, messageTemplate\u003d\u0027반드시 값이 있어야 합니다.\u0027}]"}

■  테스트코드

```java
@Test
		public void testE_2() throws Exception{
			long no = 1L;
			ProductVo productVo = new ProductVo(no,null, 50000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지");
			long[] inventorys = {100L, 90L, 95L, 77L};
			String[] options = {"검정95", "회색105", "빨강110", "초록100"};
			List<ProductDetailVo> productDetailVoList = new ArrayList<>();
			
			
			for(int i=0; i<inventorys.length; i++) {
				optionValList.set(i, options[i]);
				productDetailVoList.add(new ProductDetailVo(i+1, no, optionValList.get(i), inventorys[i]));
			}
			Map<String, Object> map = new HashMap<>();
			map.put("product", productVo);
			map.put("productDetailList", productDetailVoList);
			
			
			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/list/{no}",no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(map))
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.result", is("fail")));
			
		}
```



- <b>개발시 발생한 issue</b>

  1. Map객체를 RequestMessage의 body에 넣어서 보낼때 Controller 단에서 어떻게 유효성 검사를 하는가?

     => Validator 객체를 이용하여 Map에 저장되어 있는 vo객체들을 꺼내서 유효성 검사를 하는 방법을 찾아서 적용하였습니다.