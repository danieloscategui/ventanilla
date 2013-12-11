/**
 * 
 */
package com.pe.pgn.clubpgn.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.ListUtils;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.domain.beans.BNProgramaDetalle;

/**
 * @author Edwin Farfan
 * @modified Frank Ayala
 * 
 * */

@Entity
@Table(name = "clpb_programa")
@Searchable
public class ClpbPrograma extends CamposAuditoria {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long nuAviso1;
    private Long nuAviso2;
    private String dePrograma;
	private ClpdUnidadMedida clpdUnidadMedida = new ClpdUnidadMedida();   
    private Calendar daFechaInicioVigencia;
    private Calendar daFechaFinVigencia;
    private boolean stImprimeMensaje;
    private boolean stAcumPtos;
    private boolean stDisponiblePtoAfiliacion;
    private boolean stPrograma;    
    private boolean stAfiliacionPorDefecto;    
    private boolean stProgramaParaPuntos;
    private List <BNProgramaDetalle> programas;
    private String desFechaInicioVigencia;
    private String desFechaFinVigencia;
    
    
    
    @SuppressWarnings("unchecked")
    public ClpbPrograma() {
    	this.programas = ListUtils.lazyList(
  		new ArrayList<BNProgramaDetalle>(),
  		FactoryUtils.instantiateFactory(BNProgramaDetalle.class));
	}

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
	   return this.dePrograma;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SearchableId	
	public Long getId() {
		return id;
	}
	
	@ManyToOne(targetEntity = ClpdUnidadMedida.class)
    @JoinColumn(name = "co_unidad_medida")
	public ClpdUnidadMedida getClpdUnidadMedida() {
		return clpdUnidadMedida;
	}

	public void setClpdUnidadMedida(ClpdUnidadMedida clpdUnidadMedida) {
		this.clpdUnidadMedida = clpdUnidadMedida;
	}
	
	@Transient
	public List<BNProgramaDetalle> getProgramas() {
		return programas;
	}

	public void setProgramas(List<BNProgramaDetalle> programas) {
		this.programas = programas;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDePrograma(String dePrograma) {
		this.dePrograma = dePrograma;
	}

	@Column(name = "st_imprime_mensaje", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStImprimeMensaje() {
		return stImprimeMensaje;
	}
	
	public void setStImprimeMensaje(boolean stImprimeMensaje) {
		this.stImprimeMensaje = stImprimeMensaje;
	}
	
	@Column(name = "st_acum_ptos", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStAcumPtos() {
		return stAcumPtos;
	}

	public void setStAcumPtos(boolean stAcumPtos) {
		this.stAcumPtos = stAcumPtos;
	}

	@Column(name = "st_disponible_pto_afiliacion", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStDisponiblePtoAfiliacion() {
		return stDisponiblePtoAfiliacion;
	}

	public void setStDisponiblePtoAfiliacion(boolean stDisponiblePtoAfiliacion) {
		this.stDisponiblePtoAfiliacion = stDisponiblePtoAfiliacion;
	}

	@Column(name = "st_programa", nullable = false)
	@Type(type = "yes_no") 
	public boolean isStPrograma() {
		return stPrograma;
	}

	public void setStPrograma(boolean stPrograma) {
		this.stPrograma = stPrograma;
	}

	public void setDaFechaInicioVigencia(Calendar daFechaInicioVigencia) {
		this.daFechaInicioVigencia = daFechaInicioVigencia;
	}

	public void setDaFechaFinVigencia(Calendar daFechaFinVigencia) {
		this.daFechaFinVigencia = daFechaFinVigencia;
	}

	public void setNuAviso1(Long nuAviso1) {
		this.nuAviso1 = nuAviso1;
	}

	public void setNuAviso2(Long nuAviso2) {
		this.nuAviso2 = nuAviso2;
	}
	
	@Transient
	public String getDesFechaInicioVigencia() {
		return desFechaInicioVigencia;
	}

	public void setDesFechaInicioVigencia(String desFechaInicioVigencia) {
		this.desFechaInicioVigencia = desFechaInicioVigencia;
	}

	@Transient
	public String getDesFechaFinVigencia() {
		return desFechaFinVigencia;
	}

	public void setDesFechaFinVigencia(String desFechaFinVigencia) {
		this.desFechaFinVigencia = desFechaFinVigencia;
	}

	@Column(name = "de_programa", nullable = false, length = 150)
	public String getDePrograma() {
		return dePrograma;
	}

	@Column(name = "da_fecha_inicio_vigencia", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
	public Calendar getDaFechaInicioVigencia() {
		return daFechaInicioVigencia;
	}

	@Column(name = "da_fecha_fin_vigencia", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
	public Calendar getDaFechaFinVigencia() {
		return daFechaFinVigencia;
	}

	@Column(name = "nu_aviso1", nullable = false)
	public Long getNuAviso1() {
		return nuAviso1;
	}

	@Column(name = "nu_aviso2", nullable = false, length = 150)
	public Long getNuAviso2() {
		return nuAviso2;
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

    @Column(name = "st_afiliacion_por_defecto", nullable = false)
	@Type(type = "yes_no") 
    public boolean isStAfiliacionPorDefecto() {
		return stAfiliacionPorDefecto;
	}

	public void setStAfiliacionPorDefecto(boolean stAfiliacionPorDefecto) {
		this.stAfiliacionPorDefecto = stAfiliacionPorDefecto;
	}

    @Transient
	public String getImprimeMensaje(){
		return this.stImprimeMensaje==true?CLPConstantes.FLAG_YES:CLPConstantes.FLAG_NO;
	}
    
    @Transient
	public String getAcumPtos(){
		return this.stAcumPtos==true?CLPConstantes.FLAG_YES:CLPConstantes.FLAG_NO;
	}
	
    @Transient
	public String getDisponibleEnAfiliacion(){
		return this.stDisponiblePtoAfiliacion==true?CLPConstantes.FLAG_YES:CLPConstantes.FLAG_NO	;
	}
	
    @Transient
	public String getEstadoPrograma(){
		return this.stPrograma==true?CLPConstantes.FLAG_YES:CLPConstantes.FLAG_NO;
	}
	
    @Transient
	public boolean isStProgramaParaPuntos() {
		return stProgramaParaPuntos;
	}

	public void setStProgramaParaPuntos(boolean stProgramaParaPuntos) {
		this.stProgramaParaPuntos = stProgramaParaPuntos;
	}

	public String setDisponibleEnAfiliacion(String strDisponiblePtoAfiliacion){
		return strDisponiblePtoAfiliacion;
	}
	
	public String setEstadoPrograma(String strPrograma){
		return strPrograma;
	}
	
	public String setImprimeMensaje(String strImprimeMensaje){
		return strImprimeMensaje;
	}
}
