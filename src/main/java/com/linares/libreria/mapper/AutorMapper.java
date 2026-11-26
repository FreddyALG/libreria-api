package com.linares.libreria.mapper;

import org.springframework.stereotype.Component;

import com.linares.libreria.dto.AutorRequestDTO;
import com.linares.libreria.dto.AutorResponseDTO;
import com.linares.libreria.persistence.entity.AutorEntity;

@Component
public class AutorMapper {

	public AutorResponseDTO toResponse(AutorEntity entity) {
		if (entity == null) {
			return null;
		}
		int total = entity.getLibros() == null ? 0 : entity.getLibros().size();
		return new AutorResponseDTO(entity.getIdAutor(), entity.getNombre(), entity.getPais(), total);
	}

	public AutorEntity toEntity(AutorRequestDTO dto) {
		AutorEntity entity = new AutorEntity();
		updateEntity(dto, entity);
		return entity;
	}

	public void updateEntity(AutorRequestDTO dto, AutorEntity entity) {
		entity.setNombre(dto.nombre().trim());
		entity.setPais(dto.pais());
	}
}
