package com.fizoind.stockflow_api;

import com.fizoind.stockflow_api.user.Role;
import com.fizoind.stockflow_api.user.User;
import com.fizoind.stockflow_api.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class StockflowApiApplication {

	@Bean
	CommandLineRunner initAdmin(UserRepository userRepo, PasswordEncoder passwordEncoder) {

		return args -> {
			if (userRepo.findByUsername("admin").isEmpty()) {
				User admin = new User();
				admin.setUsername("admin");
				admin.setEmail("admin123@gmail");
				admin.setPassword(passwordEncoder.encode("admin@123"));
				admin.setRole(Role.ROLE_ADMIN);
				userRepo.save(admin);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(StockflowApiApplication.class, args);
	}

}
