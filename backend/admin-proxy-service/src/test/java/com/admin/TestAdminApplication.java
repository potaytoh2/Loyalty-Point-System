package com.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestAdminApplication {

	public static void main(String[] args) {
		SpringApplication.from(AdminApplication::main).with(TestAdminApplication.class).run(args);
	}

}
