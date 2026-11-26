package com.linares.libreria.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linares.libreria.dto.EditorialRequestDTO;
import com.linares.libreria.dto.EditorialResponseDTO;
import com.linares.libreria.exception.ConflictException;
import com.linares.libreria.exception.ResourceNotFoundException;
import com.linares.libreria.mapper.EditorialMapper;
import com.linares.libreria.persistence.entity.EditorialEntity;
import com.linares.libreria.persistence.repository.EditorialRepository;
import com.linares.libreria.persistence.repository.LibroRepository;
import com.linares.libreria.service.EditorialService;

@Service
@Transactional
public class EditorialServiceImpl implements EditorialService {

	private static final String RECURSO = "Editorial";

	private final EditorialRepository editorialRepository;
	private final LibroRepository libroRepository;
	private final EditorialMapper mapper;

	public EditorialServiceImpl(EditorialRepository editorialRepository, LibroRepository libroRepository,
			EditorialMapper mapper) {
		this.editorialRepository = editorialRepository;
		this.libroRepository = libroRepository;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<EditorialResponseDTO> listar() {
		return editorialRepository.findAll().stream().map(mapper::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public EditorialResponseDTO buscarPorId(Integer id) {
		return mapper.toResponse(obtenerEntidad(id));
	}

	@Override
	public EditorialResponseDTO crear(EditorialRequestDTO dto) {
		return mapper.toResponse(editorialRepository.save(mapper.toEntity(dto)));
	}

	@Override
	public EditorialResponseDTO actualizar(Integer id, EditorialRequestDTO dto) {
		EditorialEntity entity = obtenerEntidad(id);
		mapper.updateEntity(dto, entity);
		return mapper.toResponse(editorialRepository.save(entity));
	}

	@Override
	public void eliminar(Integer id) {
		if (!editorialRepository.existsById(id)) {
			throw new ResourceNotFoundException(RECURSO, id);
		}
		if (libroRepository.existsByEditorialIdEditorial(id)) {
			throw new ConflictException(
					"No se puede eliminar la editorial " + id + " porque tiene libros asociados");
		}
		editorialRepository.deleteById(id);
	}

	private EditorialEntity obtenerEntidad(Integer id) {
		return editorialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
	}
}
