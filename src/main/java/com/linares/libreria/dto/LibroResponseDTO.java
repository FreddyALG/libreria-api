package com.linares.libreria.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LibroResponse", description = "Libro devuelto por la API")
public record LibroResponseDTO(

		@Schema(description = "Id del libro", example = "1")
		Integer idLibro,

		@Schema(description = "Título del libro", example = "Cien años de soledad")
		String titulo,

		@Schema(description = "Fecha de publicación", example = "1967-05-30", type = "string", format = "date")
		LocalDate fechaPublicacion,

		@Schema(description = "Id del autor", example = "1")
		Integer idAutor,

		@Schema(description = "Nombre del autor", example = "Gabriel García Márquez")
		String nombreAutor,

		@Schema(description = "Id de la editorial", example = "1")
		Integer idEditorial,

		@Schema(description = "Nombre de la editorial", example = "Penguin Random House")
		String nombreEditorial) {
}
