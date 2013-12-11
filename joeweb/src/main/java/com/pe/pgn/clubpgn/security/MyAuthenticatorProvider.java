package com.pe.pgn.clubpgn.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import com.pe.pgn.clubpgn.dao.ParametroDao;
import com.pe.pgn.clubpgn.dao.RoleDao;
import com.pe.pgn.clubpgn.dao.UserDao;
import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.model.User;
import com.pe.pgn.clubpgn.util.AeSimpleSHA1;

public class MyAuthenticatorProvider implements AuthenticationProvider{

	private UserDao userDao;
	private RoleDao roleDao;
	private ParametroDao parametroDao;
	
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		String username = authentication.getPrincipal().toString();	  
		String password = authentication.getCredentials().toString();
		
		boolean isAuthenticated = true;//LDAPProvider.instance().validarAutenticacionLDAP(username, password, parametroDao);
		if(isAuthenticated == false){
			throw new BadCredentialsException("LDAP: Credentials are not valid, user " + username);
		}
		
		//User usuario = userDao.getUserByUsername(username);
		//new ---------------------------------------------------
		try {
			password = AeSimpleSHA1.SHA1(password);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//-------------------------------------------------------
		User usuario = userDao.getUserByUsernameAndPassword(username, password);
		if(usuario == null){
			throw new BadCredentialsException("LDAP: You are not authorized, user " + username);
		}
		
		List<Role> roles = roleDao.getRolesByCoUser(usuario.getId());
		Collection<GrantedAuthority> collection = new HashSet<GrantedAuthority>();
		
		for (int i=0; i<roles.size();i++) {
			Role role = roles.get(i);
			GrantedAuthority autoridad = new GrantedAuthorityImpl(role.getName());
			collection.add(autoridad);
		}
		
		UsernamePasswordAuthenticationToken result = new 
		UsernamePasswordAuthenticationToken(usuario, password, collection);
		result.setDetails(usuario);
		return result;
	}

	public boolean supports(Class<? extends Object> authentication) {
		return true;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	public void setParametroDao(ParametroDao parametroDao) {
		this.parametroDao = parametroDao;
	}
}
