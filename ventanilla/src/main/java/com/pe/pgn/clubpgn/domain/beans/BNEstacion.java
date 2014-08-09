package com.pe.pgn.clubpgn.domain.beans;

import java.io.Serializable;

public class BNEstacion implements Serializable{

	private static final long serialVersionUID = -6227209198500152393L;
	
	private Long id;
	private String deEstacion;
	private String coCodigoEstacionCofide;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDeEstacion() {
		return deEstacion;
	}
	public void setDeEstacion(String deEstacion) {
		this.deEstacion = deEstacion;
	}
	public String getCoCodigoEstacionCofide() {
		return coCodigoEstacionCofide;
	}
	public void setCoCodigoEstacionCofide(String coCodigoEstacionCofide) {
		this.coCodigoEstacionCofide = coCodigoEstacionCofide;
	}
}
