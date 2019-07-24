package com.cafe24.shoppingmall.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.shoppingmall.repository.vo.OptionVo;
import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;
import com.cafe24.shoppingmall.service.ShopService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopControllerTest {

	private MockMvc mockMvc;
	private static final String SHOPURL = "/api/shop";
	private static final String OPTIONURL = "/api/option";
	private static final String CARTURL = "/api/cart";
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
	
//	//shopService autowired Test
//	@Test
//	public void testShopService() {
//		assertNotNull(shopService);
//	}
//
	
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
		.andDo(print());
	}
	//#2. 특정상품 조회 테스트
//	@Test
//	public void testB() throws Exception{
//		System.out.println("특정상품 조회 테스트");
//		long productNo = 1L;
//		
//		ResultActions resultActions = mockMvc.perform(get(SHOPURL+"/list/{no}",productNo).contentType(MediaType.APPLICATION_JSON));
//		
//		resultActions
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("success")))
//		.andDo(print());
//		
//	}
//	
//	//#3. 특정상품 가격
//	@Test
//	public void testC() throws Exception{
//		System.out.println("특정상품 가격 테스트");
//		long productNo = 1L;
//		long quantity = 3L;
//		
//		ResultActions resultActions = mockMvc.perform(get(SHOPURL+"/list/price/{no}/{qty}", productNo, quantity)
//				.contentType(MediaType.APPLICATION_JSON));
//		resultActions
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("success")))
//		.andDo(print());
//		
//	}
//	
//	//#4. 장바구니 담기 테스트
//	@Test
//	public void testD() throws Exception{
//		System.out.println("장바구니 담기");
//	// 멤버아이디 , temp_id, seq_no, qty, product_no 데이터만 insert 
//		CartVo vo = new CartVo();
//		vo.setMember_id("gioung9833@gmail.com");
//		vo.setTemp_id("None");
//		vo.setSeq_no(1L);
//		vo.setQty(3L);
//		vo.setProduct_no(1L);
//		
//		ResultActions resultActions = mockMvc.perform(post(CARTURL).flashAttr("cartVo", vo)
//				.contentType(MediaType.APPLICATION_JSON));
//		resultActions
//		.andExpect(status().isCreated())
//		.andExpect(jsonPath("$.result", is("success")))
//		.andDo(print());
//		
//		
//	}
//	
//	// #5. 장바구니 목록 테스트
//	@Test
//	public void testE() throws Exception{
//		System.out.println("장바구니 목록 테스트");
//		String member_id = "gioung9833@gmail.com";
//		String temp_id = "None";
//		
//		ResultActions resultActions = mockMvc.perform(get(CARTURL+"/list")
//				.param("member_id", member_id)
//				.param("temp_id", temp_id)
//				.contentType(MediaType.APPLICATION_JSON));
//		
//		resultActions
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("success")))
//		.andDo(print());
//	}
//	
//	
//	// #6. 장바구니 삭제 테스트
//	@Test
//	public void testF() throws Exception{
//		System.out.println("장바구니 목록 삭제 테스트");
//		String member_id = "gioung9833@gmail.com";
//		String temp_id = "None";
//		long no = 1L;
//		
//		ResultActions resultActions = mockMvc.perform(delete(CARTURL+"/list/{no}",no)
//				.param("member_id", member_id)
//				.param("temp_id", temp_id)
//				.contentType(MediaType.APPLICATION_JSON));
//		resultActions
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("success")))
//		.andDo(print());
//		
//	}
//	
//	// #7. 장바구니 목록 확인 (비어 있어야됨)
//	@Test
//	public void testG() throws Exception{
//		System.out.println("삭제후 장바구니 목록 테스트");
//		String member_id = "gioung9833@gmail.com";
//		String temp_id = "None";
//		
//		ResultActions resultActions = mockMvc.perform(get(CARTURL+"/list")
//				.param("member_id", member_id)
//				.param("temp_id", temp_id)
//				.contentType(MediaType.APPLICATION_JSON));
//		
//		resultActions
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$.result", is("fail")))
//		.andDo(print());
//	}
	
	
	
	
	@AfterClass
	public static void endTest() {
		System.out.println("Test End!!");
	}
}
