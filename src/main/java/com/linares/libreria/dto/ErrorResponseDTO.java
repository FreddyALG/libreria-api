package com.linares.libreria.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Formato uniforme para todas las respuestas de error de la API.
 */
@Schema(name = "ErrorResponse", description = "Respuesta estándar de error")
public record ErrorResponseDTO(

		@Schema(description = "Fecha y hora del error", example = "2026-11-19T18:30:00")
		LocalDateTime timestamp,

		@Schema(description = "Código HTTP", example = "404")
		int status,

		@Schema(description = "Nombre del error HTTP", example = "Not Found")
		String error,

		@Schema(description = "Mensaje descriptivo", example = "Libro no encontrado(a) con id: 9999")
		String mensaje,

		@Schema(description = "Ruta solicitada", example = "/api/libros/9999")
		String ruta,

		@Schema(description = "Detalle de errores de validación (si aplica)")
		List<String> detalles) {
}
