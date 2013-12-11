/**
 * 
 */
package com.pe.pgn.clubpgn.domain;

import java.util.Calendar;

import com.pe.pgn.clubpgn.model.BaseObject;

/**
 * @author Edwin Farfan
 *
 */
public abstract class CamposAuditoria  extends BaseObject {
	
	private static final long serialVersionUID = 6348277956552256688L;

	protected String coUsuarioCreador;

	protected Calendar daFechaCreacion;

	protected String coUsuarioModificador;

	protected Calendar daFechaModificacion;


	public void setCoUsuarioCreador(String coUsuarioCreador) {
		this.coUsuarioCreador = coUsuarioCreador;
	}
	
	public void setDaFechaCreacion(Calendar daFechaCreacion) {
		this.daFechaCreacion = daFechaCreacion;
	}

	public void setCoUsuarioModificador(String coUsuarioModificador) {
		this.coUsuarioModificador = coUsuarioModificador;
	}
	
	public void setDaFechaModificacion(Calendar daFechaModificacion) {
		this.daFechaModificacion = daFechaModificacion;
	}

	
}
