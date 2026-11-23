package com.linares.libreria.exception;

/** Se lanza cuando un registro no existe (HTTP 404). */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String recurso, Integer id) {
		super(recurso + " no encontrado(a) con id: " + id);
	}
}
