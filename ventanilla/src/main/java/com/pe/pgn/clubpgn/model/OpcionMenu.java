package com.pe.pgn.clubpgn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.hibernate.annotations.Type;

import com.pe.pgn.clubpgn.common.CLPConstantes;

@Entity
@Table(name = "opcion_menu")
@Searchable
public class OpcionMenu implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4472173494902257422L;
	
	private Long	id;
	private String	deOpcionMenu;
	private String	deMenuHtml;
	private String	deAncho;
	private String	deName;
	private String  stEsElegido;  
	private Integer	nuNivel;
	private Long	coOpcionMenuPadre;
	private Integer	nuOrden;
	private boolean	stOpcionMenu;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SearchableId
	public Long getId() {
		return id;
	}

	@Column(name = "de_opcion_menu", nullable = false, length = 190)
	public String getDeOpcionMenu() {
		return deOpcionMenu;
	}

	@Column(name = "de_menu_html", length = 190)
	public String getDeMenuHtml() {
		return deMenuHtml;
	}

	@Column(name = "de_ancho", length = 19)
	public String getDeAncho() {
		return deAncho;
	}

	@Column(name = "nu_nivel", nullable = false)
	public Integer getNuNivel() {
		return nuNivel;
	}

	@Column(name = "co_opcion_menu_padre")
	public Long getCoOpcionMenuPadre() {
		return coOpcionMenuPadre;
	}

	@Column(name = "nu_orden")
	public Integer getNuOrden() {
		return nuOrden;
	}

	@Column(name = "st_opcion_menu")
	@Type(type = "yes_no") 
	public boolean isStOpcionMenu() {
		return stOpcionMenu;
	}

	@Transient
	public String getDeName() {
		return deName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setDeOpcionMenu(String deOpcionMenu) {
		this.deOpcionMenu = deOpcionMenu;
	}

	public void setDeMenuHtml(String deMenuHtml) {
		this.deMenuHtml = deMenuHtml;
	}

	public void setDeAncho(String deAncho) {
		this.deAncho = deAncho;
	}

	public void setNuNivel(Integer nuNivel) {
		this.nuNivel = nuNivel;
	}

	public void setCoOpcionMenuPadre(Long coOpcionMenuPadre) {
		this.coOpcionMenuPadre = coOpcionMenuPadre;
	}

	public void setNuOrden(Integer nuOrden) {
		this.nuOrden = nuOrden;
	}

	public void setStOpcionMenu(boolean stOpcionMenu) {
		this.stOpcionMenu = stOpcionMenu;
	}

	public void setDeName(String deName) {
		this.deName = deName;
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
		// TODO Auto-generated method stub
		return this.deOpcionMenu;
	}
	
	private boolean stElegido;

	@Transient
	public boolean isStElegido() {
		return stElegido;
	}
	
	@Transient
	public String getStEsElegido() {
		return stEsElegido;
	}

	public void setStElegido(boolean stElegido) {
		this.stElegido = stElegido;
	}
	
	public void setStEsElegido(String esElegido){
		if(esElegido.equalsIgnoreCase(CLPConstantes.ST_VERDADERO)){			
			this.stElegido = Boolean.TRUE;
		}
		else{
			this.stElegido = Boolean.FALSE; 
		}
	}

}
