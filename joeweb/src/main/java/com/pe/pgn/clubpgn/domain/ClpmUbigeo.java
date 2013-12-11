/**
 * 
 */
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

import com.pe.pgn.clubpgn.common.CLPConstantes;

/**
 * @author Edwin Farfan
 *
 */
@Entity
@Table(name = "clpm_ubigeo")
@Searchable
public class ClpmUbigeo extends CamposAuditoria {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5154002107423473389L;
	private Long id;
	private Integer coDepartamento;
    private Integer coProvincia;
    private Integer coDistrito;
    private String deDepartamento;
    private String deProvincia;
    private String deDistrito;
    private boolean stUbigeo;
    
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SearchableId
	public Long getId() {
		return id;
	}
	
	@Column(name = "co_departamento", nullable = false)
	public Integer getCoDepartamento() {
		return coDepartamento;
	}
	
	@Column(name = "co_provincia", nullable = false)
	public Integer getCoProvincia() {
		return coProvincia;
	}
	
	@Column(name = "co_distrito", nullable = false)
	public Integer getCoDistrito() {
		return coDistrito;
	}
	
	@Column(name = "de_departamento", nullable = false, length = 50)
	public String getDeDepartamento() {
		return deDepartamento;
	}
	
	@Column(name = "de_provincia", nullable = false, length = 50)
	public String getDeProvincia() {
		return deProvincia;
	}
	
	@Column(name = "de_distrito", nullable = false, length = 50)
	public String getDeDistrito() {
		return deDistrito;
	}
	
	@Column(name = "st_ubigeo", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStUbigeo() {
		return stUbigeo;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setCoDepartamento(Integer coDepartamento) {
		this.coDepartamento = coDepartamento;
	}

	public void setCoProvincia(Integer coProvincia) {
		this.coProvincia = coProvincia;
	}

	public void setCoDistrito(Integer coDistrito) {
		this.coDistrito = coDistrito;
	}

	public void setDeDepartamento(String deDepartamento) {
		this.deDepartamento = deDepartamento;
	}

	public void setDeProvincia(String deProvincia) {
		this.deProvincia = deProvincia;
	}

	public void setDeDistrito(String deDistrito) {
		this.deDistrito = deDistrito;
	}

	public void setStUbigeo(boolean stUbigeo) {
		this.stUbigeo = stUbigeo;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return this.deDistrito+	CLPConstantes.CARACTER_GUION + this.deProvincia+ CLPConstantes.CARACTER_GUION+ this.deDepartamento;
	}  
    
    
    
}
