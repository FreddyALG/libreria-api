package com.linares.libreria.exception;

/** Se lanza cuando la petición no tiene datos válidos (HTTP 400). */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(String mensaje) {
		super(mensaje);
	}
}
