package com.blog;

import com.blog.entity.Role;
import com.blog.repository.RoleRepository;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Bloog App rest-api",
				description = "Spring Boot Blog App REST API Documentation",
				version = "v1.0",
				contact = @Contact(
						name = "Karol",
						email = "karol200242@gmail.com"
				),
				license = @License(
					name = "Appache 2.0",
					url = "https://www.mylicence.org"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot BLog App Documentation",
				url = "https://www.github.com"
		)
)
public class BlogRestApiApplication implements CommandLineRunner {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogRestApiApplication.class, args);
	}

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		if (!roleRepository.existsById(1L)) {
			Role admin = new Role();
			admin.setName("ROLE_ADMIN");
			roleRepository.save(admin);

			Role user = new Role();
			user.setName("ROLE_USER");
			roleRepository.save(user);
		}
	}
}
