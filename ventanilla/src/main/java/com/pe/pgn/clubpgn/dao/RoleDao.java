package com.pe.pgn.clubpgn.dao;

import java.util.List;

import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.model.User;

/**
 * Role Data Access Object (DAO) interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public interface RoleDao extends GenericDao<Role, Long> {
    /**
     * Gets role information based on rolename
     * @param rolename the rolename
     * @return populated role object
     */
    Role getRoleByName(String rolename);

    /**
     * Removes a role from the database by name
     * @param rolename the role's rolename
     */
    void removeRole(String rolename);
    
    public Role getRole(Long id);
    
    public void saveRole(Role role);
    
    public List<Role> getRoleList();
    
    public void removeRole(Long id);
    
    public boolean esRoleConDependencias(Long id);
    
    public boolean esRoleRepetido(Role role);

	public List<Role> getRolesByCoUser(Long id);
	
	public User getUserByUsername(String username);
}
