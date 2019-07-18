package com.cafe24.shoppingmall.controller.api;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.shoppingmall.service.ShopService;
import com.cafe24.shppingmall.repository.vo.CartVo;
import com.cafe24.shppingmall.repository.vo.ProductVo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopControllerTest {

	private MockMvc mockMvc;
	private static final String SHOPURL = "/api/shop";
	private static final String CARTURL = "/api/cart";
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
	
	//shopService autowired Test
	@Test
	public void testShopService() {
		assertNotNull(shopService);
	}
	
	//#1. 상품등록 테스트
	@Test
	public void testA() throws Exception {
		System.out.println("상품등록 테스트");
		List<ProductVo> list = new ArrayList<>();
		// **1**
		ProductVo vo = new ProductVo(1L, "가죽자켓", 10000L);
		list.add(vo);
		// **2**
		ProductVo vo2 = new ProductVo(2L, "청자켓", 18000L);
		list.add(vo2);
		
		ResultActions resultActions = mockMvc.perform(post(SHOPURL+"/list").contentType(MediaType.APPLICATION_JSON)
		.flashAttr("productVoList", list));
		resultActions
		.andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
	}
	//#2. 특정상품 조회 테스트
	@Test
	public void testB() throws Exception{
		System.out.println("특정상품 조회 테스트");
		long productNo = 1L;
		
		ResultActions resultActions = mockMvc.perform(get(SHOPURL+"/list/{no}",productNo).contentType(MediaType.APPLICATION_JSON));
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
		
	}
	
	//#3. 특정상품 가격
	@Test
	public void testC() throws Exception{
		System.out.println("특정상품 가격 테스트");
		long productNo = 1L;
		long quantity = 3L;
		
		ResultActions resultActions = mockMvc.perform(get(SHOPURL+"/list/price/{no}/{qty}", productNo, quantity)
				.contentType(MediaType.APPLICATION_JSON));
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
		
	}
	
	//#4. 장바구니 담기 테스트
	@Test
	public void testD() throws Exception{
		System.out.println("장바구니 담기");
	// 멤버아이디 , temp_id, seq_no, qty, product_no 데이터만 insert 
		CartVo vo = new CartVo();
		vo.setMember_id("gioung9833@gmail.com");
		vo.setTemp_id("None");
		vo.setSeq_no(1L);
		vo.setQty(3L);
		vo.setProduct_no(1L);
		
		ResultActions resultActions = mockMvc.perform(post(CARTURL).flashAttr("cartVo", vo)
				.contentType(MediaType.APPLICATION_JSON));
		resultActions
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
		
		
	}
	
	// #5. 장바구니 목록 테스트
	@Test
	public void testE() throws Exception{
		System.out.println("장바구니 목록 테스트");
		String member_id = "gioung9833@gmail.com";
		String temp_id = "None";
		
		ResultActions resultActions = mockMvc.perform(get(CARTURL+"/list")
				.param("member_id", member_id)
				.param("temp_id", temp_id)
				.contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
	}
	
	
	// #6. 장바구니 삭제 테스트
	@Test
	public void testF() throws Exception{
		System.out.println("장바구니 목록 삭제 테스트");
		String member_id = "gioung9833@gmail.com";
		String temp_id = "None";
		long no = 1L;
		
		ResultActions resultActions = mockMvc.perform(delete(CARTURL+"/list/{no}",no)
				.param("member_id", member_id)
				.param("temp_id", temp_id)
				.contentType(MediaType.APPLICATION_JSON));
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
		
	}
	
	// #7. 장바구니 목록 확인 (비어 있어야됨)
	@Test
	public void testG() throws Exception{
		System.out.println("삭제후 장바구니 목록 테스트");
		String member_id = "gioung9833@gmail.com";
		String temp_id = "None";
		
		ResultActions resultActions = mockMvc.perform(get(CARTURL+"/list")
				.param("member_id", member_id)
				.param("temp_id", temp_id)
				.contentType(MediaType.APPLICATION_JSON));
		
		resultActions
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.result", is("fail")))
		.andDo(print());
	}
	
	
	
	
	@AfterClass
	public static void endTest() {
		System.out.println("Test End!!");
	}
}
