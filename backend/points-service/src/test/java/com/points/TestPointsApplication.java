package com.points;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestPointsApplication {

	public static void main(String[] args) {
		SpringApplication.from(PointsApplication::main).with(TestPointsApplication.class).run(args);
	}

}
