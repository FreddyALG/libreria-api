package com.linares.libreria.persistence.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libros")
public class LibroEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_libro")
	private Integer idLibro;

	@Column(name = "titulo", nullable = false, length = 150)
	private String titulo;

	@Column(name = "fecha_publicacion")
	private LocalDate fechaPublicacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_autor")
	private AutorEntity autor;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_editorial")
	private EditorialEntity editorial;

	public LibroEntity() {
	}

	public LibroEntity(Integer idLibro, String titulo, LocalDate fechaPublicacion, AutorEntity autor,
			EditorialEntity editorial) {
		this.idLibro = idLibro;
		this.titulo = titulo;
		this.fechaPublicacion = fechaPublicacion;
		this.autor = autor;
		this.editorial = editorial;
	}

	public Integer getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(Integer idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDate fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public AutorEntity getAutor() {
		return autor;
	}

	public void setAutor(AutorEntity autor) {
		this.autor = autor;
	}

	public EditorialEntity getEditorial() {
		return editorial;
	}

	public void setEditorial(EditorialEntity editorial) {
		this.editorial = editorial;
	}
}
