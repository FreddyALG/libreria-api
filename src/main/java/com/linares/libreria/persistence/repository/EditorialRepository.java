package com.linares.libreria.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.linares.libreria.persistence.entity.EditorialEntity;

@Repository
public interface EditorialRepository extends JpaRepository<EditorialEntity, Integer> {
}
