package com.pe.pgn.clubpgn.service.impl;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pe.pgn.clubpgn.Constants;
import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.dao.OpcionMenuDao;
import com.pe.pgn.clubpgn.dao.RoleDao;
import com.pe.pgn.clubpgn.model.OpcionMenu;
import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.model.User;
import com.pe.pgn.clubpgn.service.RoleManager;
import com.pe.pgn.clubpgn.util.StringUtil;
import com.pe.pgn.clubpgn.webapp.util.ValidationUtil;

/**
 * Implementation of RoleManager interface.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 */
@Service("roleManager")
public class RoleManagerImpl extends GenericManagerImpl<Role, Long> implements RoleManager {
    
	private RoleDao roleDao;
	
	@Autowired
	private OpcionMenuDao opcionMenuDao;

    @Autowired
    public RoleManagerImpl(RoleDao roleDao) {
        super(roleDao);
        this.roleDao = roleDao;
    }

    /**
     * {@inheritDoc}
     */
    public List<Role> getRoles(Role role) {
        return dao.getAll();
    }

    /**
     * {@inheritDoc}
     */
    public Role getRole(String rolename) {
        return roleDao.getRoleByName(rolename);
    }

    /**
     * {@inheritDoc}
     */
    public Role saveRole(Role role) {
    	
    	String name = role.getName();
    	name = StringUtil.cleanCodeString(name);
    	role.setName(name);
    	
    	String opciones = "";
    	for(Iterator<OpcionMenu> iter = role.getOpcionesMenu().iterator(); iter.hasNext(); ){
    		
    		OpcionMenu opcionMenu = iter.next();
    		if(opcionMenu.isStElegido() || 
    			opcionMenu.getDeMenuHtml().equalsIgnoreCase(Constants.URL_LOGOUT) || 
    			opcionMenu.getDeMenuHtml().equalsIgnoreCase(Constants.URL_MAIN_MENU) ){
    			opciones = opciones.concat(opcionMenu.getId().toString()).concat(CLPConstantes.CARACTER_COMA);
    		} 		
    	}
    	
    	if(opciones.endsWith(CLPConstantes.CARACTER_COMA)){
    		opciones = opciones.substring(0, opciones.length() - 1);
    	}
    	
    	List<OpcionMenu> opcionesMenu = opcionMenuDao.obtenerDependenciasOpcionMenu(opciones, Boolean.TRUE);
    	role.setOpcionesMenu(opcionesMenu);    	
        return dao.save(role);
    }
    
    @SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
        return roleDao.getUserByUsername(username);
	}

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        roleDao.removeRole(rolename);
    }

	
	public Role getRole(Long id) {
		
		return roleDao.getRole(id);
	}

	
	public List<Role> getRoleList() {
		
		return roleDao.getRoleList();
	}

	
	public void removeRole(Long id) {
		roleDao.removeRole(id);
	}

	
	public boolean esRoleConDependencias(Long id) {
		
		return roleDao.esRoleConDependencias(id);
	}

	
	public boolean esRoleRepetido(Role role) {
		
		return roleDao.esRoleRepetido(role);
	}
}