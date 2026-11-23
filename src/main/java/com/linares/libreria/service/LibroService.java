package com.linares.libreria.service;

import java.util.List;

import com.linares.libreria.dto.LibroRequestDTO;
import com.linares.libreria.dto.LibroResponseDTO;

public interface LibroService extends ICrudService<LibroRequestDTO, LibroResponseDTO, Integer> {

	List<LibroResponseDTO> listarPorAutor(Integer idAutor);

	List<LibroResponseDTO> listarPorEditorial(Integer idEditorial);

	List<LibroResponseDTO> buscarPorTitulo(String texto);
}
