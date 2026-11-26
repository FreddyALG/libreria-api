package com.linares.libreria.service;

import java.util.List;

public interface ICrudService<REQ, RES, ID> {

	List<RES> listar();

	RES buscarPorId(ID id);

	RES crear(REQ dto);

	RES actualizar(ID id, REQ dto);

	void eliminar(ID id);
}
