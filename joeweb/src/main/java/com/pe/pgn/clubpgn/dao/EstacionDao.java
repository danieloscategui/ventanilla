package com.pe.pgn.clubpgn.dao;

import java.util.List; 


import com.pe.pgn.clubpgn.domain.ClpbEstacion;
import com.pe.pgn.clubpgn.domain.beans.BNEstacion;

public interface EstacionDao extends GenericDao<ClpbEstacion, Long> {

	public List<ClpbEstacion> obtenerEstaciones();
	
	public List<ClpbEstacion> busquedaEstacion(String deEstacion);
	
	public ClpbEstacion obtenerEstacion(Long id);
	
	public void guardarEstacion(ClpbEstacion estacion);
	
	public void eliminarEstacion(Long id);

	public List<BNEstacion> obtenerTodoEstaciones();
	
	public Integer validaNombreEstacion(Long id, String deEstacion);
	
	public Integer validaCodigoCofide(Long id, String codigoCofide);
}
