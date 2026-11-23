package com.linares.libreria.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linares.libreria.persistence.entity.LibroEntity;

@Repository
public interface LibroRepository extends JpaRepository<LibroEntity, Integer> {

	/** Query derivada: libros de un autor (navega libro.autor.idAutor). */
	List<LibroEntity> findByAutorIdAutor(Integer idAutor);

	/** Query derivada: libros de una editorial (navega libro.editorial.idEditorial). */
	List<LibroEntity> findByEditorialIdEditorial(Integer idEditorial);

	/** Búsqueda por título sin importar mayúsculas (LIKE %texto%). */
	List<LibroEntity> findByTituloContainingIgnoreCase(String texto);

	boolean existsByAutorIdAutor(Integer idAutor);

	boolean existsByEditorialIdEditorial(Integer idEditorial);
}
