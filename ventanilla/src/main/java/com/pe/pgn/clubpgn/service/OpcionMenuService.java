package com.pe.pgn.clubpgn.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pe.pgn.clubpgn.model.OpcionMenu;

public interface OpcionMenuService extends GenericManager<OpcionMenu, Long> {

	public void obtenerMenuPrincipal(HttpServletRequest request);
	public List<OpcionMenu> obtenerOpcionesMenuPorRol(Long coRole);
	public List<OpcionMenu> obtenerOpcionesMenuNoEnRol(Long coRole);
	public List<OpcionMenu> obtenerTodasOpcionesMenu();
}
