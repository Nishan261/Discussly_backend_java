package com.project.Discussly;

import com.project.Discussly.repository.UserRepository;
import com.project.Discussly.security.QueryUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class DiscusslyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscusslyApplication.class, args);
	}
	@Bean
	public QueryUtils queryUtils(UserRepository userRepository){
		return new QueryUtils(userRepository);
	}

}
