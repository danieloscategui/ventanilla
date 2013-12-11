package com.pe.pgn.clubpgn.service;

import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.model.User;

import java.util.List;

/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface RoleManager extends GenericManager<Role, Long> {
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	List getRoles(Role role);

    /**
     * {@inheritDoc}
     */
    Role getRole(String rolename);

    /**
     * {@inheritDoc}
     */
    Role saveRole(Role role);

    /**
     * {@inheritDoc}
     */
    void removeRole(String rolename);
    
    public Role getRole(Long id);
        
    public List<Role> getRoleList();
    
    public void removeRole(Long id);
    
    public boolean esRoleConDependencias(Long id);
    
    public boolean esRoleRepetido(Role role);
    
    public User getUserByUsername(String username);
    
}
