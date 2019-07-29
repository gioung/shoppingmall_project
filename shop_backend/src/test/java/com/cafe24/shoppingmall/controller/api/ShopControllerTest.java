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
			
			ResultActions resultAction = mockMvc.perform(get(OPTIONURL+"/list")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(map))
					.characterEncoding("utf-8"));
			
			resultAction.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")))
			.andDo(print());
			
			
		}
		//# 카테고리 등록 테스트
		@Test
		public void testB_0() throws Exception {
			System.out.println("카테고리 등록 테스트");
			// 메인 카테고리 1
			CategoryVo categoryVo1 = new CategoryVo(1L, "TOP");
			CategoryVo categoryVo2 = new CategoryVo(2L, "PANTS");
			//서브 카테고리 1-1
			CategoryVo subCategoryVo1 = new CategoryVo(1L, 1L, "반팔티");
			//서브 카테고리 1-2
			CategoryVo subCategoryVo2 = new CategoryVo(1L, 2L, "나시");
			//서브 카테고리 2-1
			CategoryVo subCategoryVo3 = new CategoryVo(2L, 1L, "반바지");
			//서브 카테고리 2-2
			CategoryVo subCategoryVo4 = new CategoryVo(2L, 2L, "슬렉스");
			
			List<CategoryVo> categoryList = new ArrayList<>();
			categoryList.add(categoryVo1);
			categoryList.add(categoryVo2);
			categoryList.add(subCategoryVo1);
			categoryList.add(subCategoryVo2);
			categoryList.add(subCategoryVo3);
			categoryList.add(subCategoryVo4);
		
			
			ResultActions resultActions = mockMvc.perform(post(SHOPADMINURL+"/category")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(categoryList))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isCreated());
					
		}
		
		//# 카테고리 조회
		@Test
		public void testB_01() throws Exception{
			System.out.println("카테고리 전체 조회 테스트");
			//상품 등록 view에서 카테고리를 고르는데 쓰일 수 있다.
			ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/category/list"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		
					
		}
		//# 특정 카테고리 조회
		@Test
		public void testB_02() throws Exception{
			System.out.println("특정 카테고리 리스트(상위 - 하위,하위..)");
			//유저가 화면에서 상위옵션에 마우스 커서를 올렸을때 특정 상위옵션의 하위옵션들을 조회하여야 하는 경우
			ResultActions resultActions = mockMvc.perform(get(SHOPCOMMONURL+"/category/list/{no}",2L));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
			
		}
		
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
		
		//# 서브 카테고리 수정
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
		
		//#2. 상품등록 테스트
		@Test
		public void testB_1() throws Exception {
			System.out.println("상품등록 테스트");
			ProductVo productVo = new ProductVo(1L,"티셔츠", 42000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,1L);
			long[] inventorys = {100L, 90L, 95L, 77L};
			List<ProductDetailVo> productDetailVoList = new ArrayList<>();
			
			for(int i=0; i<inventorys.length; i++) {
				productDetailVoList.add(new ProductDetailVo(optionValList.get(i), inventorys[i]));
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("product", productVo);
			map.put("productDetailList", productDetailVoList);
			
			ResultActions resultActions = mockMvc.perform(post(SHOPADMINURL+"/list")
			.contentType(MediaType.APPLICATION_JSON)
			.content(new Gson().toJson(map))
			.characterEncoding("utf-8"));
			
		
			resultActions
			.andExpect(status().isCreated())
			.andDo(print());
		}
		
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
		
		//#5 관리사 상품 수정
		//case1. 성공케이스
		@Test
		public void testE_1() throws Exception{
			System.out.println("관리자 상품 수정 테스트");
			long no = 1L;
			ProductVo productVo = new ProductVo(no,"청바지", 50000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,1L);
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
		//case2. 필수값을 넣지 않았을경우
		//name = null 로 수정
		@Test
		public void testE_2() throws Exception{
			long no = 1L;
			ProductVo productVo = new ProductVo(no,null, 50000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,1L);
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
		
		// 하위 카테고리 삭제
		//case1. 성공 케이스
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
		
		//#7 상품삭제
		//case1. 성공 케이스
		@Test
		public void testG() throws Exception{
			long no = 1L;
			
			ResultActions resultActions = mockMvc.perform(delete(SHOPADMINURL+"/list/{no}",no)
					.characterEncoding("utf-8"));
			
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
			
		}
		
		
		
		
		
		@AfterClass
		public static void endTest() {
			System.out.println("Test End!!");
		}

}
