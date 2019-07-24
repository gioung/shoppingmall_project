## 상품 등록 및 옵션리스트 생성

 요구 사항

	1. 상품 필수정보는 꼭 들어가야 한다.
 	2. 하나의 상품에 각 옵션에 대한 재고를 설정 할 수 있어야 한다.



■ 실제동작코드 

```java
//옵션 등록
	@ApiOperation(value = "옵션 리스트 생성")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<JSONResult> addOption(@RequestBody Map<String,Object> map) {
		
		Gson gson = new GsonBuilder().create();
		OptionVo option1 = gson.fromJson(String.valueOf(map.get("option1")), OptionVo.class);
		OptionVo option2 = gson.fromJson(String.valueOf(map.get("option2")), OptionVo.class);
		
		List<OptionVo> optionList = new ArrayList<>();
		optionList.add(option1);
		optionList.add(option2);
		List<String> optionValList = optionService.addOptionValList(optionList);
	
		if(optionValList != null){
			return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(optionValList));
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("옵션 리스트 생성 실패"));
			
		}
	}
```

- [서비스코드](https://github.com/gioung/shoppingmall_project/blob/master/shop_backend/src/main/java/com/cafe24/shoppingmall/service/OptionService.java)



```java
// 상품 등록
	@ApiOperation(value = "상품 등록")
	@RequestMapping(value = "/list", method = RequestMethod.POST) 
	public ResponseEntity<JSONResult> addProducts(@RequestBody Map<String,Object> map) {
		
		Gson gson = new GsonBuilder().create();
		Type listType = new TypeToken<ArrayList<ProductDetailVo>>(){}.getType();
		
		ProductVo productVo = gson.fromJson(String.valueOf(map.get("product")), ProductVo.class);
		List<ProductDetailVo> productDetailVoList = gson.fromJson(String.valueOf(map.get("productDetailList")), listType);

		System.out.println(productVo);
		System.out.println(productDetailVoList);
		// 상품 등록
		boolean judge = shopService.addProduct(productVo, productDetailVoList);
		if(!judge)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(JSONResult.fail("상품 등록 실패")); 
		
		
		// Service에 삽입 요청을 하는 code
		return ResponseEntity.status(HttpStatus.CREATED).body(JSONResult.success(map));
	}
```

- [서비스코드](https://github.com/gioung/shoppingmall_project/blob/master/shop_backend/src/main/java/com/cafe24/shoppingmall/service/ShopService.java)

### 테스트케이스

#### case1. 옵션값 리스트 생성 테스트 (성공테스트)

**Request**

HTTP Method = POST<br>
      Request URI = /api/option/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"option1":{"opt_name":"색상","opt_val":["레드","블루"]},"option2":{"opt_name":"사이즈","opt_val":["100","105"]}}

**Response**

Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":["레드100","레드105","블루100","블루105"]}

■  테스트코드

```java
//#1. 옵션리스트 생성 테스트
	@Test
	public void testA() throws Exception{
		System.out.println("옵션리스트 생성 테스트");
		// 옵션1
		OptionVo optionVo1 = new OptionVo();
		optionVo1.setOpt_name("색상");
		List<String> valueList1 = new ArrayList<>();
		valueList1.add("레드"); valueList1.add("블루");
		optionVo1.setOpt_val(valueList1);
		
		// 옵션2
		OptionVo optionVo2 = new OptionVo();
		optionVo2.setOpt_name("사이즈");
		List<String> valueList2 = new ArrayList<>();
		valueList2.add("100"); valueList2.add("105");
		optionVo2.setOpt_val(valueList2);
		
		Map<String,Object> map = new HashMap<>();
		map.put("option1", optionVo1);
		map.put("option2", optionVo2);
		
		// 옵션 값 리스트 생성
		for(int i=0; i<valueList1.size(); i++) {
			for(int j=0; j<valueList2.size(); j++) {
				optionValList.add(valueList1.get(i).concat(valueList2.get(j)));
			}
		}
		System.out.println("optionValList = "+ optionValList);
		
		ResultActions resultAction = mockMvc.perform(post(OPTIONURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(map))
				.characterEncoding("utf-8"));
		
		resultAction.andExpect(status().isCreated())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
		
		
	}
```



#### case2. 상품등록 테스트 (성공테스트)

**Request**

HTTP Method = POST<br>
      Request URI = /api/shop/list<br>
       Parameters = {}<br>
          Headers = [Content-Type:"application/json;charset=utf-8"]<br>
             Body = {"product":{"product_no":0,"name":"티셔츠","price":42000,"image":"imageURL","summary_desc":"요약설명","detail_desc":"상세설명","display":true,"material":"원자재","provider":"공급사","manufacturer":"제조사","origin":"원산지"},"productDetailList":[{"pd_detail_no":0,"product_no":0,"option":"레드100","inventory":100},{"pd_detail_no":0,"product_no":0,"option":"레드105","inventory":90},{"pd_detail_no":0,"product_no":0,"option":"블루100","inventory":95},{"pd_detail_no":0,"product_no":0,"option":"블루105","inventory":77}]}

**Response**

Status = 201<br>
    Error message = null<br>
          Headers = [Content-Type:"application/json;charset=UTF-8"]<br>
     Content type = application/json;charset=UTF-8<br>
             Body = {"result":"success","data":{"product":{"product_no":0.0,"name":"티셔츠","price":42000.0,"image":"imageURL","summary_desc":"요약설명","detail_desc":"상세설명","display":true,"material":"원자재","provider":"공급사","manufacturer":"제조사","origin":"원산지"},"productDetailList":[{"pd_detail_no":0.0,"product_no":0.0,"option":"레드100","inventory":100.0},{"pd_detail_no":0.0,"product_no":0.0,"option":"레드105","inventory":90.0},{"pd_detail_no":0.0,"product_no":0.0,"option":"블루100","inventory":95.0},{"pd_detail_no":0.0,"product_no":0.0,"option":"블루105","inventory":77.0}]}}

■  테스트코드

```java
//#2. 상품등록 테스트
	@Test
	public void testB() throws Exception {
		System.out.println("상품등록 테스트");
		ProductVo productVo = new ProductVo("티셔츠", 42000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지");
		long[] inventorys = {100L, 90L, 95L, 77L};
		List<ProductDetailVo> productDetailVoList = new ArrayList<>();
		
		for(int i=0; i<inventorys.length; i++) {
			productDetailVoList.add(new ProductDetailVo(optionValList.get(i), inventorys[i]));
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("product", productVo);
		map.put("productDetailList", productDetailVoList);
		
		ResultActions resultActions = mockMvc.perform(post(SHOPURL+"/list")
		.contentType(MediaType.APPLICATION_JSON)
		.content(new Gson().toJson(map))
		.characterEncoding("utf-8"));
		
		resultActions
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
	}
```



- <b>개발시 발생한 issue</b>

  **구현상의 이슈**

  1. 상품등록시 상품의 옵션, 옵션값들, 옵션세트도 등록시켜야 되는데 이를 어떻게 구현 할 것인가?

     => 클라이언트가 상품등록 api를 요청시 요청 데이터를 여러개의 table (상품 테이블, 옵션 테이블)에 insert가 되도록 로직을 구성한다. 

     => 여러 table에 select, insert되므로 구현이 복잡하고 DB접근 횟수가  많아서 고민끝에 구현을 바꾸기로 결정

     => 상품상세 table에 상품옵션을 insert하기로 결정

     => 위 과정에서 많은 시간을 소비하였습니다.

  2. ShopController 구현시 Test 단 에서 vo 객체를 두개이상 보내기 위해 Map<String, Object>에 vo객체를 저장하고 이를 json으로 변환하여 데이터를 보낸 후 ShopController에서 받을시 vo 객체로 다시 형변환(casting)을 할때, 형변환 오류가 생김. 

     ​	=> 테스트단에서 Json 데이터를 변환해주는 라이브러리인 Gson의 공식문서를 참고하여 해결 , 앞으로 막히는 문제가 있으면 공식문서를 먼저 참조해야겠다는 생각이 들었습니다.

  

  

   