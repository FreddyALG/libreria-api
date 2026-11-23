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

import com.linares.libreria.dto.EditorialRequestDTO;
import com.linares.libreria.dto.EditorialResponseDTO;
import com.linares.libreria.dto.ErrorResponseDTO;
import com.linares.libreria.dto.LibroResponseDTO;
import com.linares.libreria.service.EditorialService;
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
@RequestMapping("/api/editoriales")
@Tag(name = "Editoriales", description = "CRUD de la tabla editoriales")
public class EditorialController {

	private final EditorialService editorialService;
	private final LibroService libroService;

	public EditorialController(EditorialService editorialService, LibroService libroService) {
		this.editorialService = editorialService;
		this.libroService = libroService;
	}

	@Operation(summary = "Listar editoriales", description = "Devuelve todas las editoriales con su número de libros")
	@ApiResponse(responseCode = "200", description = "Listado obtenido")
	@GetMapping
	public ResponseEntity<List<EditorialResponseDTO>> listar() {
		return ResponseEntity.ok(editorialService.listar());
	}

	@Operation(summary = "Buscar editorial por id")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Editorial encontrada"),
			@ApiResponse(responseCode = "404", description = "La editorial no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@GetMapping("/{id}")
	public ResponseEntity<EditorialResponseDTO> buscarPorId(
			@Parameter(description = "Id de la editorial", example = "9") @PathVariable Integer id) {
		return ResponseEntity.ok(editorialService.buscarPorId(id));
	}

	@Operation(summary = "Listar libros de una editorial")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Libros de la editorial"),
			@ApiResponse(responseCode = "404", description = "La editorial no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@GetMapping("/{id}/libros")
	public ResponseEntity<List<LibroResponseDTO>> librosDeLaEditorial(
			@Parameter(description = "Id de la editorial", example = "1") @PathVariable Integer id) {
		return ResponseEntity.ok(libroService.listarPorEditorial(id));
	}

	@Operation(summary = "Crear editorial", description = "El id lo genera la base de datos (AUTO_INCREMENT)")
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Editorial creada"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@PostMapping
	public ResponseEntity<EditorialResponseDTO> crear(@Valid @RequestBody EditorialRequestDTO dto) {
		EditorialResponseDTO creada = editorialService.crear(dto);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(creada.idEditorial()).toUri();
		return ResponseEntity.created(location).body(creada);
	}

	@Operation(summary = "Actualizar editorial")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Editorial actualizada"),
			@ApiResponse(responseCode = "400", description = "Datos inválidos",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "404", description = "La editorial no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@PutMapping("/{id}")
	public ResponseEntity<EditorialResponseDTO> actualizar(
			@Parameter(description = "Id de la editorial", example = "9") @PathVariable Integer id,
			@Valid @RequestBody EditorialRequestDTO dto) {
		return ResponseEntity.ok(editorialService.actualizar(id, dto));
	}

	@Operation(summary = "Eliminar editorial", description = "Solo se puede eliminar si no tiene libros asociados")
	@ApiResponses({
			@ApiResponse(responseCode = "204", description = "Editorial eliminada"),
			@ApiResponse(responseCode = "404", description = "La editorial no existe",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
			@ApiResponse(responseCode = "409", description = "La editorial tiene libros asociados",
					content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(
			@Parameter(description = "Id de la editorial", example = "11") @PathVariable Integer id) {
		editorialService.eliminar(id);
		return ResponseEntity.noContent().build();
	}
}
