package com.abinbev.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@Profile("test")
public class TestConfig {

	@Bean
	MongoTemplate mongoTemplate() {
		return Mockito.mock(MongoTemplate.class);
	}
}
