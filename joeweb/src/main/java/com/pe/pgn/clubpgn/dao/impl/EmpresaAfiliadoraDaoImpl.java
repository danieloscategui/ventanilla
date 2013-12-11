package com.pe.pgn.clubpgn.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.dao.EmpresaAfiliadoraDao;
import com.pe.pgn.clubpgn.dao.hibernate.GenericDaoHibernate;
import com.pe.pgn.clubpgn.domain.ClpbEmpresaAfiliadora;
import com.pe.pgn.clubpgn.domain.beans.BNEmpresaAfiliadora;
import com.pe.pgn.clubpgn.domain.beans.BNProgramaDetalle;

@Repository
public class EmpresaAfiliadoraDaoImpl 
		extends GenericDaoHibernate<ClpbEmpresaAfiliadora, Long> 
			implements EmpresaAfiliadoraDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public EmpresaAfiliadoraDaoImpl(){
		super(ClpbEmpresaAfiliadora.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<BNEmpresaAfiliadora> listarTodasLasEmpresasAfiliadoras(){
		
		StringBuffer query = new StringBuffer();
		query.append("select ea.id as coEmpresaAfiliadora, ");
		query.append("		ea.de_empresa_afiliadora as deEmpresaAfiliadora, ");
		query.append("		ea.st_empresa_afiliadora as stStrEmpresaAfiliadora, ");
		query.append(" 		ea.co_programa as coPrograma,  ");
		query.append("		p.de_programa as dePrograma ");
		query.append("	from clpb_empresa_afiliadora ea join clpb_programa p on ea.co_programa = p.id ");
		query.append("	where ea.st_empresa_afiliadora = ? ");
		query.append("	order by ea.id desc ");
		
		List<BNEmpresaAfiliadora> list = findListOfBeans(query.toString(), 
				new Object[]{CLPConstantes.ST_VERDADERO}, BNEmpresaAfiliadora.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<BNProgramaDetalle> obtenerProgramasActivosParaEmpresasAfiliadoras(){
		
		StringBuffer query = new StringBuffer();
		query.append(" select id as id, ");
		query.append(" 		de_programa as descripcion ");
		query.append(" from clpb_programa  ");
		query.append(" where st_programa = ? ");
		
		List<BNProgramaDetalle> lista = findListOfBeans(query.toString(), 
				new Object[]{CLPConstantes.ST_VERDADERO}, BNProgramaDetalle.class);
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	public BNEmpresaAfiliadora obtenerEmpresaAfiliadoraPorId(BigDecimal coEmpresaAfiliadora){
		
		StringBuffer query = new StringBuffer();
		query.append(" select ea.id as coEmpresaAfiliadora, ");
		query.append(" 		ea.de_empresa_afiliadora as deEmpresaAfiliadora,  ");
		query.append(" 		ea.st_empresa_afiliadora as stStrEmpresaAfiliadora,  ");
		query.append(" 		ea.co_programa as coPrograma  ");
		query.append(" 	from clpb_empresa_afiliadora ea  ");
		query.append(" 	where ea.id = ? ");
		query.append(" 		and ea.st_empresa_afiliadora = ? ");

		List<BNEmpresaAfiliadora> list = findListOfBeans(query.toString(), 
				new Object[]{coEmpresaAfiliadora, CLPConstantes.ST_VERDADERO}, 
				BNEmpresaAfiliadora.class);
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	public void guardarEmpresaAfiliadora(BNEmpresaAfiliadora afiliadora){
		
		if(afiliadora.getCoEmpresaAfiliadora() == null){
			
			StringBuffer insert = new StringBuffer();
			insert.append("insert into clpb_empresa_afiliadora(");
			insert.append("		id, ");
			insert.append("		de_empresa_afiliadora, ");
			insert.append("		st_empresa_afiliadora, ");
			insert.append("		co_programa, ");
			insert.append("		co_usuario_creador, ");
			insert.append("		da_fecha_creacion) values(");
			insert.append("		seq_empresa_afiliadora.nextval, ?, ?, ?, ?, sysdate)");
			jdbcTemplate.update(insert.toString(), afiliadora.getDeEmpresaAfiliadora(),
					afiliadora.isStEmpresaAfiliadora() ? CLPConstantes.ST_VERDADERO : CLPConstantes.ST_FALSO,
							afiliadora.getCoPrograma(), getUsuarioLogueado().getUsername());
		} else {
			
			StringBuffer update = new StringBuffer();
			update.append("update clubpgn.clpb_empresa_afiliadora ");
			update.append(" set de_empresa_afiliadora = ?, ");
			update.append(" 	st_empresa_afiliadora = ?, ");
			update.append(" 	co_programa = ?, ");
			update.append(" 	co_usuario_modificador = ?, ");
			update.append(" 	da_fecha_modificacion = sysdate ");
			update.append(" where id = ? ");
			jdbcTemplate.update(update.toString(), afiliadora.getDeEmpresaAfiliadora(),
					afiliadora.isStEmpresaAfiliadora() ? CLPConstantes.ST_VERDADERO : CLPConstantes.ST_FALSO,
							afiliadora.getCoPrograma(), getUsuarioLogueado().getUsername(), 
							afiliadora.getCoEmpresaAfiliadora());
		}
	}
	
	public boolean esEmpresaAfiliadoraConDependencias(BigDecimal coEmpresaAfiliadora){
		
		StringBuffer query = new StringBuffer();
		query.append("select count(*) from clpb_cliente_persona ");
		query.append("where co_empresa_afiliadora = ? ");
		int count = jdbcTemplate.queryForInt(query.toString(), coEmpresaAfiliadora);
		
		query = new StringBuffer();
		query.append("select count(*) from app_user ");
		query.append("where co_empresa_afiliadora = ? ");
		count = count + jdbcTemplate.queryForInt(query.toString(), coEmpresaAfiliadora);
		
		if(count > 0){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public void eliminarEmpresaAfiliadora(BigDecimal coEmpresaAfiliadora){
		
		StringBuffer update = new StringBuffer();
		update.append("update clubpgn.clpb_empresa_afiliadora ");
		update.append(" set st_empresa_afiliadora = ?, ");
		update.append(" 	co_usuario_modificador = ?, ");
		update.append(" 	da_fecha_modificacion = sysdate ");
		update.append(" where id = ? ");
		jdbcTemplate.update(update.toString(), CLPConstantes.ST_FALSO,
				getUsuarioLogueado().getUsername(), coEmpresaAfiliadora);
	}
}
