package com.linares.libreria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Datos para crear o actualizar un autor. El id lo genera la BD (AUTO_INCREMENT). */
@Schema(name = "AutorRequest", description = "Datos para crear o actualizar un autor")
public record AutorRequestDTO(

		@Schema(description = "Nombre del autor", example = "Juan Rulfo", maxLength = 100)
		@NotBlank(message = "El nombre del autor es obligatorio")
		@Size(max = 100, message = "El nombre no puede superar 100 caracteres")
		String nombre,

		@Schema(description = "País de origen", example = "México", maxLength = 50)
		@Size(max = 50, message = "El país no puede superar 50 caracteres")
		String pais) {
}
