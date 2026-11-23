package com.linares.libreria.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.linares.libreria.dto.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Manejo centralizado de errores: los controladores no necesitan try/catch y
 * el cliente siempre recibe el mismo formato (ErrorResponseDTO).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
		return build(HttpStatus.NOT_FOUND, ex.getMessage(), req, List.of());
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ErrorResponseDTO> handleConflict(ConflictException ex, HttpServletRequest req) {
		return build(HttpStatus.CONFLICT, ex.getMessage(), req, List.of());
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponseDTO> handleBadRequest(BadRequestException ex, HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req, List.of());
	}

	/** Errores de @Valid (anotaciones @NotBlank, @Positive, ...) */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest req) {
		List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
				.map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
				.toList();
		return build(HttpStatus.BAD_REQUEST, "Datos de entrada inválidos", req, detalles);
	}

	/** JSON mal formado */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponseDTO> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, "El cuerpo de la petición no es un JSON válido", req, List.of());
	}

	/** Ej: /api/productos/abc cuando se espera un número */
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, "Parámetro '" + ex.getName() + "' con formato inválido", req, List.of());
	}

	/** Falta un parámetro obligatorio (ej: /api/libros/buscar sin ?titulo=) */
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorResponseDTO> handleMissingParam(MissingServletRequestParameterException ex,
			HttpServletRequest req) {
		return build(HttpStatus.BAD_REQUEST, "Falta el parámetro '" + ex.getParameterName() + "'", req, List.of());
	}

	/** Restricciones de la BD (FK, NOT NULL, ...) */
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponseDTO> handleIntegrity(DataIntegrityViolationException ex,
			HttpServletRequest req) {
		return build(HttpStatus.CONFLICT, "La operación viola una restricción de la base de datos", req, List.of());
	}

	/** Ruta inexistente (ej: /api/otracosa) */
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleNoResource(NoResourceFoundException ex, HttpServletRequest req) {
		return build(HttpStatus.NOT_FOUND, "Recurso no encontrado", req, List.of());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex, HttpServletRequest req) {
		return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", req, List.of());
	}

	private ResponseEntity<ErrorResponseDTO> build(HttpStatus status, String mensaje, HttpServletRequest req,
			List<String> detalles) {
		ErrorResponseDTO body = new ErrorResponseDTO(LocalDateTime.now(), status.value(), status.getReasonPhrase(),
				mensaje, req.getRequestURI(), detalles);
		return ResponseEntity.status(status).body(body);
	}
}
