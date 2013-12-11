package com.pe.pgn.clubpgn.domain.beans;

import java.io.Serializable;
import java.math.BigDecimal;

public class BNEmpresaAfiliadora implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5705313040584093879L;
	private BigDecimal coEmpresaAfiliadora;
	private String deEmpresaAfiliadora;
	private String stStrEmpresaAfiliadora;
	private BigDecimal coPrograma;
	private String dePrograma;
	private boolean stEmpresaAfiliadora;
	
	public BigDecimal getCoEmpresaAfiliadora() {
		return coEmpresaAfiliadora;
	}
	public void setCoEmpresaAfiliadora(BigDecimal coEmpresaAfiliadora) {
		this.coEmpresaAfiliadora = coEmpresaAfiliadora;
	}
	public String getDeEmpresaAfiliadora() {
		return deEmpresaAfiliadora;
	}
	public void setDeEmpresaAfiliadora(String deEmpresaAfiliadora) {
		this.deEmpresaAfiliadora = deEmpresaAfiliadora;
	}
	public BigDecimal getCoPrograma() {
		return coPrograma;
	}
	public void setCoPrograma(BigDecimal coPrograma) {
		this.coPrograma = coPrograma;
	}
	public String getDePrograma() {
		return dePrograma;
	}
	public void setDePrograma(String dePrograma) {
		this.dePrograma = dePrograma;
	}
	public String getStStrEmpresaAfiliadora() {
		return stStrEmpresaAfiliadora;
	}
	public void setStStrEmpresaAfiliadora(String stStrEmpresaAfiliadora) {
		this.stStrEmpresaAfiliadora = stStrEmpresaAfiliadora;
	}
	public boolean isStEmpresaAfiliadora() {
		return stEmpresaAfiliadora;
	}
	public void setStEmpresaAfiliadora(boolean stEmpresaAfiliadora) {
		this.stEmpresaAfiliadora = stEmpresaAfiliadora;
	}
	
}