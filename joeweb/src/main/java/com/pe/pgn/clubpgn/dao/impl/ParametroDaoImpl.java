package com.pe.pgn.clubpgn.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.dao.ParametroDao;
import com.pe.pgn.clubpgn.dao.hibernate.GenericDaoHibernate;
import com.pe.pgn.clubpgn.domain.ClpbParametro;
import com.pe.pgn.clubpgn.security.LDAPConstants;
import com.pe.pgn.clubpgn.webapp.util.ValidationUtil;

@Repository("parametroDao")
public class ParametroDaoImpl extends GenericDaoHibernate<ClpbParametro, Long> 
			implements ParametroDao {
	
	public ParametroDaoImpl(){
		super(ClpbParametro.class);
	}
	
	@SuppressWarnings("rawtypes")
	public ClpbParametro obtenerParametro(String deParametro){
		
		StringBuffer query = new StringBuffer();
		query.append("select de_parametro as deParametro, ");
		query.append("		va_valor as vaValor ");
		query.append("	from clpb_parametro ");
		query.append("	where de_parametro = '").append(deParametro).append("' ");
		query.append("		and st_parametro = '").append(CLPConstantes.ST_VERDADERO).append("' ");
		
		List lista = findListOfBeans(query.toString(), null, ClpbParametro.class);
		if(!lista.isEmpty()){
			
			return (ClpbParametro)lista.get(0);
		}
		
		return null;		
	}

	@SuppressWarnings("unchecked")
	public List<ClpbParametro> obtenerParametrosLDAP() {
		
		StringBuffer query = new StringBuffer();
		query.append("select de_parametro as deParametro, ");
		query.append("		va_valor as vaValor ");
		query.append("	from clpb_parametro ");
		query.append("	where de_parametro in ('"+LDAPConstants.HOST+"'," +
		"'"+LDAPConstants.PORT+"','"+LDAPConstants.BASE_DN+"','"+LDAPConstants.UID+"') ");
		query.append("	and st_parametro = '").append(CLPConstantes.ST_VERDADERO).append("' ");
		
		List<ClpbParametro> lista = findListOfBeans(query.toString(), null, ClpbParametro.class);
		if(ValidationUtil.validateList(lista)){
			return lista;
		}
		
		return null;
	}
}
