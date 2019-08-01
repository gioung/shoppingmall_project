package com.cafe24.shoppingmall.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.cafe24.shoppingmall.enums.Gender;
import com.cafe24.shoppingmall.repository.vo.CartVo;
import com.cafe24.shoppingmall.repository.vo.CategoryVo;
import com.cafe24.shoppingmall.repository.vo.MemberVo;
import com.cafe24.shoppingmall.repository.vo.OptionVo;
import com.cafe24.shoppingmall.repository.vo.OrderVo;
import com.cafe24.shoppingmall.repository.vo.OrderedProductVo;
import com.cafe24.shoppingmall.repository.vo.ProductDetailVo;
import com.cafe24.shoppingmall.repository.vo.ProductVo;
import com.cafe24.shoppingmall.service.CartService;
import com.cafe24.shoppingmall.service.OrderService;
import com.cafe24.shoppingmall.service.ShopService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderControllerTest {
	private MockMvc mockMvc;
	
	private static final String ORDERURL = "/api/order";
	private static final String SHOPADMINURL = "/api/admin/product";
	private static final String CARTURL = "/api/cart";
	private static final String OPTIONURL = "/api/option";
	private static final String SHAREDURL = "/api/user";
	@Autowired
	OrderService orderService;
	@Autowired 
	CartService cartService;
	@Autowired
	ShopService shopService;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	private static List<String> optionValList = new ArrayList<>();
	
	@Before
	public void setup() { 
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@BeforeClass
	public static void startTest() {
		System.out.println("Test Start!!");
		
	}
	
	// #. 장바구니 담기
	// # 사전작업 - 카테고리 등록, 상품 등록, 옵션등록
	// 옵션리스트 생성 테스트
	@Test
	public void TestA0() throws Exception {
		System.out.println("옵션리스트 생성 테스트");
		// 옵션1
		OptionVo optionVo1 = new OptionVo();
		optionVo1.setOpt_name("색상");
		List<String> valueList1 = new ArrayList<>();
		valueList1.add("레드");
		valueList1.add("블루");
		optionVo1.setOpt_val(valueList1);

		// 옵션2
		OptionVo optionVo2 = new OptionVo();
		optionVo2.setOpt_name("사이즈");
		List<String> valueList2 = new ArrayList<>();
		valueList2.add("100");
		valueList2.add("105");
		optionVo2.setOpt_val(valueList2);

		Map<String, Object> map = new HashMap<>();
		map.put("option1", optionVo1);
		map.put("option2", optionVo2);

		// 옵션 값 리스트 생성
		for (int i = 0; i < valueList1.size(); i++) {
			for (int j = 0; j < valueList2.size(); j++) {
				optionValList.add(valueList1.get(i).concat(valueList2.get(j)));
			}
		}
		System.out.println("optionValList = " + optionValList);

		mockMvc.perform(post(OPTIONURL + "/list").contentType(MediaType.APPLICATION_JSON).content(new Gson().toJson(map))
				.characterEncoding("utf-8"));

	}
	
	//회원 가입
	@Test
	public void TestA00() throws Exception{
		System.out.println("회원가입");
		 MemberVo vo = new MemberVo(); 
		  vo.setEmail("ska2253@naver.com");
		  vo.setName("남기웅");
		  vo.setPassword("gioung1234!");
		  vo.setBirth("1993-12-22");
		  vo.setGender(Gender.M);
		  vo.setPhone_num("010-9958-9833");
		  
		  mockMvc.perform(post(SHAREDURL+"/signup").contentType(MediaType.APPLICATION_JSON)
					 .content(new Gson().toJson(vo)).characterEncoding("utf-8")); 
	}
	// # 카테고리 등록
	@Test
	public void TestA01() throws Exception {
		System.out.println("카테고리 등록");
		// 메인 카테고리 1
		CategoryVo categoryVo1 = new CategoryVo(1L, "TOP");
		CategoryVo categoryVo2 = new CategoryVo(2L, "PANTS");
		// 서브 카테고리 1-1
		CategoryVo subCategoryVo1 = new CategoryVo(1L, 1L, "반팔티");
		// 서브 카테고리 1-2
		CategoryVo subCategoryVo2 = new CategoryVo(1L, 2L, "나시");
		// 서브 카테고리 2-1
		CategoryVo subCategoryVo3 = new CategoryVo(2L, 1L, "반바지");
		// 서브 카테고리 2-2
		CategoryVo subCategoryVo4 = new CategoryVo(2L, 2L, "슬렉스");

		List<CategoryVo> categoryList = new ArrayList<>();
		categoryList.add(categoryVo1);
		categoryList.add(categoryVo2);
		categoryList.add(subCategoryVo1);
		categoryList.add(subCategoryVo2);
		categoryList.add(subCategoryVo3);
		categoryList.add(subCategoryVo4);

		mockMvc.perform(post(SHOPADMINURL + "/category").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(categoryList)).characterEncoding("utf-8"));

	}

	// # 상품등록
	@Test
	public void TestA02() throws Exception {
		System.out.println("상품 등록");
		ProductVo productVo = new ProductVo(1L, "티셔츠", 42000L, "imageURL", "요약설명", "상세설명", true, "원자재", "공급사", "제조사",
				"원산지", 1L, 1L);
		long[] inventorys = { 100L, 90L, 95L, 77L };
		List<ProductDetailVo> productDetailVoList = new ArrayList<>();

		for (int i = 0; i < inventorys.length; i++) {
			productDetailVoList.add(new ProductDetailVo(optionValList.get(i), inventorys[i]));
		}

		Map<String, Object> map = new HashMap<>();
		map.put("product", productVo);
		map.put("productDetailList", productDetailVoList);

		mockMvc.perform(post(SHOPADMINURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(map)).characterEncoding("utf-8"));

	}
	
	
	//# 장바구니 담기 비회원일 경우
	@Test
	public void TestA03() throws Exception {
		System.out.println("비회원 장바구니 담기");
		CartVo cartVo = new CartVo(3L, 1L, 1L, false);

		ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

	}
	
	// # 주문하기(비회원) + 상품페이지에서 주문 
	@Test
	public void TestA04() throws Exception{
		System.out.println("비회원 주문하기");
		long order_no = 1L;
		List<OrderedProductVo> orderList = new ArrayList<>();
		//상품 주문
		orderList.add(new OrderedProductVo(order_no, 1L, 1L, 4L,42000L*4L));
		//주문자와 받는자가 같으므로 두번째 , 세번째는 null값
		OrderVo orderVo = new OrderVo(order_no, null, null, "서울", "남기웅", 
				"010-1234-5678", true, false, null);
		orderVo.setIsmember(false);
		orderVo.setOrderList(orderList);
		
		ResultActions resultActions = mockMvc.perform(post(ORDERURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo))
				.characterEncoding("utf-8"));
				
				resultActions
				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.result", is("success")));	
	}
	
	// # 주문하기(회원) + 회원 추가정보 insert
	@Test
	public void TestA05() throws Exception{
		System.out.println("회원 주문하기");
		long order_no = 2L;
		String id = "ska2253@naver.com";
		List<OrderedProductVo> orderList = new ArrayList<>();
		//상품 주문
		orderList.add(new OrderedProductVo(order_no, 1L, 1L, 4L,42000L*4L));
		//주문자와 받는자가 같으므로 두번째 , 세번째는 null값
		OrderVo orderVo = new OrderVo(order_no, null, null, "서울", "남기웅", 
				"010-1234-5678", true, false, id);
		//회원임을 표시
		orderVo.setIsmember(true);
		orderVo.setOrderList(orderList);
		
		ResultActions resultActions = mockMvc.perform(post(ORDERURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo))
				.characterEncoding("utf-8"));
		
		resultActions
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.result", is("success")));
	}
	
	// # 주문자와 받는자가 다를 경우
	@Test
	public void TestA06() throws Exception {
		System.out.println("주문자와 받는자가 다를 경우");
		long order_no = 3L;
		//회원 id
		
		String id = "ska2253@naver.com";
		List<OrderedProductVo> orderList = new ArrayList<>();
		// 상품 주문
		orderList.add(new OrderedProductVo(order_no, 1L, 1L, 4L, 42000L * 4L));
		// 주문자와 받는자가 다르다. 두번재는 받는자의 이름, 세번째는 받는자의 전화번호
		OrderVo orderVo = new OrderVo(order_no, "문상수", "111-1111-1111", "서울", "남기웅", "010-1234-5678", false, false, id);
		// 회원임을 표시
		orderVo.setIsmember(true);
		orderVo.setOrderList(orderList);

		ResultActions resultActions = mockMvc.perform(post(ORDERURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));
	}
	
	// # 장바구니 담기(2개이상)
		@Test
		public void TestA07() throws Exception {

			System.out.println("장바구니 담기 ");
			String id = "ska2253@naver.com";
			CartVo cartVo = new CartVo(3L, 3L, 1L, true);
			cartVo.setId(id);

			ResultActions resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(cartVo)).characterEncoding("utf-8"));

			resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));
			
			CartVo cartVo2 = new CartVo(3L, 4L, 1L, true);
			cartVo2.setId(id);
			resultActions = mockMvc.perform(post(CARTURL + "/list").contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(cartVo2)).characterEncoding("utf-8"));
			
			resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));

		}
		
	// # 장바구니로 주문할 경우 장바구니 삭제
	@Test
	public void TestA08() throws Exception {
		System.out.println("장바구니로 주문시 장바구니 삭제");
		long order_no = 5L;
		String id = "ska2253@naver.com";
		OrderVo orderVo = new OrderVo(order_no, "문상수", "111-1111-1111", "서울", "남기웅", "010-1234-5678", true, id);
		// 회원임을 표시
		orderVo.setIsmember(true);
		// 장바구니 사용 표시
		orderVo.setIscart(true);

		ResultActions resultActions = mockMvc.perform(post(ORDERURL + "/list").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo)).characterEncoding("utf-8"));

		resultActions.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.result", is("success")));
	}
	
	// # 재고보다 주문 수량이 많을 경우
	@Test
	public void TestA09() throws Exception {
		System.out.println("재고보다 주문 수량이 많을 경우");
		long order_no = 6L;
		String id = "ska2253@naver.com";
		List<OrderedProductVo> orderList = new ArrayList<>();
		//상품 주문시 상품번호가 1이고 상품 디테일번호가 2인 상품을 900개 (재고 초과)주문 한다고 가정
		orderList.add(new OrderedProductVo(order_no, 1L, 2L, 900L,42000L*4L));
		//주문자와 받는자가 같으므로 두번째 , 세번째는 null값
		OrderVo orderVo = new OrderVo(order_no, null, null, "서울", "남기웅", 
				"010-1234-5678", true, false, id);
		//회원임을 표시
		orderVo.setIsmember(true);
		orderVo.setOrderList(orderList);
		
		ResultActions resultActions = mockMvc.perform(post(ORDERURL+"/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(orderVo))
				.characterEncoding("utf-8"));
		//fail이 떠야된다.
		resultActions
		.andDo(print())
		.andExpect(status().isBadRequest()) 
		.andExpect(jsonPath("$.result", is("fail")));
	}
	
	// # 주문내역 조회
		@Test
		public void TestB0() throws Exception{
			System.out.println("주문내역 조회");
			//ska2253@naver.com 의 주문내역 조회
			String id = "ska2253@naver.com";
			
			ResultActions resultActions = mockMvc.perform(get(ORDERURL+"/list")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(id))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
		
	// # 주문내역 상세 조회
		@Test
		public void TestB1() throws Exception{
			System.out.println("주문내역 상세 조회");
			String id = "ska2253@naver.com";
			long order_no = 2L;
			
			OrderVo orderVo = new OrderVo();
			orderVo.setId(id);
			
			ResultActions resultActions = mockMvc.perform(get(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
	
	// # 주문 취소
		@Test
		public void TestC00() throws Exception{
			System.out.println("주문 취소(비회원)");
			//비회원 주문 취소
			String id=cartService.getTempId();
			System.out.println("id = "+id);
			long order_no = 1L;
			
			OrderVo orderVo = new OrderVo();
			orderVo.setId(id);
			
			ResultActions resultActions = mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
					
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
			
		}
		
		@Test
		public void TestC01() throws Exception{
			System.out.println("주문 취소(회원)");
			//회원 주문 취소
			String id="ska2253@naver.com";
			long order_no = 2L;
			
			OrderVo orderVo = new OrderVo();
			orderVo.setId(id);
			
			ResultActions resultActions = mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
					
			resultActions
			.andDo(print())
			.andExpect(status().isOk());
			
			order_no = 3L;
			mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
			order_no = 4L;
			mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
			order_no = 5L;
			mockMvc.perform(delete(ORDERURL+"/list/{no}",order_no)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(orderVo))
					.characterEncoding("utf-8"));
			
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
			
			mockMvc.perform(delete(CARTURL+"/list/{seq_no}",1L)
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(cartVo))
					.characterEncoding("utf-8"));
		}
			
		//# 비회원 장바구니 삭제
		@Test
		public void TestF_1() throws Exception{
			System.out.println("비회원 장바구니 삭제");
			String id=cartService.getTempId();
			
			ResultActions resultActions = mockMvc.perform(delete(CARTURL+"/list")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(id))
					.characterEncoding("utf-8"));
			
			resultActions.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.result", is("success")));
		}
		
		//# 회원 장바구니 삭제
		@Test
		public void TestF_2() throws Exception{
			System.out.println("회원 장바구니 삭제");
			String id="ska2253@naver.com";
			
			mockMvc.perform(delete(CARTURL+"/list")
					.contentType(MediaType.APPLICATION_JSON)
					.content(new Gson().toJson(id))
					.characterEncoding("utf-8"));
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
		
	// ska2253@naver.com 삭제
	@Test
	public void TestZ() throws Exception {
		MemberVo memberVo = new MemberVo();
		memberVo.setEmail("ska2253@naver.com");
		memberVo.setPassword("gioung1234!");

		mockMvc.perform(delete(SHAREDURL + "/out").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(memberVo)).characterEncoding("utf-8"));
	}

	
	@AfterClass
	public static void endTest() {
		System.out.println("Test End!!");
	}
	
	
	
}
