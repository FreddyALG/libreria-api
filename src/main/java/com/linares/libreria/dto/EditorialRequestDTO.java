package com.linares.libreria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "EditorialRequest", description = "Datos para crear o actualizar una editorial")
public record EditorialRequestDTO(

		@Schema(description = "Nombre de la editorial", example = "Panamericana", maxLength = 100)
		@NotBlank(message = "El nombre de la editorial es obligatorio")
		@Size(max = 100, message = "El nombre no puede superar 100 caracteres")
		String nombre,

		@Schema(description = "País de la editorial", example = "Colombia", maxLength = 50)
		@Size(max = 50, message = "El país no puede superar 50 caracteres")
		String pais) {
}
