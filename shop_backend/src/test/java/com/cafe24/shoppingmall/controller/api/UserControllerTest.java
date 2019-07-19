package com.cafe24.shoppingmall.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.cafe24.shoppingmall.enums.Gender;
import com.cafe24.shoppingmall.service.UserService;
import com.cafe24.shppingmall.repository.vo.MemberVo;
import com.google.gson.Gson;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { TestAppConfig.class, TestWebConfig.class })
//@WebAppConfiguration
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTest {
	private MockMvc mockMvc;
	private static final String SHAREDURL = "/api/user";
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	private UserService userService;
	 // @Autowired private LocalValidatorFactoryBean validator;
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	@BeforeClass
	public static void startTest() {
		System.out.println("Test Start!!");
	}
	
	
	
	/* #1.회원가입 테스트  */
	// case1. 성공 케이스
	  @Test
	  public void TestA_1() throws Exception { 
		  System.out.println("회원가입 테스트 시작 ");
		  MemberVo vo = new MemberVo(); 
		  vo.setEmail("gioung9833@gmail.com");
		  vo.setName("남기웅");
		  vo.setPassword("namgioung123!");
		  vo.setBirth("1993-12-22");
		  vo.setGender(Gender.M);
		  vo.setPhone_num("010-9958-9833");
		  
		  ResultActions resultActions =
		  mockMvc.perform(post(SHAREDURL+"/signup").contentType(MediaType.APPLICATION_JSON)
				 .content(new Gson().toJson(vo)).characterEncoding("utf-8")); 
		  resultActions.andExpect(status().isCreated()) 
		  .andExpect(jsonPath("$.result",is("success"))) 
		  .andDo(print());
	  }
	// case2. 아이디가 이메일이 아닌경우
	  @Test
	  public void TestA_2() throws Exception { 
		  MemberVo vo = new MemberVo(); 
		  vo.setEmail("gioung9833gmail.com"); // ######### 변경부분 ##########
		  vo.setName("남기웅");
		  vo.setPassword("namgioung123!");
		  vo.setBirth("1993-12-22");
		  vo.setGender(Gender.M);
		  vo.setPhone_num("010-9958-9833");
		  System.out.println(vo);
		  
		  ResultActions resultActions =
		  mockMvc.perform(post(SHAREDURL+"/signup").contentType(MediaType.APPLICATION_JSON)
				 .content(new Gson().toJson(vo)).characterEncoding("utf-8")); 
		  resultActions.andExpect(status().isBadRequest()) 
		  .andExpect(jsonPath("$.result",is("fail"))) 
		  .andDo(print());
	  }
		
	// case3. 아이디가 존재하지 않는지 테스트
	  @Test
	  public void TestA_3() throws Exception {
			//ska2253@naver.com 은 DB에 있는 데이터라 가정.
			System.out.println("이메일 체크 테스트");
			String email = "ska2253@naver.com";
			ResultActions resultActions = mockMvc.perform(get(SHAREDURL+"/checkemail")
					.param("email", email).contentType(MediaType.TEXT_PLAIN).characterEncoding("utf-8"));
			resultActions.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.result", is("fail")));
	  }
		
	  // case4. 패스워드 길이가 4~16인지 테스트
	  @Test
	  public void TestA_4() throws Exception {
		  MemberVo vo = new MemberVo(); 
		  vo.setEmail("gioung9833@gmail.com");
		  vo.setName("남기웅");
		  vo.setPassword("n1fwfwrergggqqqqwefdfferskkk223!");
		  vo.setBirth("1993-12-22");
		  vo.setGender(Gender.M);
		  vo.setPhone_num("010-9958-9833");
		  System.out.println(vo);
		  
		  ResultActions resultActions =
		  mockMvc.perform(post(SHAREDURL+"/signup").contentType(MediaType.APPLICATION_JSON)
				 .content(new Gson().toJson(vo)).characterEncoding("utf-8")); 
		  resultActions.andExpect(status().isBadRequest()) 
		  .andExpect(jsonPath("$.result",is("fail"))) 
		  .andDo(print());
	  }
		
	  // case5. 패스워드가 영문자+숫자+특수문자 조합인지 테스트
	  @Test
	  public void TestA_5() throws Exception {
		  MemberVo vo = new MemberVo(); 
		  vo.setEmail("gioung9833@gmail.com");
		  vo.setName("남기웅");
		  vo.setPassword("namgioung");
		  vo.setBirth("1993-12-22");
		  vo.setGender(Gender.M);
		  vo.setPhone_num("010-9958-9833");
		  System.out.println(vo);
		  
		  ResultActions resultActions =
		  mockMvc.perform(post(SHAREDURL+"/signup").contentType(MediaType.APPLICATION_JSON)
				 .content(new Gson().toJson(vo)).characterEncoding("utf-8")); 
		  resultActions.andExpect(status().isBadRequest()) 
		  .andExpect(jsonPath("$.result",is("fail"))) 
		  .andDo(print());
	  }
	  // case6. 필요한 데이터가 들어갔는지 테스트
	  @Test
	  public void TestA_6() throws Exception { 
		  MemberVo vo = new MemberVo(); 
		  vo.setEmail(null);
		  vo.setName("남기웅");
		  vo.setPassword("namgioung123!");
		  vo.setBirth(null);
		  vo.setGender(Gender.M);
		  vo.setPhone_num("010-9958-9833");
		  System.out.println(vo);
		  
		  ResultActions resultActions =
		  mockMvc.perform(post(SHAREDURL+"/signup").contentType(MediaType.APPLICATION_JSON)
				 .content(new Gson().toJson(vo)).characterEncoding("utf-8")); 
		  resultActions.andExpect(status().isBadRequest()) 
		  .andExpect(jsonPath("$.result",is("fail"))) 
		  .andDo(print());
	  }

	/* #2.로그인 테스트 */
	  // case1. 성공케이스
	  @Test 
	  public void TestB_1() throws Exception { 
	  System.out.println("로그인 테스트");
	  MemberVo memberVo = new MemberVo();
	  String email = "gioung9833@gmail.com"; 
	  String password = "namgioung123!";
	  memberVo.setEmail(email);
	  memberVo.setPassword(password);
	  
	  ResultActions resultActions =
	  mockMvc.perform(post(SHAREDURL+"/login")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(new Gson().toJson(memberVo))
	  .characterEncoding("utf-8"));
	  
	  resultActions.andDo(print())
	  .andExpect(status().isOk()) 
	  .andExpect(jsonPath("$.result",is("success"))); 
	  }
	  
	  // case2. 아이디가 존재하지 않거나 아이디, 패스워드가 일치하지 않는경우
	  @Test 
	  public void TestB_2() throws Exception { 
	  MemberVo memberVo = new MemberVo();
	  String email = "fkegme@gmail.com"; 
	  String password = "namgioung1234!";
	  memberVo.setEmail(email);
	  memberVo.setPassword(password);
	  
	  ResultActions resultActions =
	  mockMvc.perform(post(SHAREDURL+"/login")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(new Gson().toJson(memberVo))
	  .characterEncoding("utf-8"));
	  
	  resultActions.andDo(print())
	  .andExpect(status().isOk()) 
	  .andExpect(jsonPath("$.result",is("fail"))); 
	  }
	  
	  // case3. 아이디 or 비밀번호 형식이 올바르지 않을 경우
	  @Test 
	  public void TestB_3() throws Exception { 
	  MemberVo memberVo = new MemberVo();
	  String email = "namgioung"; 
	  String password = "123456789"; //비밀번호는 영어 + 숫자 + 문자 조합
	  memberVo.setEmail(email);
	  memberVo.setPassword(password);
	  
	  ResultActions resultActions =
	  mockMvc.perform(post(SHAREDURL+"/login")
	  .contentType(MediaType.APPLICATION_JSON)
	  .content(new Gson().toJson(memberVo))
	  .characterEncoding("utf-8"));
	  
	  resultActions.andDo(print())
	  .andExpect(status().isBadRequest()) 
	  .andExpect(jsonPath("$.result",is("fail"))); 
	  }
	
	  @Test
	  public void TestZ() {
		//아이디 삭제
		  String email = "gioung9833@gmail.com";
		  userService.deleteMember(email);
	  }
	  
	  
	  @AfterClass 
		public static void endTest(){
			System.out.println("Test End!");
		  }
	  
	  //#4.회원정보 수정 테스트
	  
//	  @Test public void TestD() throws Exception {
//	  System.out.println("회원정보 수정 테스트"); String email = "gioung9833@gmail.com";
//	  String password = "9876";
//	  
//	  ResultActions resultActions = mockMvc.perform(put(SHAREDURL+"//info")
//	  .contentType(MediaType.APPLICATION_JSON).param("email",
//	  email).param("password", password));
//	  resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.result",
//	  is("success"))).andDo(print()); }
//	  
	  
//	  
//	  //#5.정보수정 후 로그인 테스트
//	  
//	  @Test public void TestE() throws Exception {
//	  System.out.println("수정 후 로그인 테스트"); String email = "gioung9833@gmail.com";
//	  String password = "9876";
//	  
//	  ResultActions resultActions =
//	  mockMvc.perform(post(SHAREDURL+"/login").contentType(MediaType.
//	  APPLICATION_JSON) .param("email", email).param("password", password));
//	  resultActions.andExpect(status().isOk()).andExpect(jsonPath("$.result",
//	  is("success"))).andDo(print()); }
//	  
//	  //#6.회원 탈퇴 테스트
//	  
//	  @Test public void TestF() throws Exception { System.out.println("회원 탈퇴 테스트");
//	  String email = "gioung9833@gmail.com";
//	  
//	  ResultActions resultActions =
//	  mockMvc.perform(delete(SHAREDURL+"/out").contentType(MediaType.
//	  APPLICATION_JSON) .param("email", email));
//	  resultActions.andExpect(status().isOk()) .andExpect(jsonPath("$.result",
//	  is("success"))) .andDo(print()); }
//	  
//	  //#7.회원 탈퇴 후 이메일 체크
//	  
//	  @Test public void TestG() throws Exception {
//	  System.out.println("탈퇴 후 이메일 체크 테스트"); String email = "gioung9833@gmail.com";
//	  ResultActions resultActions =
//	  mockMvc.perform(get(SHAREDURL+"/checkemail").param("email",
//	  email).contentType(MediaType.APPLICATION_JSON));
//	  resultActions.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath(
//	  "$.result", is("success"))); //ska2253@naver.com 은 DB에 있는 데이터라 가정.
//	  
//	  }
//	 
}