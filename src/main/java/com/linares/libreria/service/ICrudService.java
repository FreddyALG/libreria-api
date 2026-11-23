package com.linares.libreria.service;

import java.util.List;

/**
 * Contrato CRUD genérico.
 *
 * @param <REQ> DTO de entrada (request)
 * @param <RES> DTO de salida (response)
 * @param <ID>  tipo de la llave primaria
 */
public interface ICrudService<REQ, RES, ID> {

	List<RES> listar();

	RES buscarPorId(ID id);

	RES crear(REQ dto);

	RES actualizar(ID id, REQ dto);

	void eliminar(ID id);
}
