package com.adem;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.adem.domain.Role;
import com.adem.domain.User;
import com.adem.enums.RoleType;
import com.adem.exception.message.ErrorMessage;
import com.adem.repository.RoleRepository;
import com.adem.repository.UserRepository;

@SpringBootApplication
public class GreenrentApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenrentApplication.class, args);
	}
	
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public GreenrentApplication(RoleRepository roleRepository, UserRepository userRepository,
								BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

    @Bean
	CommandLineRunner initRolesAndAdmin() {
		return args -> {
			if (roleRepository.count() == 0) {
				roleRepository.save(new Role(1L, RoleType.ROLE_CUSTOMER));
				roleRepository.save(new Role(2L, RoleType.ROLE_ADMIN));
			}

			Optional<Role> adminRoleOptional = roleRepository.findByType(RoleType.ROLE_ADMIN);
			Set<Role> roles;
			if (adminRoleOptional.isPresent()) {
				Role adminRole = adminRoleOptional.get();
				
				if (!userRepository.existsByRoles(RoleType.ROLE_ADMIN)) {
					User admin = new User();
					admin.setFirstName("Adem");
					admin.setLastName("Kok");
					admin.setEmail("ademk@gmail.com");
					admin.setPassword(bCryptPasswordEncoder.encode("password"));
					admin.setPhone("0985 668 68 00");
					admin.setAddress("alik1");
					admin.setZipCode("85101");
					admin.setCreateAt(LocalDateTime.now());
					admin.setUpdateAt(LocalDateTime.now());

					roles = new HashSet<>();
					roles.add(adminRole);

					admin.setRoles(roles);
					userRepository.save(admin);
				}
			} else {
				throw new RuntimeException(String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_ADMIN));
			}
		};
	}
}
