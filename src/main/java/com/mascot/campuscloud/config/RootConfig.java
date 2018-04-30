package com.mascot.campuscloud.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mascot.campuscloud.Marker4ComponentScan;

@Configuration
@ImportResource("classpath:beans.xml") // 持久层相关的Bean
@ComponentScan(basePackageClasses = { Marker4ComponentScan.class }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
public class RootConfig {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
