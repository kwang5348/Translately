package com.kwang;

import java.util.ArrayList;
import java.util.List;

import com.kwang.jwt.interceptor.JwtInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class KwangApplication implements WebMvcConfigurer {

	@Autowired
	private JwtInterceptor jwtInterceptor;
	public static void main(final String[] args) {
		SpringApplication.run(KwangApplication.class, args);
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry){
		List<String> excludePatterns = new ArrayList<String>();
		excludePatterns.add("/api/account/login");
		excludePatterns.add("/api/account/join");
		excludePatterns.add("/api/mp4/download");
		excludePatterns.add("/api/vtt/download");
		excludePatterns.add("/api/jpg/download");
		
		registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**").excludePathPatterns(excludePatterns);
	}
	// @Override
	// public void addInterceptors(final InterceptorRegistry registry) {
	// 	registry.addInterceptor(jwtInterceptor).addPathPatterns("/api/**")
	// 	.excludePathPatterns("/api/account/login", "/api/account/join");
		
	// }
}
