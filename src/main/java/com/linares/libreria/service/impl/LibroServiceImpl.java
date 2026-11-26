package com.linares.libreria.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linares.libreria.dto.LibroRequestDTO;
import com.linares.libreria.dto.LibroResponseDTO;
import com.linares.libreria.exception.ResourceNotFoundException;
import com.linares.libreria.mapper.LibroMapper;
import com.linares.libreria.persistence.entity.AutorEntity;
import com.linares.libreria.persistence.entity.EditorialEntity;
import com.linares.libreria.persistence.entity.LibroEntity;
import com.linares.libreria.persistence.repository.AutorRepository;
import com.linares.libreria.persistence.repository.EditorialRepository;
import com.linares.libreria.persistence.repository.LibroRepository;
import com.linares.libreria.service.LibroService;

@Service
@Transactional
public class LibroServiceImpl implements LibroService {

	private static final String RECURSO = "Libro";

	private final LibroRepository libroRepository;
	private final AutorRepository autorRepository;
	private final EditorialRepository editorialRepository;
	private final LibroMapper mapper;

	public LibroServiceImpl(LibroRepository libroRepository, AutorRepository autorRepository,
			EditorialRepository editorialRepository, LibroMapper mapper) {
		this.libroRepository = libroRepository;
		this.autorRepository = autorRepository;
		this.editorialRepository = editorialRepository;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LibroResponseDTO> listar() {
		return libroRepository.findAll().stream().map(mapper::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public LibroResponseDTO buscarPorId(Integer id) {
		return mapper.toResponse(obtenerEntidad(id));
	}

	@Override
	@Transactional(readOnly = true)
	public List<LibroResponseDTO> listarPorAutor(Integer idAutor) {
		if (!autorRepository.existsById(idAutor)) {
			throw new ResourceNotFoundException("Autor", idAutor);
		}
		return libroRepository.findByAutorIdAutor(idAutor).stream().map(mapper::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<LibroResponseDTO> listarPorEditorial(Integer idEditorial) {
		if (!editorialRepository.existsById(idEditorial)) {
			throw new ResourceNotFoundException("Editorial", idEditorial);
		}
		return libroRepository.findByEditorialIdEditorial(idEditorial).stream().map(mapper::toResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public List<LibroResponseDTO> buscarPorTitulo(String texto) {
		return libroRepository.findByTituloContainingIgnoreCase(texto.trim()).stream().map(mapper::toResponse)
				.toList();
	}

	@Override
	public LibroResponseDTO crear(LibroRequestDTO dto) {
		LibroEntity entity = mapper.toEntity(dto, obtenerAutor(dto.idAutor()), obtenerEditorial(dto.idEditorial()));
		return mapper.toResponse(libroRepository.save(entity));
	}

	@Override
	public LibroResponseDTO actualizar(Integer id, LibroRequestDTO dto) {
		LibroEntity entity = obtenerEntidad(id);
		mapper.updateEntity(dto, obtenerAutor(dto.idAutor()), obtenerEditorial(dto.idEditorial()), entity);
		return mapper.toResponse(libroRepository.save(entity));
	}

	@Override
	public void eliminar(Integer id) {
		if (!libroRepository.existsById(id)) {
			throw new ResourceNotFoundException(RECURSO, id);
		}
		libroRepository.deleteById(id);
	}

	private LibroEntity obtenerEntidad(Integer id) {
		return libroRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RECURSO, id));
	}

	private AutorEntity obtenerAutor(Integer idAutor) {
		if (idAutor == null) {
			return null;
		}
		return autorRepository.findById(idAutor).orElseThrow(() -> new ResourceNotFoundException("Autor", idAutor));
	}

	private EditorialEntity obtenerEditorial(Integer idEditorial) {
		if (idEditorial == null) {
			return null;
		}
		return editorialRepository.findById(idEditorial)
				.orElseThrow(() -> new ResourceNotFoundException("Editorial", idEditorial));
	}
}
