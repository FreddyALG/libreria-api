package com.linares.libreria.exception;

/** Se lanza cuando la operación choca con el estado actual de los datos (HTTP 409). */
public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ConflictException(String mensaje) {
		super(mensaje);
	}
}
