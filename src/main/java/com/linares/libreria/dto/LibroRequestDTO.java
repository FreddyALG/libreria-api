package com.linares.libreria.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

/**
 * Datos para crear o actualizar un libro. El id lo genera la BD (AUTO_INCREMENT).
 * idAutor e idEditorial son opcionales porque en libreria.sql admiten NULL.
 */
@Schema(name = "LibroRequest", description = "Datos para crear o actualizar un libro")
public record LibroRequestDTO(

		@Schema(description = "Título del libro", example = "El llano en llamas", maxLength = 150)
		@NotBlank(message = "El título es obligatorio")
		@Size(max = 150, message = "El título no puede superar 150 caracteres")
		String titulo,

		@Schema(description = "Fecha de publicación (yyyy-MM-dd)", example = "1953-09-01", type = "string", format = "date")
		@PastOrPresent(message = "La fecha de publicación no puede estar en el futuro")
		LocalDate fechaPublicacion,

		@Schema(description = "Id del autor (opcional)", example = "1")
		Integer idAutor,

		@Schema(description = "Id de la editorial (opcional)", example = "9")
		Integer idEditorial) {
}
