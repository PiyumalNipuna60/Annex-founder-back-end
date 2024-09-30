package com.example.annex_founder;

import com.example.annex_founder.model.Admins;
import com.example.annex_founder.repo.AdminRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class ApiApplication {
	private final AdminRepo adminRepo;
	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private static final Logger logger = LoggerFactory.getLogger(ApiApplication.class);

    public ApiApplication(AdminRepo adminRepo) {
        this.adminRepo = adminRepo;
    }

    public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@PostConstruct
	public void initUsers() {
		try {
			String encodePassword = passwordEncoder.encode("1234");

			adminRepo.save(new Admins(1L, "nipuna315np@gmail.com", "0754585756", "admin",  encodePassword));

		} catch (Exception e) {
			logger.error("An error occurred during user initialization.", e);
		}

	}

}
