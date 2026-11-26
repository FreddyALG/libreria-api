package com.linares.libreria.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linares.libreria.persistence.entity.LibroEntity;

@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Integer> {

	List<LibroEntity> findByAutorIdAutor(Integer idAutor);

	List<LibroEntity> findByEditorialIdEditorial(Integer idEditorial);

	List<LibroEntity> findByTituloContainingIgnoreCase(String texto);

	boolean existsByAutorIdAutor(Integer idAutor);

	boolean existsByEditorialIdEditorial(Integer idEditorial);
}
