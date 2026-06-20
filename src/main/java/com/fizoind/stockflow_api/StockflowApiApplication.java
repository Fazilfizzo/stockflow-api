package com.fizoind.stockflow_api;

import com.fizoind.stockflow_api.product.entity.Product;
import com.fizoind.stockflow_api.product.repository.ProductRepository;
import com.fizoind.stockflow_api.user.Role;
import com.fizoind.stockflow_api.user.User;
import com.fizoind.stockflow_api.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class StockflowApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockflowApiApplication.class, args);
	}

}
