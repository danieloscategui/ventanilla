package com.pe.pgn.clubpgn.security;

import java.util.Enumeration;
import java.util.List;

import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPReferralException;
import netscape.ldap.LDAPSearchResults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.pe.pgn.clubpgn.dao.ParametroDao;
import com.pe.pgn.clubpgn.domain.ClpbParametro;

@Service("LDAPProvider")
public class LDAPProvider {

	@Autowired
	private ParametroDao parametroDao;
	
	public static LDAPProvider instance(){
		return new LDAPProvider();
	}
	
	public LDAPUsuario getUserLDAP(String username){
		
		List<ClpbParametro> listParameter = parametroDao.obtenerParametrosLDAP();
		LDAPConnection connection = new LDAPConnection();

		int port = 0;
		String uid = "";
		String host = "";
		String basedn= "";
		
		for (ClpbParametro clpbParametro : listParameter) {
			
			if(LDAPConstants.UID.equals(clpbParametro.getDeParametro()))
				uid = clpbParametro.getVaValor();
			
			if(LDAPConstants.HOST.equals(clpbParametro.getDeParametro()))
				host = clpbParametro.getVaValor();
			
			if(LDAPConstants.PORT.equals(clpbParametro.getDeParametro()))
				port = Integer.parseInt(clpbParametro.getVaValor());
			
			if(LDAPConstants.BASE_DN.equals(clpbParametro.getDeParametro()))
				basedn = clpbParametro.getVaValor();
		}
		
		LDAPUsuario userLDAP = null;		
		String userNameLDAP = uid+ username; 
		
		try {
			
			connection.connect(host,port);
            String[] filters = LDAPConstants.attrs;
            
			LDAPSearchResults results = connection.search(
			basedn, LDAPConnection.SCOPE_SUB, 
			userNameLDAP, filters, false);

			if(results.hasMoreElements()){
				
                LDAPEntry entry = results.next();                
                userLDAP = new LDAPUsuario();
                userLDAP.setUserId(userNameLDAP);
                userLDAP = obtenerDatosUsuario(filters, entry);                
            }			
			
		} catch ( LDAPReferralException e ) {
		} catch ( LDAPException e ){
		}

		return userLDAP;
	}
	
	private void throwException(String username) {
		throw new BadCredentialsException("LDAP: Credentials are not valid, user " + username);	
	}

	@SuppressWarnings("rawtypes")
	private LDAPUsuario obtenerDatosUsuario(String[] filtros, LDAPEntry entry){
		
		if(filtros == null || entry == null || filtros.length < 1)
		return null;
		
		LDAPUsuario usuario = new LDAPUsuario();		
		for ( int i = 0; i < filtros.length; i++){
			
	       LDAPAttribute attr = entry.getAttribute(filtros[i]);
	       if(attr != null){
	    	   
	    	   Enumeration enumVals = attr.getStringValues();
		       while((enumVals != null) && enumVals.hasMoreElements()){
		    	   
		         String val = (String)enumVals.nextElement();
		         switch(i){
		           case 0 :{                             
                     usuario.setDeNombre(val);
                     break;
                   }
		           case 1 :{
		         	  usuario.setDeApellido(val);
		              break;
		           }
		           case 2 :{
		         	  usuario.setDeMail(val);
		              break;
			       }
		           case 3 :{
		        	  usuario.setUserName(val);
		        	  break;
		           }
		         }
		       }
	       }
        }
		
		return usuario;
	}
	
	
	public boolean validarAutenticacionLDAP(String username, String password, ParametroDao dao){
		
		List<ClpbParametro> listParameter = dao.obtenerParametrosLDAP();
		LDAPConnection connection = new LDAPConnection();

		int port = 0;
		String uid = "";
		String host = "";
		String basedn= "";
		
		for (ClpbParametro clpbParametro : listParameter) {
			
			if(LDAPConstants.UID.equals(clpbParametro.getDeParametro()))
				uid = clpbParametro.getVaValor();
			
			if(LDAPConstants.HOST.equals(clpbParametro.getDeParametro()))
				host = clpbParametro.getVaValor();
			
			if(LDAPConstants.PORT.equals(clpbParametro.getDeParametro()))
				port = Integer.parseInt(clpbParametro.getVaValor());
			
			if(LDAPConstants.BASE_DN.equals(clpbParametro.getDeParametro()))
				basedn = clpbParametro.getVaValor();
		}
		
		try{
			
			String userLDAP = uid+ username;
    		connection.connect(host,port);
            LDAPSearchResults searchResult = connection.search(basedn,
            LDAPConnection.SCOPE_SUB, userLDAP, null, false);

            while(searchResult.hasMoreElements()){
                LDAPEntry entry = searchResult.next();
                String dn = entry.getDN();
                connection.authenticate(dn, password);
                return connection.isAuthenticated();
            }
        
    	}catch(LDAPReferralException e){
        	  throwException(username);
        }catch( LDAPException e){
        	  throwException(username);
        }  		           

        return false;
	}
}
