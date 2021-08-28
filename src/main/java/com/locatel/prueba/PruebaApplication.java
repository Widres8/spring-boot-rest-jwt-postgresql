package com.locatel.prueba;

import java.util.Arrays;
import java.util.HashSet;

import com.locatel.prueba.models.user.Role;
import com.locatel.prueba.models.user.User;
import com.locatel.prueba.repositories.user.RoleRepository;
import com.locatel.prueba.repositories.user.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PruebaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PruebaApplication.class, args);
	}

	@Bean
	CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository) {
		return args -> {

			// Create Admin and Client Roles
			Role adminRole = roleRepository.findByName("ADMIN");
			if (adminRole == null) {
				adminRole = new Role();
				adminRole.setName("ADMIN");
				roleRepository.save(adminRole);
			}

			Role userRole = roleRepository.findByName("CLIENT");
			if (userRole == null) {
				userRole = new Role();
				userRole.setName("CLIENT");
				roleRepository.save(userRole);
			}

			// Create an Admin user
			String email = "admin.locatel@gmail.com";
			User admin = userRepository.findByEmail(email);
			if (admin == null) {
				admin = new User().setEmail(email)
						.setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
						.setFirstName("Admin").setLastName("Locatel").setMobileNumber("9425094250")
						.setRoles(new HashSet<>(Arrays.asList(adminRole)));
				userRepository.save(admin);

				User user = new User().setEmail("user.locatel@gmail.com")
						.setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
						.setFirstName("User").setLastName("Locatel").setMobileNumber("9425094250")
						.setRoles(new HashSet<>(Arrays.asList(userRole)));
				userRepository.save(user);
			}
		};
	}
}
