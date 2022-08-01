package com.asw.test.app.app_test.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.develop.torres.demo")
public class AppTestConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
