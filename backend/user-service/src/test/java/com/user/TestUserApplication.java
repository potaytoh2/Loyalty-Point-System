package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestUserApplication {

	public static void main(String[] args) {
		SpringApplication.from(UserApplication::main).with(TestUserApplication.class).run(args);
	}

}
