package com.cafe24.shoppingmall.controller.api;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.cafe24.shoppingmall.service.OptionService;
import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OptionControllerTest {
	private MockMvc mockMvc;
	private static final String OPTIONURL = "/api/option";
	@Autowired
	private WebApplicationContext webApplicationContext;
	@Autowired
	OptionService optionService;
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@BeforeClass
	public static void startTest() {
		System.out.println("Test Start!!");
	}
	
	@AfterClass
	public static void endTest() {
		System.out.println("Test End!!");
	}
	
	//옵션등록 테스트
	@Test
	public void TestA() throws Exception{
		System.out.println("옵션등록 테스트");
		
		OptionVo optionVo = new OptionVo();
		optionVo.setOpt_name("사이즈");
		
		ResultActions resultActions = mockMvc.perform(post(OPTIONURL+"/list").contentType(MediaType.APPLICATION_JSON)
		.content(new Gson().toJson(optionVo))
		.characterEncoding("utf-8"));
		
		resultActions
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.result", is("success")))
		.andDo(print());
	}
}
