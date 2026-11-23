package com.linares.libreria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AutorResponse", description = "Autor devuelto por la API")
public record AutorResponseDTO(

		@Schema(description = "Id del autor", example = "1")
		Integer idAutor,

		@Schema(description = "Nombre del autor", example = "Gabriel García Márquez")
		String nombre,

		@Schema(description = "País de origen", example = "Colombia")
		String pais,

		@Schema(description = "Número de libros del autor", example = "6")
		Integer totalLibros) {
}
