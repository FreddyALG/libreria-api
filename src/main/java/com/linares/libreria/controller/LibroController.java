package com.linares.libreria.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linares.libreria.dto.ErrorResponseDTO;
import com.linares.libreria.dto.LibroRequestDTO;
import com.linares.libreria.dto.LibroResponseDTO;
import com.linares.libreria.service.LibroService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "CRUD de la tabla libros")
public class LibroController {

	private final LibroService libroService;

	public LibroController(LibroService libroService) {
		this.libroService = libroService;
	}

	@Operation(summary = "Listar libros", description = "Incluye el nombre del autor y de la editorial")
	@ApiResponse(responseCode = "200", description = "Listado obtenido")
	@GetMapping
	public ResponseEntity<List<LibroResponseDTO>> listar() {
		return ResponseEntity.ok(libroService.listar());
	}

	@Operation(summary = "Buscar libros por título", description = "Coincidencia parcial, sin distinguir mayúsculas")
	@ApiResponse(responseCode = "200", description = "Libros que coinciden")
	@GetMapping("/buscar")
	public ResponseEntity<List<LibroResponseDTO>> buscarPorTitulo(
			@Parameter(description = "Texto a buscar en el título", example = "soledad") @RequestParam String titulo) {
		return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
	}

	@Operation(summary = "Buscar libro por id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Libro encontrado"),
			@ApiResponse(responseCode = "404", description = "El libro no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<LibroResponseDTO> buscarPorId(
			@Parameter(description = "Id del libro", example = "1") @PathVariable Integer id) {
		return ResponseEntity.ok(libroService.buscarPorId(id));
	}

	@Operation(summary = "Crear libro", description = "El id lo genera la BD. idAutor e idEditorial son opcionales")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Libro creado"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "El autor o la editorial indicados no existen",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@PostMapping
	public ResponseEntity<LibroResponseDTO> crear(@Valid @RequestBody LibroRequestDTO dto) {
		LibroResponseDTO creado = libroService.crear(dto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(creado.idLibro()).toUri();
		return ResponseEntity.created(location).body(creado);
	}

	@Operation(summary = "Actualizar libro")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Libro actualizado"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "El libro, el autor o la editorial no existen",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@PutMapping("/{id}")
	public ResponseEntity<LibroResponseDTO> actualizar(
			@Parameter(description = "Id del libro", example = "1") @PathVariable Integer id,
			@Valid @RequestBody LibroRequestDTO dto) {
		return ResponseEntity.ok(libroService.actualizar(id, dto));
	}

	@Operation(summary = "Eliminar libro")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Libro eliminado"),
			@ApiResponse(responseCode = "404", description = "El libro no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(
			@Parameter(description = "Id del libro", example = "41") @PathVariable Integer id) {
		libroService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
