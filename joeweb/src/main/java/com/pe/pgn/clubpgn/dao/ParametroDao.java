package com.pe.pgn.clubpgn.dao;

import java.util.List;

import com.pe.pgn.clubpgn.domain.ClpbParametro;

public interface ParametroDao extends GenericDao<ClpbParametro, Long>{

	public ClpbParametro obtenerParametro(String deParametro);

	public List<ClpbParametro> obtenerParametrosLDAP();
}
