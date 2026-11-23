package com.linares.libreria.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.tags.Tag;

/**
 * Personalización de la documentación Swagger / OpenAPI.
 * UI:   http://localhost:8080/swagger-ui.html
 * JSON: http://localhost:8080/api-docs
 */
@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI libreriaOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API Librería - libreria")
						.version("1.0.0")
						.description("""
								API REST para administrar **autores**, **editoriales** y **libros** de la base de datos `libreria`.

								Tecnologías: Spring Boot 3, Spring Data JPA, MariaDB, Docker (red `linares`).

								Arquitectura por capas: *Controller → Service → Mapper → Repository → Entity*.
								""")
						.contact(new Contact()
								.name("Fredy Linares")
								.email("fredy.linares@sky.net.co"))
						.license(new License()
								.name("Uso académico - Universidad Distrital Francisco José de Caldas")
								.url("https://www.udistrital.edu.co")))
				.externalDocs(new ExternalDocumentation()
						.description("Documentación de Spring Data JPA")
						.url("https://spring.io/projects/spring-data-jpa"))
				.tags(List.of(
						new Tag().name("Autores").description("Operaciones CRUD sobre la tabla autores"),
						new Tag().name("Editoriales").description("Operaciones CRUD sobre la tabla editoriales"),
						new Tag().name("Libros").description("Operaciones CRUD sobre la tabla libros")));
	}
}
