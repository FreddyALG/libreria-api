package com.linares.libreria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "EditorialResponse", description = "Editorial devuelta por la API")
public record EditorialResponseDTO(

		@Schema(description = "Id de la editorial", example = "9")
		Integer idEditorial,

		@Schema(description = "Nombre de la editorial", example = "Norma")
		String nombre,

		@Schema(description = "País de la editorial", example = "Colombia")
		String pais,

		@Schema(description = "Número de libros publicados", example = "5")
		Integer totalLibros) {
}
