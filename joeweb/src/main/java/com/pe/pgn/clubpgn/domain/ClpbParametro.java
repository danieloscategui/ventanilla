package com.pe.pgn.clubpgn.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.compass.annotations.SearchableId;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "clpb_parametro")
public class ClpbParametro implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2562589670796612595L;
	private Long id;
	private String deParametro;
	private String vaValor;
	private boolean stParametro;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SearchableId
	public Long getId() {
		return id;
	}
	
	@Column(name = "de_parametro", nullable = false, length = 100)
	public String getDeParametro() {
		return deParametro;
	}
	
	@Column(name = "va_valor", nullable = false, length = 100)
	public String getVaValor() {
		return vaValor;
	}
	
	@Column(name = "st_parametro", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStParametro() {
		return stParametro;
	}	

	public void setId(Long id) {
		this.id = id;
	}

	public void setDeParametro(String deParametro) {
		this.deParametro = deParametro;
	}

	public void setVaValor(String vaValor) {
		this.vaValor = vaValor;
	}

	public void setStParametro(boolean stParametro) {
		this.stParametro = stParametro;
	}	

}
