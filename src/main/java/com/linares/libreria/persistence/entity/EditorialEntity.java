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

@Entity
@Table(name = "editoriales")
public class EditorialEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_editorial")
	private Integer idEditorial;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "pais", length = 50)
	private String pais;

	@OneToMany(mappedBy = "editorial")
	private List<LibroEntity> libros = new ArrayList<>();

	public EditorialEntity() {
	}

	public EditorialEntity(Integer idEditorial, String nombre, String pais) {
		this.idEditorial = idEditorial;
		this.nombre = nombre;
		this.pais = pais;
	}

	public Integer getIdEditorial() {
		return idEditorial;
	}

	public void setIdEditorial(Integer idEditorial) {
		this.idEditorial = idEditorial;
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
