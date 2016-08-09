package com.aytov;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

@SpringBootApplication
public class ProductDemoApplication {

	@Bean
	public ObjectMapper objectMapper() {
		Jackson2ObjectMapperFactoryBean bean = new Jackson2ObjectMapperFactoryBean();
		bean.setIndentOutput(true);
		bean.setSimpleDateFormat("yyyy-MM-dd 'T' HH:mm:ss");
		bean.afterPropertiesSet();

		return bean.getObject();
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductDemoApplication.class, args);
	}
}
