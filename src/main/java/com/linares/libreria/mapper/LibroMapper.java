package com.linares.libreria.mapper;

import org.springframework.stereotype.Component;

import com.linares.libreria.dto.LibroRequestDTO;
import com.linares.libreria.dto.LibroResponseDTO;
import com.linares.libreria.persistence.entity.AutorEntity;
import com.linares.libreria.persistence.entity.EditorialEntity;
import com.linares.libreria.persistence.entity.LibroEntity;

@Component
public class LibroMapper {

	public LibroResponseDTO toResponse(LibroEntity entity) {
		if (entity == null) {
			return null;
		}
		AutorEntity autor = entity.getAutor();
		EditorialEntity editorial = entity.getEditorial();
		return new LibroResponseDTO(
				entity.getIdLibro(),
				entity.getTitulo(),
				entity.getFechaPublicacion(),
				autor != null ? autor.getIdAutor() : null,
				autor != null ? autor.getNombre() : null,
				editorial != null ? editorial.getIdEditorial() : null,
				editorial != null ? editorial.getNombre() : null);
	}

	public LibroEntity toEntity(LibroRequestDTO dto, AutorEntity autor, EditorialEntity editorial) {
		LibroEntity entity = new LibroEntity();
		updateEntity(dto, autor, editorial, entity);
		return entity;
	}

	public void updateEntity(LibroRequestDTO dto, AutorEntity autor, EditorialEntity editorial, LibroEntity entity) {
		entity.setTitulo(dto.titulo().trim());
		entity.setFechaPublicacion(dto.fechaPublicacion());
		entity.setAutor(autor);
		entity.setEditorial(editorial);
	}
}
