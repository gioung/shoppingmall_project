package com.cafe24.shoppingmall.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	//Message Converter
		@Bean
		public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
			Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
					.indentOutput(true)
					.dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
					.modulesToInstall(new ParameterNamesModule());
			
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(builder.build());
			converter.setSupportedMediaTypes(Arrays.asList(new MediaType("application","json",Charset.forName("utf-8"))));
			
			return converter;
		}
		
		@Bean
		public StringHttpMessageConverter stringHttpMessageConverter() {
			StringHttpMessageConverter converter = new StringHttpMessageConverter();
			converter.setSupportedMediaTypes(Arrays.asList(new MediaType("text","html",Charset.forName("utf-8"))));
			
			return converter;
		}
		

		 //
		 //Default Servlet Handler
		 //
		@Override
		public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
					configurer.enable();
		}
		

//		@Override
//		
//		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//				converters.add(mappingJackson2HttpMessageConverter());
//				converters.add(stringHttpMessageConverter());
//		}
//		
	
		
//		
//		@Override
//		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//			argumentResolvers.add(authUserHandlerMethodArgumentResolver());
//		}
//
//
//		@Override
//		public void addInterceptors(InterceptorRegistry registry) {
//			registry.addInterceptor(authLoginInterceotor()).addPathPatterns("/user/auth");
//			
//			registry.addInterceptor(authLogoutInterceotor()).addPathPatterns("/user/logout");
//			
//			registry
//	        .addInterceptor(authInterceptor())
//	        .addPathPatterns("**")
//	        .excludePathPatterns("/assets/**")
//	        .excludePathPatterns("/user/logout")
//	        .excludePathPatterns("/user/auth");
//			
//		}
//		@Bean
//		public AuthInterceptor authInterceptor() {
//			 return new AuthInterceptor();
//		}
//		@Bean
//		public AuthLoginInterceptor authLoginInterceotor() {
//			return new AuthLoginInterceptor();
//			
//		}
//		@Bean
//		public AuthLogoutInterceptor authLogoutInterceotor() {
//			return new AuthLogoutInterceptor();
//			
//		}
		
		@Override
		//가상 url mapping 해주는 놈
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/images/**").addResourceLocations("file:/mysite-uploads/");
			
		}
	
}