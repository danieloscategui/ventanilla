package com.pe.pgn.clubpgn.dao.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Repository;


import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.dao.OpcionMenuDao;
import com.pe.pgn.clubpgn.dao.hibernate.GenericDaoHibernate;
import com.pe.pgn.clubpgn.model.OpcionMenu;
import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.model.User;

@Repository("opcionMenuDao")
public class OpcionMenuDaoImpl extends GenericDaoHibernate<OpcionMenu, Long> implements
		OpcionMenuDao {

	public OpcionMenuDaoImpl(){
		super(OpcionMenu.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<OpcionMenu> obtenerOpcionesMenuPorUsuario(){
		
		StringBuffer query = new StringBuffer();		
		query.append("select distinct om.id as id, ");
		query.append("		om.de_opcion_menu as  deOpcionMenu,");
		query.append("		om.de_menu_html as  deMenuHtml,");
		query.append("		om.de_ancho  as  deAncho,");
		query.append("		om.nu_nivel as  nuNivel,");
		query.append("		om.co_opcion_menu_padre as  coOpcionMenuPadre,");
		query.append("		om.nu_orden as  nuOrden ");	
		query.append("	from role_opcion_menu rom join opcion_menu om on rom.co_opcion_menu = om.id ");
		query.append("	where rom.co_role in (").append(obtenerIdRoles()).append(") ");
		query.append("		and om.st_opcion_menu = '").append(CLPConstantes.ST_VERDADERO).append("' ");
		query.append("	order by om.nu_nivel asc, om.nu_orden asc");

		return findListOfBeans(query.toString(), null, OpcionMenu.class);
	}
	
	public String obtenerIdRoles(){
		
		User user = getUsuarioLogueado();
		String string = "";
		for(Iterator<Role> iterRole = user.getRoles().iterator(); iterRole.hasNext(); ){
			
			Role role = iterRole.next();
			string = string.concat(role.getId().toString());
			if(iterRole.hasNext()){
				string = string.concat(",");
			}			
		}		
		return string;		
	}
	
	@SuppressWarnings("unchecked")
	public List<OpcionMenu> obtenerOpcionesMenuPorRol(Long coRole){
		
		StringBuffer query = new StringBuffer();
		query.append("select om.id as id,");
		query.append("		om.de_opcion_menu as  deOpcionMenu,");
		query.append("		om.de_menu_html as  deMenuHtml,");
		query.append("		om.de_ancho  as  deAncho,");
		query.append("		om.nu_nivel as  nuNivel,");
		query.append("		om.co_opcion_menu_padre as  coOpcionMenuPadre,");
		query.append("		om.nu_orden as  nuOrden,  ");
		query.append("		'").append(CLPConstantes.ST_VERDADERO).append("' as stEsElegido ");
		query.append("	from opcion_menu om join role_opcion_menu rom  on om.id = rom.co_opcion_menu ");
		query.append("	where rom.co_role = ").append(coRole);
		query.append("		and st_opcion_menu = '").append(CLPConstantes.ST_VERDADERO).append("' ");
		query.append("		and de_menu_html is not null ");
		query.append("		order by co_opcion_menu_padre asc nulls first, nu_nivel asc, nu_orden asc");
		//System.out.println("obtenerOpcionesMenuPorRol:" + query.toString());
		return findListOfBeans(query.toString(), null, OpcionMenu.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<OpcionMenu> obtenerOpcionesMenuNoEnRol(Long coRole){
		
		StringBuffer query = new StringBuffer();
		query.append("select om.id as id, ");
		query.append("		om.de_opcion_menu as  deOpcionMenu, ");
		query.append("		om.de_menu_html as  deMenuHtml, ");
		query.append("		om.de_ancho  as  deAncho, ");
		query.append("		om.nu_nivel as  nuNivel, ");
		query.append("		om.co_opcion_menu_padre as  coOpcionMenuPadre, ");
		query.append("		om.nu_orden as  nuOrden  ");
		query.append("	from opcion_menu  om left outer join opcion_menu omp on om.co_opcion_menu_padre = omp.id  ");
		query.append("	where om.id not in  ");
		query.append("		(select rop.co_opcion_menu  ");
		query.append("			from role_opcion_menu rop  ");
		query.append("			where rop.co_role = ").append(coRole).append(") ");
		query.append("		and om.st_opcion_menu = '").append(CLPConstantes.ST_VERDADERO).append("' ");
		query.append("		and om.de_menu_html is not null ");
		query.append("	order by omp.nu_nivel asc, omp.nu_orden asc, om.nu_nivel asc, om.nu_orden asc ");
		
		return findListOfBeans(query.toString(), null, OpcionMenu.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<OpcionMenu> obtenerDependenciasOpcionMenu(String opcionesMenu, boolean depedenciasSuperiores){
		
		StringBuffer query = new StringBuffer();
		query.append("select distinct    ");
		query.append("		substr(sys_connect_by_path(t.id, '*') || '*',   ");
		query.append("		(instr(sys_connect_by_path(t.id, '*')|| '*','*',1,1)+1),  ");
		query.append(" 		(instr(sys_connect_by_path(t.id, '*')|| '*','*',1,2))-2) as id,   ");  		  

		query.append(" 		substr(sys_connect_by_path(t.de_opcion_menu, '*') || '*',   ");
		query.append(" 		(instr(sys_connect_by_path(t.de_opcion_menu, '*')|| '*','*',1,1)+1),  ");
		query.append(" 		(instr(sys_connect_by_path(t.de_opcion_menu, '*')|| '*','*',1,2))-2) as deOpcionMenu, "); 
	            
		query.append(" 		substr(sys_connect_by_path(t.de_menu_html, '*') || '*',   ");
		query.append(" 		(instr(sys_connect_by_path(t.de_menu_html, '*')|| '*','*',1,1)+1),  ");
		query.append(" 		(instr(sys_connect_by_path(t.de_menu_html, '*')|| '*','*',1,2))-2) as deMenuHtml,  ");
	    
		query.append("		'Y'    as stEsElegido, ");
		query.append("		level  as nulevel 	 ");		
		query.append("	from opcion_menu t  ");
		query.append("	where t.st_opcion_menu = '").append(CLPConstantes.ST_VERDADERO).append("' ");
		query.append("		and t.id in (").append(opcionesMenu).append(") ");
		
		if(depedenciasSuperiores){
			query.append("	connect by  prior t.id = t.co_opcion_menu_padre");
		}
		
		query.append("	order by level");
		
		return findListOfBeans(query.toString(), null, OpcionMenu.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<OpcionMenu> obtenerTodasOpcionesMenu(){
		
		StringBuffer query = new StringBuffer();
		query.append("select om.id as id,");
		query.append("		om.de_opcion_menu as  deOpcionMenu,");
		query.append("		om.de_menu_html as  deMenuHtml,");
		query.append("		om.de_ancho  as  deAncho,");
		query.append("		om.nu_nivel as  nuNivel,");
		query.append("		om.co_opcion_menu_padre as  coOpcionMenuPadre,");
		query.append("		om.nu_orden as  nuOrden  ");
		query.append("	from opcion_menu  om left outer join opcion_menu omp on om.co_opcion_menu_padre = omp.id  ");
		query.append("	where  om.st_opcion_menu = '").append(CLPConstantes.ST_VERDADERO).append("' ");
		query.append("		and om.de_menu_html is not null  ");
		query.append("	order by omp.nu_nivel asc nulls first, omp.nu_orden asc, om.nu_nivel asc, om.nu_orden asc ");
		
		return findListOfBeans(query.toString(), null, OpcionMenu.class);
	}

	@SuppressWarnings("unchecked")
	public List<OpcionMenu> obtenerTodosRolesMenuOpcion() {

		StringBuffer query = new StringBuffer();
		query.append(" select om.de_menu_html as deMenuHtml, r.name as deName "); 
		query.append(" from role_opcion_menu rom");
		query.append(" join role r on rom.co_role = r.id");
		query.append(" join opcion_menu om on rom.co_opcion_menu = om.id");
		query.append(" left outer join opcion_menu omp");
		query.append("		on om.co_opcion_menu_padre = omp.id");
		query.append(" where om.st_opcion_menu    = '").append(CLPConstantes.ST_VERDADERO).append("'");
		query.append(" and om.de_menu_html       is not null");
		query.append(" order by omp.nu_nivel asc nulls first,");
		query.append(" omp.nu_orden asc,");
		query.append(" om.nu_nivel asc,");
		query.append(" om.nu_orden asc ");
		
		return findListOfBeans(query.toString(), null, OpcionMenu.class);
	}
}
