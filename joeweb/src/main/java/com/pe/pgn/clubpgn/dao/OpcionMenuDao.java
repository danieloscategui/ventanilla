package com.pe.pgn.clubpgn.dao;

import java.util.List;

import com.pe.pgn.clubpgn.model.OpcionMenu;

public interface OpcionMenuDao extends GenericDao<OpcionMenu, Long> {

	public List<OpcionMenu> obtenerOpcionesMenuPorUsuario();
	public List<OpcionMenu> obtenerOpcionesMenuPorRol(Long coRole);
	public List<OpcionMenu> obtenerOpcionesMenuNoEnRol(Long coRole);
	public List<OpcionMenu> obtenerDependenciasOpcionMenu(String opcionesMenu, boolean depedenciasSuperiores);
	public List<OpcionMenu> obtenerTodasOpcionesMenu();
	public List<OpcionMenu> obtenerTodosRolesMenuOpcion();
}
