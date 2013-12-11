package com.pe.pgn.clubpgn.dao.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.dao.EstacionDao;
import com.pe.pgn.clubpgn.dao.hibernate.GenericDaoHibernate;
import com.pe.pgn.clubpgn.domain.ClpbEstacion;
import com.pe.pgn.clubpgn.domain.ClpmUbigeo;
import com.pe.pgn.clubpgn.domain.beans.BNEstacion;

@Repository("estacionaDao")
public class EstacionDaoImpl extends GenericDaoHibernate<ClpbEstacion, Long> implements
		EstacionDao {

	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public EstacionDaoImpl() {
		super(ClpbEstacion.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<ClpbEstacion> obtenerEstaciones(){
		
		Criteria criteria = DetachedCriteria.forClass(ClpbEstacion.class)
			.getExecutableCriteria(getSessionFactory().getCurrentSession());
		
		criteria.add(Restrictions.eq("stEstacion", Boolean.TRUE));
		criteria.addOrder(Order.asc("deEstacion"));
		
		return (List<ClpbEstacion>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<ClpbEstacion> busquedaEstacion(String deEstacion){
		
		Criteria criteria = DetachedCriteria.forClass(ClpbEstacion.class)
		.getExecutableCriteria(getSessionFactory().getCurrentSession());
	
		criteria.add(Restrictions.eq("stEstacion", Boolean.TRUE));
		criteria.add(Restrictions.ilike("deEstacion", "%"+deEstacion+"%"));
		criteria.addOrder(Order.asc("deEstacion"));
		
		return (List<ClpbEstacion>) criteria.list();
	}
	
	public ClpbEstacion obtenerEstacion(Long id){

		ClpbEstacion estacion = get(id);
		if(estacion.getClpmUbigeo()!=null){
			getDescriptionUbigeo(estacion.getClpmUbigeo());
			obtenerCodigosDeUbigeo(estacion.getClpmUbigeo().getId().toString(),estacion);
		}
		
		return estacion;
	}
	
	@SuppressWarnings("rawtypes")
	private void getDescriptionUbigeo(ClpmUbigeo ubigeo) {
		
		StringBuffer query = new StringBuffer();
		query.append(" select ");
		query.append(" de_distrito,de_provincia,de_departamento ");
		query.append(" from clpm_ubigeo ");
		query.append(" where id = ").append(ubigeo.getId());
		
		List list = findListOfMaps(query.toString(),null);
		if(list != null && list.size() > 0){
			
			Map objMap = (HashMap)list.get(0);						
			ubigeo.setDeDepartamento(""+objMap.get("de_departamento"));
			ubigeo.setDeDistrito(""+objMap.get("de_distrito"));
			ubigeo.setDeProvincia(""+objMap.get("de_provincia"));
		}
	}

	@SuppressWarnings("unchecked")
	private void obtenerCodigosDeUbigeo(String coUbigeo, ClpbEstacion bean) {
		
		StringBuffer query = new StringBuffer();
		query.append(" select ");
		query.append(" co_departamento 	as coDepartamento, ");
		query.append(" co_provincia 	as coProvincia, ");
		query.append(" co_distrito 		as coDistrito ");
		query.append(" from clpm_ubigeo ");
		query.append(" where id = ").append(coUbigeo);
		
		List<ClpbEstacion> list = findListOfBeans(query.toString(), null, ClpbEstacion.class);
		ClpbEstacion estacion = (ClpbEstacion)list.get(0);
		
		bean.setCoDepartamento(estacion.getCoDepartamento());
		bean.setCoProvincia(estacion.getCoProvincia());
		bean.setCoDistrito(estacion.getCoDistrito());
	}
	
	
	public void guardarEstacion(ClpbEstacion estacion){
		
		if(estacion.getId() == null){			
			estacion.setStEstacion(Boolean.TRUE);
			estacion.setCoUsuarioCreador(getUsuarioLogueado().getUsername());
			estacion.setDaFechaCreacion(Calendar.getInstance());
		}
		else{
			estacion.setCoCodeEstacionPgn(estacion.getId().toString());
			estacion.setCoUsuarioModificador(getUsuarioLogueado().getUsername());
			estacion.setDaFechaModificacion(Calendar.getInstance());
		}
		
		getHibernateTemplate().saveOrUpdate(estacion);
		
		estacion.setCoCodeEstacionPgn(estacion.getId().toString());
		getHibernateTemplate().update(estacion);
		getHibernateTemplate().flush();
	}
	
	public void eliminarEstacion(Long id){
		
		//Eliminacion Logica
		ClpbEstacion estacion=getHibernateTemplate().get(ClpbEstacion.class, id);
		estacion.setStEstacion(Boolean.FALSE);
		
		getHibernateTemplate().update(estacion);
	    getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public List<BNEstacion> obtenerTodoEstaciones() {

		StringBuffer query = new StringBuffer();
		query.append(" select ");
		query.append(" 	 id   ");
		query.append("	,de_estacion as deEstacion ");
		query.append(" from clpb_estacion ");
		query.append(" where 1 = 1 ");
		query.append(" 	and st_estacion = '").append(CLPConstantes.ST_VERDADERO).append("'");
		query.append(" order by de_estacion asc ");
		
		return findListOfBeans(query.toString(), null, BNEstacion.class);
	}

	public Integer validaNombreEstacion(Long id, String deEstacion) {
		StringBuffer query = new StringBuffer();
		query.append(" select count(id) from clpb_estacion ");
		query.append(" where 1 = 1 ");
		query.append(" and st_estacion = '").append(CLPConstantes.ST_VERDADERO).append("'");
		query.append(" and upper(trim(de_estacion) )=  upper(trim('" +deEstacion + "'))");
		if (id>0){
			query.append(" and  id <> ").append(id);
		}
		return jdbcTemplate.queryForInt(query.toString());
	}

	public Integer validaCodigoCofide(Long id, String codigoCofide) {
		StringBuffer query = new StringBuffer();
		query.append(" select count(id) from clpb_estacion ");
		query.append(" where 1 = 1 ");
		query.append(" and st_estacion = '").append(CLPConstantes.ST_VERDADERO).append("'");
		query.append(" and upper(trim(CO_CODIGO_COFIDE))=  upper(trim(" + codigoCofide + ")) ");
		if (id>0){
			query.append(" and  id <> ").append(id);
		}
		return jdbcTemplate.queryForInt(query.toString());
	}
	
	
	
	
}
