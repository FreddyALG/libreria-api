package com.linares.libreria.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa la tabla autores.
 */
@Entity
@Table(name = "autores")
public class AutorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // id_autor es AUTO_INCREMENT
	@Column(name = "id_autor")
	private Integer idAutor;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "pais", length = 50)
	private String pais;

	/* Relación inversa 1..* con libros (sin cascada: la FK no tiene ON DELETE CASCADE) */
	@OneToMany(mappedBy = "autor")
	private List<LibroEntity> libros = new ArrayList<>();

	public AutorEntity() {
	}

	public AutorEntity(Integer idAutor, String nombre, String pais) {
		this.idAutor = idAutor;
		this.nombre = nombre;
		this.pais = pais;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<LibroEntity> getLibros() {
		return libros;
	}

	public void setLibros(List<LibroEntity> libros) {
		this.libros = libros;
	}
}
