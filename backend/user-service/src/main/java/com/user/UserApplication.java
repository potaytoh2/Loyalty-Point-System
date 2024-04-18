package com.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(UserApplication.class, args);

		UserRepository userRepository = ctx.getBean(UserRepository.class);
		BCryptPasswordEncoder passwordEncoder = ctx.getBean(BCryptPasswordEncoder.class);

		// User user = new User(passwordEncoder.encode("password"),
		// 		"test@email.com", "John", "Highway", "");
		// userRepository.save(user);
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
