package com.linares.libreria.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linares.libreria.persistence.entity.AutorEntity;

@Repository
public interface AutorRepository extends JpaRepository<AutorEntity, Integer> {
	// findAll, findById, save, deleteById, existsById... los hereda de JpaRepository
}
