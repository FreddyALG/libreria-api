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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.linares.libreria.dto.AutorRequestDTO;
import com.linares.libreria.dto.AutorResponseDTO;
import com.linares.libreria.dto.ErrorResponseDTO;
import com.linares.libreria.dto.LibroResponseDTO;
import com.linares.libreria.service.AutorService;
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
@RequestMapping("/api/autores")
@Tag(name = "Autores", description = "CRUD de la tabla autores")
public class AutorController {

	private final AutorService autorService;
	private final LibroService libroService;

	public AutorController(AutorService autorService, LibroService libroService) {
		this.autorService = autorService;
		this.libroService = libroService;
	}

	@Operation(summary = "Listar autores", description = "Devuelve todos los autores con su número de libros")
	@ApiResponse(responseCode = "200", description = "Listado obtenido")
	@GetMapping
	public ResponseEntity<List<AutorResponseDTO>> listar() {
		return ResponseEntity.ok(autorService.listar());
	}

	@Operation(summary = "Buscar autor por id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Autor encontrado"),
			@ApiResponse(responseCode = "404", description = "El autor no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<AutorResponseDTO> buscarPorId(
			@Parameter(description = "Id del autor", example = "1") @PathVariable Integer id) {
		return ResponseEntity.ok(autorService.buscarPorId(id));
	}

	@Operation(summary = "Listar libros de un autor")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Libros del autor"),
			@ApiResponse(responseCode = "404", description = "El autor no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@GetMapping("/{id}/libros")
	public ResponseEntity<List<LibroResponseDTO>> librosDelAutor(
			@Parameter(description = "Id del autor", example = "1") @PathVariable Integer id) {
		return ResponseEntity.ok(libroService.listarPorAutor(id));
	}

	@Operation(summary = "Crear autor", description = "El id lo genera la base de datos (AUTO_INCREMENT)")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Autor creado"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@PostMapping
	public ResponseEntity<AutorResponseDTO> crear(@Valid @RequestBody AutorRequestDTO dto) {
		AutorResponseDTO creado = autorService.crear(dto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(creado.idAutor()).toUri();
		return ResponseEntity.created(location).body(creado);
	}

	@Operation(summary = "Actualizar autor")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Autor actualizado"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "El autor no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@PutMapping("/{id}")
	public ResponseEntity<AutorResponseDTO> actualizar(
			@Parameter(description = "Id del autor", example = "1") @PathVariable Integer id,
			@Valid @RequestBody AutorRequestDTO dto) {
		return ResponseEntity.ok(autorService.actualizar(id, dto));
	}

	@Operation(summary = "Eliminar autor", description = "Solo se puede eliminar si no tiene libros asociados")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Autor eliminado"),
			@ApiResponse(responseCode = "404", description = "El autor no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "409", description = "El autor tiene libros asociados",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(
			@Parameter(description = "Id del autor", example = "11") @PathVariable Integer id) {
		autorService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
