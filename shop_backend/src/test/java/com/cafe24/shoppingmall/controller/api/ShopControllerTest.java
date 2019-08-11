package com.cafe24.shoppingmall.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.shoppingmall.repository.vo.CategoryVo;
import com.cafe24.shoppingmall.repository.vo.OptionVo;
import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;
import com.cafe24.shoppingmall.service.ShopService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopControllerTest {
	private MockMvc mockMvc;
	private static final String SHOPADMINURL = "/api/admin/product";
	private static final String SHOPCOMMONURL = "/api/product";
	private static final String OPTIONURL = "/api/option";
	
	private static List<String> optionValList = new ArrayList<>();
	private static List<String> optionValList2 = new ArrayList<>();
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	ShopService shopService;
	
	@Before
	public void setup() { 
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@BeforeClass
	public static void startTest() {
		System.out.println("Test Start!!");
	}
	
//	//#1. 옵션리스트 생성 테스트
//		@Test
//		public void testA() throws Exception{
//			System.out.println("옵션리스트 생성 테스트");
//			// 옵션1
//			OptionVo optionVo1 = new OptionVo();
//			optionVo1.setOpt_name("색상");
//			List<String> valueList1 = new ArrayList<>();
//			valueList1.add("레드"); valueList1.add("블루");
//			optionVo1.setOpt_val(valueList1);
//			
//			// 옵션2
//			OptionVo optionVo2 = new OptionVo();
//			optionVo2.setOpt_name("사이즈");
//			List<String> valueList2 = new ArrayList<>();
//			valueList2.add("100"); valueList2.add("105");
//			optionVo2.setOpt_val(valueList2);
//			
//			Map<String,Object> map = new HashMap<>();
//			map.put("option1", optionVo1);
//			map.put("option2", optionVo2);
//			
//			// 옵션 값 리스트 생성
//			for(int i=0; i<valueList1.size(); i++) {
//				for(int j=0; j<valueList2.size(); j++) {
//					optionValList.add(valueList1.get(i).concat(valueList2.get(j)));
//				}
//			}
//			
//			ResultActions resultAction = mockMvc.perform(post(OPTIONURL+"/list")
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(new Gson().toJson(map))
//					.characterEncoding("utf-8"));
//			
//			resultAction.andExpect(status().isCreated())
//			.andExpect(jsonPath("$.result", is("success")))
//			.andDo(print());
//			
//			// 옵션3
//			OptionVo optionVo3 = new OptionVo();
//			optionVo3.setOpt_name("색상");
//			List<String> valueList3 = new ArrayList<>();
//			valueList3.add("그레이"); valueList3.add("블랙");
//			optionVo3.setOpt_val(valueList3);
//						
//			// 옵션4
//			OptionVo optionVo4 = new OptionVo();
//			optionVo4.setOpt_name("사이즈");
//			List<String> valueList4 = new ArrayList<>();
//			valueList4.add("28"); valueList4.add("30");
//			optionVo4.setOpt_val(valueList4);
//						
//			Map<String,Object> map2 = new HashMap<>();
//			map2.put("option1", optionVo3);
//			map2.put("option2", optionVo4);
//						
//			// 옵션 값 리스트 생성
//			for(int i=0; i<valueList3.size(); i++) {
//				for(int j=0; j<valueList4.size(); j++) {
//						optionValList2.add(valueList3.get(i).concat(valueList4.get(j)));
//					}
//				}			
//			
//			mockMvc.perform(post(OPTIONURL+"/list")
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(new Gson().toJson(map2))
//					.characterEncoding("utf-8"));
//		}
//	
//		//# 카테고리 등록 테스트
//		@Test
//		public void testB_0() throws Exception {
//			System.out.println("카테고리 등록 테스트");
//			// 메인 카테고리 1
//			CategoryVo categoryVo1 = new CategoryVo(1L, "TOP");
//			CategoryVo categoryVo2 = new CategoryVo(2L, "PANTS");
//			//서브 카테고리 1-1
//			CategoryVo subCategoryVo1 = new CategoryVo(1L, 1L, "반팔티");
//			//서브 카테고리 1-2
//			CategoryVo subCategoryVo2 = new CategoryVo(1L, 2L, "나시");
//			//서브 카테고리 2-1
//			CategoryVo subCategoryVo3 = new CategoryVo(2L, 1L, "반바지");
//			//서브 카테고리 2-2
//			CategoryVo subCategoryVo4 = new CategoryVo(2L, 2L, "슬랙스");
//			
//			List<CategoryVo> categoryList = new ArrayList<>();
//			categoryList.add(categoryVo1);
//			categoryList.add(categoryVo2);
//			categoryList.add(subCategoryVo1);
//			categoryList.add(subCategoryVo2);
//			categoryList.add(subCategoryVo3);
//			categoryList.add(subCategoryVo4);
//		
//			
//			ResultActions resultActions = mockMvc.perform(post(SHOPADMINURL+"/category")
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(new Gson().toJson(categoryList))
//					.characterEncoding("utf-8"));
//			
//			resultActions.andDo(print())
//			.andExpect(status().isCreated());
//					
//		}
//		
//		//# 카테고리 조회
//		@Test
//		public void testB_01() throws Exception{
//			System.out.println("카테고리 전체 조회 테스트");
//			//상품 등록 view에서 카테고리를 고르는데 쓰일 수 있다.
//			ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/category/list"));
//			
//			resultActions.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.result", is("success")));
//		
//					
//		}
//		//# 특정 카테고리 조회
//		@Test
//		public void testB_02() throws Exception{
//			System.out.println("특정 카테고리 리스트(상위 - 하위,하위..)");
//			//유저가 화면에서 상위옵션에 마우스 커서를 올렸을때 특정 상위옵션의 하위옵션들을 조회하여야 하는 경우
//			ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/category/list/{no}",2L));
//			
//			resultActions.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.result", is("success")));
//			
//		}
//		
//		//# 메인 카테고리 수정
//		@Test
//		public void testB_03() throws Exception{
//			System.out.println("메인 카테고리 수정");
//			CategoryVo categoryVo = new CategoryVo();
//			categoryVo.setName("TOP");
//			
//			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/category/list/{no}", 1L)
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(new Gson().toJson(categoryVo))
//					.characterEncoding("utf-8"));
//			
//			resultActions.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.result", is("success")));
//		}
//		
//		//# 서브 카테고리 수정
//		@Test
//		public void testB_04() throws Exception{
//			System.out.println("서브 카테고리 수정");
//			CategoryVo categoryVo = new CategoryVo();
//			categoryVo.setName("셔츠");
//			
//			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/category/list/{no}/{s_no}",1L,1L)
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(new Gson().toJson(categoryVo))
//					.characterEncoding("utf-8"));
//			
//			resultActions.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.result", is("success")));
//		}
//		
//		//#2. 상품등록 테스트
//		@Test
//		public void testB_1() throws Exception {
//			System.out.println("상품등록 테스트");
//			ProductVo productVo = new ProductVo(1L,"\"레스트 모던 카라 티셔츠\"", 42000L, "반팔티.jpg", "\"군더더기 없는 깔끔한 라인의 트임 카라 반팔 티셔츠\"",
//					"상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,1L);
//			long[] inventorys = {100L, 90L, 95L, 77L};
//			List<ProductDetailVo> productDetailVoList = new ArrayList<>();
//			
//			for(int i=0; i<inventorys.length; i++) {
//				productDetailVoList.add(new ProductDetailVo(optionValList.get(i), inventorys[i]));
//			}
//			
//			Map<String, Object> map = new HashMap<>();
//			map.put("product", productVo);
//			map.put("productDetailList", productDetailVoList);
//			
//			ResultActions resultActions = mockMvc.perform(post(SHOPADMINURL+"/list")
//			.contentType(MediaType.APPLICATION_JSON)
//			.content(new Gson().toJson(map))
//			.characterEncoding("utf-8"));
//			
//			ProductVo productVo2 = new ProductVo(2L,"\"플레이스 썸머 나시\"", 14000L, "나시.jpg", "\"스트릿 백나염이 매력적인 썸머 나시!\"","상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,2L);
//			long[] inventorys2 = {90L, 95L};
//			List<ProductDetailVo> productDetailVoList2 = new ArrayList<>();
//			
//			for(int i=0; i<inventorys2.length; i++) {
//				productDetailVoList2.add(new ProductDetailVo(optionValList.get(i), inventorys2[i]));
//			}
//			
//			Map<String, Object> map2 = new HashMap<>();
//			map2.put("product", productVo2);
//			map2.put("productDetailList", productDetailVoList2);
//			
//			resultActions = mockMvc.perform(post(SHOPADMINURL+"/list")
//			.contentType(MediaType.APPLICATION_JSON)
//			.content(new Gson().toJson(map2))
//			.characterEncoding("utf-8"));
//			
//			ProductVo productVo3 = new ProductVo(3L,"\"코모도 5부 밴딩 반바지\"", 15000L, "반바지.gif", "\"편안함, 시원함, 착용감 모두 우수한 일석삼조 밴딩 팬츠\"",
//					"상세설명", true, "원자재", "공급사", "제조사", "원산지",2L ,1L);
//			long[] inventorys3 = {28L, 30L, 32L, 34L};
//			List<ProductDetailVo> productDetailVoList3 = new ArrayList<>();
//			
//			for(int i=0; i<inventorys3.length; i++) {
//				productDetailVoList3.add(new ProductDetailVo(optionValList2.get(i), inventorys3[i]));
//			}
//			
//			Map<String, Object> map3 = new HashMap<>();
//			map3.put("product", productVo3);
//			map3.put("productDetailList", productDetailVoList3);
//			
//			resultActions = mockMvc.perform(post(SHOPADMINURL+"/list")
//			.contentType(MediaType.APPLICATION_JSON)
//			.content(new Gson().toJson(map3))
//			.characterEncoding("utf-8"));
//			
//			ProductVo productVo4 = new ProductVo(4L,"\"소프트 밴딩 스판 슬랙스\"", 34000L, "슬랙스.jpg", "\"편안한 밴딩 디테일로\r\n" + 
//					"멋스럽고 깔끔하게 착용할 수 있는 밴딩 슬랙스\"",
//					"상세설명", true, "원자재", "공급사", "제조사", "원산지",2L ,2L);
//			long[] inventorys4 = {28L, 30L, 32L, 34L};
//			List<ProductDetailVo> productDetailVoList4 = new ArrayList<>();
//			
//			for(int i=0; i<inventorys4.length; i++) {
//				productDetailVoList4.add(new ProductDetailVo(optionValList2.get(i), inventorys4[i]));
//			}
//			
//			Map<String, Object> map4 = new HashMap<>();
//			map4.put("product", productVo4);
//			map4.put("productDetailList", productDetailVoList4);
//			
//			resultActions = mockMvc.perform(post(SHOPADMINURL+"/list")
//			.contentType(MediaType.APPLICATION_JSON)
//			.content(new Gson().toJson(map4))
//			.characterEncoding("utf-8"));
//			
//			resultActions
//			.andExpect(status().isCreated())
//			.andDo(print());
//			
//			
//		}
		
		
	//#3. 관리자 상품목록 조회
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
//	
//	
//	//카테고리별 상품 조회
//	@Test
//	public void testC_1() throws Exception{
//		System.out.println("테스트");
//		
//		ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/list/{no}",1L));
//		
//		resultActions
//		.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("success")));
//		
//	}
//	
//	//서브카테고리별 상품 조회
//	@Test
//	public void testC_2() throws Exception{
//		System.out.println("서브카테고리별 상품 조회");
//		
//		ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/list/{no}/{sub_no}",1L,1L));
//		
//		resultActions
//		.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("success")));
//		
//	}
		
		//#4. 관리자 상품 조회
		// case1. 성공케이스 
//		@Test
//		public void testD() throws Exception{
//			System.out.println("관리자 상품 조회 테스트");
//			
//			ResultActions resultActions = mockMvc.perform(get(SHOPADMINURL+"/list/{no}",1L));
//			
//			resultActions
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.result", is("success")))
//			.andDo(print());
//		}
//		
//		@Test
//		public void testD_1() throws Exception{
//			System.out.println("유저 상품 조회 테스트");
//			
//			ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/{no}",1L));
//			
//			resultActions
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.result", is("success")))
//			.andDo(print());
//		}
//		//#5 관리사 상품 수정
//		//case1. 성공케이스
////		@Test
//		public void testE_1() throws Exception{
//			System.out.println("관리자 상품 수정 테스트");
//			long no = 1L;
//			ProductVo productVo = new ProductVo(no,"립넥 소프트 반팔티", 50000L, "imageURL", "\"댄디한 느낌 물씬, 립넥 포인트의 유니크한 반팔티\"", "상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,1L);
//			long[] inventorys = {100L, 90L, 95L, 77L};
//			String[] options = {"검정95", "회색105", "빨강110", "초록100"};
//			List<ProductDetailVo> productDetailVoList = new ArrayList<>();
//			
//			
//			for(int i=0; i<inventorys.length; i++) {
//				optionValList.set(i, options[i]);
//				productDetailVoList.add(new ProductDetailVo(i+1, no, optionValList.get(i), inventorys[i]));
//			}
//			Map<String, Object> map = new HashMap<>();
//			map.put("product", productVo);
//			map.put("productDetailList", productDetailVoList);
//			
//			System.out.println("requestJSON = " + new Gson().toJson(map));
//			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/list/{no}",no)
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(new Gson().toJson(map))
//					.characterEncoding("utf-8"));
//			
//			resultActions
//			.andDo(print())
//			.andExpect(status().isOk())
//			.andExpect(jsonPath("$.result", is("success")));
//			
//		}
//		//case2. 필수값을 넣지 않았을경우
//		//name = null 로 수정
//		@Test
//		public void testE_2() throws Exception{
//			long no = 1L;
//			ProductVo productVo = new ProductVo(no,null, 50000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,1L);
//			long[] inventorys = {100L, 90L, 95L, 77L};
//			String[] options = {"검정95", "회색105", "빨강110", "초록100"};
//			List<ProductDetailVo> productDetailVoList = new ArrayList<>();
//			
//			
//			for(int i=0; i<inventorys.length; i++) {
//				optionValList.set(i, options[i]);
//				productDetailVoList.add(new ProductDetailVo(i+1, no, optionValList.get(i), inventorys[i]));
//			}
//			Map<String, Object> map = new HashMap<>();
//			map.put("product", productVo);
//			map.put("productDetailList", productDetailVoList);
//			
//			
//			ResultActions resultActions = mockMvc.perform(put(SHOPADMINURL+"/list/{no}",no)
//					.contentType(MediaType.APPLICATION_JSON)
//					.content(new Gson().toJson(map))
//					.characterEncoding("utf-8"));
//			
//			resultActions
//			.andDo(print())
//			.andExpect(status().isBadRequest())
//			.andExpect(jsonPath("$.result", is("fail")));
//			
//		}
		
//		//#6 특정 상품 옵션 삭제
//		//case1. 성공 케이스
//		@Test
//		public void testF_1() throws Exception{
//			// 상품번호가 1인 상품의 옵션중 2번째 옵션을 삭제한다.
//			long no = 1L;
//			long p_no = 2L;
//			
//			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/list/{no}/{p_no}", no , p_no)
//					.characterEncoding("utf-8"));
//			
//			resultActions
//			.andDo(print())
//			.andExpect(status().isOk());
//		}
//		
//		//case2. 실패 케이스 : 없는 옵션삭제를 요청
//		@Test
//		public void testF_2() throws Exception{
//			// 상품번호가 1인 상품의 옵션중 5번째 옵션을 삭제한다.
//			// 5번째 옵션은 없는 옵션이다.
//			long no = 1L;
//			long p_no = 5L;
//			
//			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/list/{no}/{p_no}", no , p_no)
//					.characterEncoding("utf-8"));
//			
//			resultActions
//			.andDo(print())
//			.andExpect(status().isBadRequest());
//		}
//		
//	// #7 상품삭제
//	// case1. 성공 케이스
//	@Test
//	public void testF_3() throws Exception {
//		long no = 1L;
//		long no2 = 2L;
//		long no3 = 3L;
//		long no4 = 4L;
//		
//		ResultActions resultActions = mockMvc
//				.perform(delete(SHOPADMINURL + "/list/{no}", no).characterEncoding("utf-8"));
//		
//		mockMvc
//		.perform(delete(SHOPADMINURL + "/list/{no}", no2).characterEncoding("utf-8"));
//		
//		mockMvc
//		.perform(delete(SHOPADMINURL + "/list/{no}", no3).characterEncoding("utf-8"));
//		
//		mockMvc
//		.perform(delete(SHOPADMINURL + "/list/{no}", no4).characterEncoding("utf-8"));
//		
//
//		resultActions.andDo(print()).andExpect(status().isOk());
//
//	}
//
//	// 하위 카테고리 삭제
//		//case1. 성공 케이스
//		@Test
//		public void testF_4() throws Exception{
//			System.out.println("하위 카테고리 삭제");
//			long main_no = 1L; //main_no = 1 이고
//			long sub_no = 1L; //sub_no = 1 인 카테고리 삭제
//			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/category/list/{no}/{s_no}",main_no,sub_no)
//					.characterEncoding("utf-8"));
//			
//			resultActions
//			.andDo(print())
//			.andExpect(status().isOk());
//		}
//		
//		// 메인 카테고리 삭제 
//		@Test
//		public void testF_5() throws Exception{
//			System.out.println("상위 카테고리 삭제");
//			long main_no = 2L; // main_no = 2 에 속하는 모든 카테고리 삭제
//			
//			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/category/list/{no}",main_no)
//					.characterEncoding("utf-8"));
//			
//			resultActions
//			.andDo(print())
//			.andExpect(status().isOk());
//			
//			CategoryVo categoryVo = new CategoryVo();
//			categoryVo.setMain_no(1L);
//			shopService.deleteMainCategory(categoryVo);
//		}
//		
		
		
		
		
		
		
		@AfterClass
		public static void endTest() {
			System.out.println("Test End!!");
		}

}
