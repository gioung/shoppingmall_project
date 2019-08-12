package com.cafe24.shoppingmall.controller.api;

import org.apache.commons.text.StringEscapeUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudyTest {
	
	@BeforeClass
	public static void startTest() {
		System.out.println("Test Start!!");
	}
	
//	@Test
//	public void testJsonReader() throws Exception{
//	       StringReader stringReader = new StringReader("\"ABC_DEF GHI\"");
//	        JsonReader jsonReader = new JsonReader(stringReader);
//	        final JsonParser jsonParser = new JsonParser();
//	        JsonElement parsed = jsonParser.parse(jsonReader);
//	        Assert.assertEquals("ABC_DEF GHI", parsed.getAsString());
//	}
	
	@Test
	public void FuckingGson() throws Exception{
		
		String s = StringEscapeUtils.escapeJava("안녕 하세요");
		System.out.println();
	}
	
	@AfterClass
	public static void endTest() {
		System.out.println("Test End!!");
	}
}
