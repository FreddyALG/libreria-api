package com.linares.libreria.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.linares.libreria.dto.LibroRequestDTO;
import com.linares.libreria.dto.LibroResponseDTO;
import com.linares.libreria.persistence.entity.AutorEntity;
import com.linares.libreria.persistence.entity.EditorialEntity;

/** Prueba unitaria del mapper (no necesita base de datos). */
class LibroMapperTest {

	private final LibroMapper mapper = new LibroMapper();

	@Test
	void conservaLosDatosConAutorYEditorial() {
		AutorEntity gabo = new AutorEntity(1, "Gabriel García Márquez", "Colombia");
		EditorialEntity norma = new EditorialEntity(9, "Norma", "Colombia");
		LibroRequestDTO dto = new LibroRequestDTO("Cien años de soledad", LocalDate.of(1967, 5, 30), 1, 9);

		LibroResponseDTO res = mapper.toResponse(mapper.toEntity(dto, gabo, norma));

		assertEquals("Cien años de soledad", res.titulo());
		assertEquals(LocalDate.of(1967, 5, 30), res.fechaPublicacion());
		assertEquals(1, res.idAutor());
		assertEquals("Gabriel García Márquez", res.nombreAutor());
		assertEquals(9, res.idEditorial());
		assertEquals("Norma", res.nombreEditorial());
	}

	@Test
	void permiteLibroSinAutorNiEditorial() {
		LibroRequestDTO dto = new LibroRequestDTO("Anónimo", null, null, null);

		LibroResponseDTO res = mapper.toResponse(mapper.toEntity(dto, null, null));

		assertNull(res.idAutor());
		assertNull(res.nombreEditorial());
	}
}
