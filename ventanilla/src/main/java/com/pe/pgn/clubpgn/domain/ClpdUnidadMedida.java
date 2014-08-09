package com.pe.pgn.clubpgn.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "clpd_unidad_medida")
@Searchable
public class ClpdUnidadMedida extends CamposAuditoria{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5967317367254411692L;
	private Long id;
	private String deUnidadMedida;
    private String deSimbolo;
    private boolean stUnidadMedida;
    private boolean stIndicadorPuntos;
	
	@Override
	public boolean equals(Object o) {
		return false;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return this.deUnidadMedida;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SearchableId
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "de_unidad_medida", nullable = false, length = 50)
	public String getDeUnidadMedida() {
		return deUnidadMedida;
	}

	public void setDeUnidadMedida(String deUnidadMedida) {
		this.deUnidadMedida = deUnidadMedida;
	}

	@Column(name = "de_simbolo", nullable = false, length = 50)
	public String getDeSimbolo() {
		return deSimbolo;
	}

	public void setDeSimbolo(String deSimbolo) {
		this.deSimbolo = deSimbolo;
	}

	@Type(type = "yes_no")
	@Column(name = "st_unidad_medida", nullable = false)
	public boolean isStUnidadMedida() {
		return stUnidadMedida;
	}

	public void setStUnidadMedida(boolean stUnidadMedida) {
		this.stUnidadMedida = stUnidadMedida;
	}
	
	@Type(type = "yes_no")
	@Column(name = "st_indicador_puntos", nullable = false)
	public boolean isStIndicadorPuntos() {
		return stIndicadorPuntos;
	}

	public void setStIndicadorPuntos(boolean stIndicadorPuntos) {
		this.stIndicadorPuntos = stIndicadorPuntos;
	}

	@Column(name = "co_usuario_creador", updatable = false, nullable = false, length = 30)
	public String getCoUsuarioCreador() {
		return coUsuarioCreador;
	}

    @Column(name = "da_fecha_creacion", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
	public Calendar getDaFechaCreacion() {
		return daFechaCreacion;
	}

	@Column(name = "co_usuario_modificador", length = 30)
	public String getCoUsuarioModificador() {
		return coUsuarioModificador;
	}

    @Column(name = "da_fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
	public Calendar getDaFechaModificacion() {
		return daFechaModificacion;
	}
}
