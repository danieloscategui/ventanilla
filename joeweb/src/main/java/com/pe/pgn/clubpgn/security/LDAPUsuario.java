package com.pe.pgn.clubpgn.security;

public class LDAPUsuario {

	private String userId;
	private String userName;
	private String deNombre;
	private String deApellido;
	private String dePais;
	private String deMail;	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeNombre() {
		return deNombre;
	}
	public void setDeNombre(String deNombre) {
		this.deNombre = deNombre;
	}
	public String getDePais() {
		return dePais;
	}
	public void setDePais(String dePais) {
		this.dePais = dePais;
	}
	public String getDeMail() {
		return deMail;
	}
	public void setDeMail(String deMail) {
		this.deMail = deMail;
	}
	public String getDeApellido() {
		return deApellido;
	}
	public void setDeApellido(String deApellido) {
		this.deApellido = deApellido;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LDAPUsuario other = (LDAPUsuario) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
}
