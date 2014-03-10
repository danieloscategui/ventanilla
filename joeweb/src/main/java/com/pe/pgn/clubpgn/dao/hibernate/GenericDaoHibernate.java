package com.pe.pgn.clubpgn.dao.hibernate;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.hibernate.SessionFactory;

import com.pe.pgn.clubpgn.dao.GenericDao;
import com.pe.pgn.clubpgn.model.User;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>To register this class in your Spring context file, use the following XML.
 * <pre>
 *      &lt;bean id="fooDao" class="org.appfuse.dao.hibernate.GenericDaoHibernate"&gt;
 *          &lt;constructor-arg value="org.appfuse.model.Foo"/&gt;
 *      &lt;/bean&gt;
 * </pre>
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a>
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
    /**
     * Log variable for all child classes. Uses LogFactory.getLog(getClass()) from Commons Logging
     */
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
    private HibernateTemplate hibernateTemplate;
    private SessionFactory sessionFactory;

    /**
     * Constructor that takes in a class to see which type of entity to persist.
     * Use this constructor when subclassing.
     *
     * @param persistentClass the class type you'd like to persist
     */
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * Constructor that takes in a class and sessionFactory for easy creation of DAO.
     *
     * @param persistentClass the class type you'd like to persist
     * @param sessionFactory  the pre-configured Hibernate SessionFactory
     */
    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

	@Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    public List<T> getAll() {
        return hibernateTemplate.loadAll(this.persistentClass);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<T> getAllDistinct() {
        
		Collection result = new LinkedHashSet(getAll());
        return new ArrayList(result);
    }

    /**
     * {@inheritDoc}
     */
    public T get(PK id) {
        T entity = (T) hibernateTemplate.get(this.persistentClass, id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
    }

    /**
     * {@inheritDoc}
     */
    public boolean exists(PK id) {
        T entity = (T) hibernateTemplate.get(this.persistentClass, id);
        return entity != null;
    }

    /**
     * {@inheritDoc}
     */
    public T save(T object) {
        return (T) hibernateTemplate.merge(object);
    }

    /**
     * {@inheritDoc}
     */
    public void remove(PK id) {
        hibernateTemplate.delete(this.get(id));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<T> findByNamedQuery(String queryName, Map<String, Object> queryParams) {
        String[] params = new String[queryParams.size()];
        Object[] values = new Object[queryParams.size()];
        
        int index = 0;
        for (String s : queryParams.keySet()) {
            params[index] = s;
            values[index++] = queryParams.get(s);
        }

        return hibernateTemplate.findByNamedQueryAndNamedParam(queryName, params, values);
    }
    
    
    /**
     * Utils methods for map results of queries to customized beans or java.Util.Map
     **/
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findListOfBeans(String sql, Object[] filterKeys, Class beanClass) {	
        return (List) executeQuery(sql, filterKeys, new BeanListHandler(beanClass));
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findListOfMaps(String sql, Object[] filterKeys) {
		return (List) executeQuery(sql, filterKeys, new MapListHandler());
	}
	  
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findListOfArrays(String sql, Object[] filterKeys) {
		return (List) executeQuery(sql, filterKeys, new ArrayListHandler());
	}
	
	@SuppressWarnings({"unchecked", "finally", "deprecation", "rawtypes" })
	public Object executeQuery(String sql, Object[] filterKeys, ResultSetHandler resultSetHandler) {
		
		filterKeys = filterKeys == null  ? new Object[]{} : filterKeys;
		Connection con = null;
		Object object = null;
		QueryRunner queryRunner = new QueryRunner();
        try {        	
           	con = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
           	object = queryRunner.query(con, sql, filterKeys, resultSetHandler);
            
        } catch (SQLException e) {
        	log.info(e.getMessage());
        } finally{
        	try {
				con.close();
			} catch (SQLException e) {
				log.info(e.getMessage());
			}
        	return object;
        }
    }	
	
	public User getUsuarioLogueado() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> obtenerEmpresaAfiliadora(JdbcTemplate jdbcTemplate){
		
		StringBuffer query = new StringBuffer();
		query.append("select u.co_empresa_afiliadora as coEmpresaAfiliadora,  ");
		query.append("		ea.de_empresa_afiliadora as deEmpresaAfiliadora,  ");
		query.append("		ea.co_programa as coPrograma  ");
		query.append("	from app_user u join clpb_empresa_afiliadora ea  ");
		query.append("    	on u.co_empresa_afiliadora = ea.id  ");
		query.append("	where u.username = ? ");
		
		 List<Map<String, Object>> empresas = findListOfMaps(query.toString(),
				new Object[]{getUsuarioLogueado().getUsername()});
		 if(empresas.isEmpty()){
			 return null;
		 }
		return empresas.get(0);
	}
	
	public boolean esUsuarioEmpresaAfiliadora(JdbcTemplate jdbcTemplate){
		
		Map<String, Object> empresa = obtenerEmpresaAfiliadora(jdbcTemplate);
		if(empresa != null && empresa.get("coEmpresaAfiliadora") != null){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
}
