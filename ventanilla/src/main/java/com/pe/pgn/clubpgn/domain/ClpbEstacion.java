package com.pe.pgn.clubpgn.domain;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Susan Inga
 *
 */

@Entity
@Table(name = "clpb_estacion")
@Searchable
public class ClpbEstacion extends CamposAuditoria {

	
	private static final long serialVersionUID = 1368055597661549322L;
	private Long id;
    private String deEstacion;
    private String coCodeEstacionPgn;
    private ClpmUbigeo clpmUbigeo;
    private boolean stEstacion;
    private String deDireccion;
    private String coCodigoCofide;
    private BigDecimal vaMargen;
    private boolean stMetropolitano;
    
    private String coDepartamento;
	private String coProvincia;
	private String coDistrito;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SearchableId
	public Long getId() {
		return id;
	}

	@Column(name = "de_estacion", nullable = false, length = 150)
	public String getDeEstacion() {
		return deEstacion;
	}

	@Column(name = "co_code_estacion_pgn", length = 50)
	public String getCoCodeEstacionPgn() {
		return coCodeEstacionPgn;
	}

	@ManyToOne(targetEntity = ClpmUbigeo.class)
    @JoinColumn(name="co_ubigeo")
	public ClpmUbigeo getClpmUbigeo() {
		return clpmUbigeo;
	}
	
	@Column(name = "st_estacion", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStEstacion() {
		return stEstacion;
	}

	@Column(name = "de_direccion", nullable = false, length = 150)
	public String getDeDireccion() {
		return deDireccion;
	}

	@Column(name = "co_codigo_cofide", nullable = false, length = 50)
	public String getCoCodigoCofide() {
		return coCodigoCofide;
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
    
    @Column(name = "va_margen")
	public BigDecimal getVaMargen() {
		return vaMargen;
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

	public void setDeEstacion(String deEstacion) {
		this.deEstacion = deEstacion;
	}

	public void setCoCodeEstacionPgn(String coCodeEstacionPgn) {
		this.coCodeEstacionPgn = coCodeEstacionPgn;
	}

	public void setClpmUbigeo(ClpmUbigeo clpmUbigeo) {
		this.clpmUbigeo = clpmUbigeo;
	}

	public void setStEstacion(boolean stEstacion) {
		this.stEstacion = stEstacion;
	}

	public void setDeDireccion(String deDireccion) {
		this.deDireccion = deDireccion;
	}

	public void setCoCodigoCofide(String coCodigoCofide) {
		this.coCodigoCofide = coCodigoCofide;
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
		return this.deEstacion;
	}

	//Objetos Transient
	
	@Transient
	public String getCoDepartamento() {
		return coDepartamento;
	}

	public void setCoDepartamento(String coDepartamento) {
		this.coDepartamento = coDepartamento;
	}

	@Transient
	public String getCoProvincia() {
		return coProvincia;
	}

	public void setCoProvincia(String coProvincia) {
		this.coProvincia = coProvincia;
	}
	
	public void setVaMargen(BigDecimal vaMargen) {
		this.vaMargen = vaMargen;
	}

	@Transient
	public String getCoDistrito() {
		return coDistrito;
	}

	public void setCoDistrito(String coDistrito) {
		this.coDistrito = coDistrito;
	}    	
	 
 	@Column(name = "st_metropolitano", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStMetropolitano() {
		return stMetropolitano;
	}

	public void setStMetropolitano(boolean stMetropolitano) {
		this.stMetropolitano = stMetropolitano;
	} 	
	
}
