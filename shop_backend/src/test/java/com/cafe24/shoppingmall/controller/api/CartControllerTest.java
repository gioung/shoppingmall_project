package com.cafe24.shoppingmall.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.cafe24.shoppingmall.repository.vo.CartVo;
import com.cafe24.shoppingmall.repository.vo.CategoryVo;
import com.cafe24.shoppingmall.repository.vo.OptionVo;
import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;
import com.cafe24.shoppingmall.service.CartService;
import com.cafe24.shoppingmall.service.ShopService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CartControllerTest {
	private MockMvc mockMvc;
	
	private static final String SHOPADMINURL = "/api/admin/product";
	private static final String CARTURL = "/api/cart";
	private static final String OPTIONURL = "/api/option";
	
	@Autowired
	ShopService shopService;
	@Autowired
	CartService cartService;
	
	private static List<String> optionValList = new ArrayList<>();
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	
	@Before
	public void setup() { 
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@BeforeClass
	public static void startTest() {
		System.out.println("Test Start!!");
	}
	
	//#. 장바구니 담기
	//# 사전작업 - 카테고리 등록, 상품 등록, 옵션등록
	//옵션리스트 생성 테스트
			@Test
			public void TestA0() throws Exception{
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
				
				mockMvc.perform(get(OPTIONURL+"/list")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new Gson().toJson(map))
						.characterEncoding("utf-8"));
				
				
				
				
			}
	//# 카테고리 등록
			@Test
			public void TestA1() throws Exception {
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
			
				
				mockMvc.perform(post(SHOPADMINURL+"/category")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new Gson().toJson(categoryList))
						.characterEncoding("utf-8"));
				
			}
			//# 상품등록
			@Test
			public void TestA2() throws Exception {
				ProductVo productVo = new ProductVo(1L,"티셔츠", 42000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사", "원산지",1L ,1L);
				long[] inventorys = {100L, 90L, 95L, 77L};
				List<ProductDetailVo> productDetailVoList = new ArrayList<>();
				
				for(int i=0; i<inventorys.length; i++) {
					productDetailVoList.add(new ProductDetailVo(optionValList.get(i), inventorys[i]));
				}
				
				Map<String, Object> map = new HashMap<>();
				map.put("product", productVo);
				map.put("productDetailList", productDetailVoList);
				
				mockMvc.perform(post(SHOPADMINURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(map))
				.characterEncoding("utf-8"));
				
			
			}
	
	//# 장바구니 담기 비회원일 경우
	@Test
	public void TestA3() throws Exception {
		System.out.println("비회원 장바구니 담기");
		CartVo cartVo = new CartVo(3L, 1L, 1L, false);

		ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

	}
	
	//# 장바구니 아이디 수정
	@Test
	public void TestA3_1() throws Exception{
		System.out.println("비회원 회원으로 전환시 장바구니 id 변환 테스트");
		String id ="ska2253@naver.com"; // 해당 id로 회원가입
		CartVo cartVo = new CartVo();
		cartVo.setId(id); 
		cartVo.setIsmember(true);
		cartVo.setTempId(cartService.getTempId());
		
		ResultActions resultActions = mockMvc.perform(put(CARTURL + "/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo))
				.characterEncoding("utf-8"));
		
		resultActions.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.result", is("success")));
	}

	// # 장바구니 담기 회원일 경우
	@Test
	public void TestA4() throws Exception {

		System.out.println("회원 장바구니 담기 ");
		String id = "ska2253@naver.com";
		CartVo cartVo = new CartVo(3L, 3L, 1L, true);
		cartVo.setId(id);

		ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

	}
	
	// # 장바구니에 같은 상품을 담을 경우
	@Test
	public void TestA5() throws Exception {

		System.out.println("장바구니에 같은 상품을 담을 경우 ");
		String id = "ska2253@naver.com";
		CartVo cartVo = new CartVo(2L, 1L, 1L, true); //앞의 TEST에서 3개의 수량이 있었고 2개가 추가되므로 총 5개여야 한다. 
		cartVo.setId(id);

		ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

	}
	
	//# 장바구니 조회
	@Test
	public void TestB1() throws Exception {
		System.out.println("장바구니 조회");
		String id = "ska2253@naver.com";
		
		ResultActions resultActions = mockMvc.perform(get(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(id)).characterEncoding("utf-8"));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")));
	}
	
	//# 장바구니 수량 수정
	@Test
	public void TestB2() throws Exception {
		System.out.println("장바구니 수량 수정");
		String id = "ska2253@naver.com";
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		cartVo.setQty(10L); //수량을 10개로 변경
		cartVo.setIsmember(true);
	
		ResultActions resultActions = mockMvc.perform(put(CARTURL + "/list/{seq_no}",1L).contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success"))); 
	}
	
	//# 장바구니 상품 삭제
	@Test
	public void TestC1() throws Exception{
		//회원이 ska2253@naver.com 이고 seq_no = 1인 상품을 삭제
		System.out.println("장바구니 상품 삭제");
		String id="ska2253@naver.com";
		CartVo cartVo = new CartVo();
		cartVo.setId(id);
		cartVo.setIsmember(true);
		
		ResultActions resultActions = mockMvc.perform(delete(CARTURL+"/list/{seq_no}",1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo))
				.characterEncoding("utf-8"));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")));
	}
		
	//# 비회원 장바구니 삭제
//	@Test
//	public void TestF_1() throws Exception{
//		System.out.println("비회원 장바구니 삭제");
//		String id=cartService.getTempId();
//		
//		ResultActions resultActions = mockMvc.perform(delete(CARTURL+"/list")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(new Gson().toJson(id))
//				.characterEncoding("utf-8"));
//		
//		resultActions.andDo(print())
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("success")));
//	}
	
	//# 회원 장바구니 삭제
	@Test
	public void TestF_2() throws Exception{
		System.out.println("회원 장바구니 삭제");
		String id="ska2253@naver.com";
		
		ResultActions resultActions = mockMvc.perform(delete(CARTURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(id))
				.characterEncoding("utf-8"));
		
		resultActions.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")));
	}
	
	// 상품삭제
	@Test
	public void TestG_1() throws Exception{
		System.out.println("상품 삭제");
		long no = 1L;
		
		mockMvc.perform(delete(SHOPADMINURL+"/list/{no}",no)
				.characterEncoding("utf-8"));
		
	}

	// 하위 카테고리 삭제
	@Test
	public void TestG_2() throws Exception {
		System.out.println("하위 카테고리 삭제");
		long main_no = 1L; // main_no = 1 이고
		long sub_no = 1L; // sub_no = 1 인 카테고리 삭제
		mockMvc.perform(
				delete(SHOPADMINURL + "/category/list/{no}/{s_no}", main_no, sub_no).characterEncoding("utf-8"));
	}

	// 메인 카테고리 삭제
	@Test
	public void TestG_3() throws Exception {
		System.out.println("상위 카테고리 삭제");
		long main_no = 2L; // main_no = 2 에 속하는 모든 카테고리 삭제

		mockMvc.perform(delete(SHOPADMINURL + "/category/list/{no}", main_no).characterEncoding("utf-8"));

		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setMain_no(1L);
		shopService.deleteMainCategory(categoryVo);
	}

	@AfterClass
	public static void endTest() {
		System.out.println("Test End!!");
	}
}
