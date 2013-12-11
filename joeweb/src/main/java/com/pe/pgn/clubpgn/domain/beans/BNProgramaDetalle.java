/**
 * 
 */
package com.pe.pgn.clubpgn.domain.beans;

import java.io.Serializable;


/**
 * @author Edwin Farfan
 *
 */
public class BNProgramaDetalle implements Serializable{
	
	private static final long serialVersionUID = -7569142734240099681L;

	private boolean chkNoCompatible;

	private String unidadMedida;
	
	private String descripcion;
	
	private Long id;
	
	private String stNoCompatible;
	
	private String stafiliacionpordefecto;
	


	public String getStafiliacionpordefecto() {
		return stafiliacionpordefecto;
	}

	public void setStafiliacionpordefecto(String stafiliacionpordefecto) {
		this.stafiliacionpordefecto = stafiliacionpordefecto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isChkNoCompatible() {
		return chkNoCompatible;
	}

	public void setChkNoCompatible(boolean chkNoCompatible) {
		this.chkNoCompatible = chkNoCompatible;
	}

	public String getStNoCompatible() {
		return stNoCompatible;
	}

	public void setStNoCompatible(String stNoCompatible) {
		this.stNoCompatible = stNoCompatible;
		this.chkNoCompatible = this.stNoCompatible.equalsIgnoreCase("Y") ? true : false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BNProgramaDetalle other = (BNProgramaDetalle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	
}
