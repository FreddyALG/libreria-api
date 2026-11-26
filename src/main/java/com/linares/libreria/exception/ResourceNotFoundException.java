package com.linares.libreria.exception;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String recurso, Integer id) {
		super(recurso + " no encontrado(a) con id: " + id);
	}
}
