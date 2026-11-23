package com.linares.libreria.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linares.libreria.dto.AutorRequestDTO;
import com.linares.libreria.dto.AutorResponseDTO;
import com.linares.libreria.exception.ConflictException;
import com.linares.libreria.exception.ResourceNotFoundException;
import com.linares.libreria.mapper.AutorMapper;
import com.linares.libreria.persistence.entity.AutorEntity;
import com.linares.libreria.persistence.repository.AutorRepository;
import com.linares.libreria.persistence.repository.LibroRepository;
import com.linares.libreria.service.AutorService;

@Service
@Transactional
public class AutorServiceImpl implements AutorService {

	private static final String RECURSO = "Autor";

	private final AutorRepository autorRepository;
	private final LibroRepository libroRepository;
	private final AutorMapper mapper;

	public AutorServiceImpl(AutorRepository autorRepository, LibroRepository libroRepository, AutorMapper mapper) {
		this.autorRepository = autorRepository;
		this.libroRepository = libroRepository;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AutorResponseDTO> listar() {
		return autorRepository.findAll().stream().map(mapper::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public AutorResponseDTO buscarPorId(Integer id) {
		return mapper.toResponse(obtenerEntidad(id));
	}

	@Override
	public AutorResponseDTO crear(AutorRequestDTO dto) {
		return mapper.toResponse(autorRepository.save(mapper.toEntity(dto)));
	}

	@Override
	public AutorResponseDTO actualizar(Integer id, AutorRequestDTO dto) {
		AutorEntity entity = obtenerEntidad(id);
		mapper.updateEntity(dto, entity);
		return mapper.toResponse(autorRepository.save(entity));
	}

	@Override
	public void eliminar(Integer id) {
		if (!autorRepository.existsById(id)) {
			throw new ResourceNotFoundException(RECURSO, id);
		}
		// FK libros_ibfk_1: no se puede borrar un autor que tenga libros
		if (libroRepository.existsByAutorIdAutor(id)) {
			throw new ConflictException("No se puede eliminar el autor " + id + " porque tiene libros asociados");
		}
		autorRepository.deleteById(id);
	}

	private AutorEntity obtenerEntidad(Integer id) {
		return autorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
	}
}
