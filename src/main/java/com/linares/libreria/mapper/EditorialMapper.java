package com.linares.libreria.mapper;

import org.springframework.stereotype.Component;

import com.linares.libreria.dto.EditorialRequestDTO;
import com.linares.libreria.dto.EditorialResponseDTO;
import com.linares.libreria.persistence.entity.EditorialEntity;

@Component
public class EditorialMapper {

	public EditorialResponseDTO toResponse(EditorialEntity entity) {
		if (entity == null) {
			return null;
		}
		int total = entity.getLibros() == null ? 0 : entity.getLibros().size();
		return new EditorialResponseDTO(entity.getIdEditorial(), entity.getNombre(), entity.getPais(), total);
	}

	public EditorialEntity toEntity(EditorialRequestDTO dto) {
		EditorialEntity entity = new EditorialEntity();
		updateEntity(dto, entity);
		return entity;
	}

	public void updateEntity(EditorialRequestDTO dto, EditorialEntity entity) {
		entity.setNombre(dto.nombre().trim());
		entity.setPais(dto.pais());
	}
}
