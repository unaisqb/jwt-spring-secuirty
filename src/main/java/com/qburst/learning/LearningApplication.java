package com.qburst.learning;

import com.qburst.learning.model.AppUser;
import com.qburst.learning.model.Role;
import com.qburst.learning.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class LearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningApplication.class, args);
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService)  {
		return  args -> {
			userService.saveRole(new Role(null, "ADMIN"));
			userService.saveUser(new AppUser(null, "unais", "admin", "1234", new ArrayList<>()));
			userService.addRoleToUser("admin", "ADMIN");
		};
	}

}
