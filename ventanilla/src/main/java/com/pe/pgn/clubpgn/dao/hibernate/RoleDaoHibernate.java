package com.pe.pgn.clubpgn.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.pe.pgn.clubpgn.dao.RoleDao;
import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.model.User;
import com.pe.pgn.clubpgn.webapp.util.ValidationUtil;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve Role objects.
 *
 * @author <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> 
 */
@Repository("roleDao")
public class RoleDaoHibernate extends GenericDaoHibernate<Role, Long> implements RoleDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
 
    /**
     * Constructor to create a Generics-based version using Role as the entity
     */
    public RoleDaoHibernate() {
        super(Role.class);
    }

    /**
     * {@inheritDoc}
     */
	@SuppressWarnings("rawtypes")
	public Role getRoleByName(String rolename) {
        List roles = getHibernateTemplate().find("from Role where name=?", rolename);
        if (roles.isEmpty()) {
            return null;
        } else {
            return (Role) roles.get(0);
        }
    }
	
	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		List<User> users = getHibernateTemplate().find("from User where username=?", username);
        if (ValidationUtil.validateList(users)) {
            return users.get(0);
        }
        
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void removeRole(String rolename) {
        Object role = getRoleByName(rolename);
        getHibernateTemplate().delete(role);
    }
    
    public Role getRole(Long id){
    	
    	Role role = get(id);
    	return role;
    }
    
    public void saveRole(Role role){
    	
    	getHibernateTemplate().saveOrUpdate(role);
    	getHibernateTemplate().flush();
    }
    
    @SuppressWarnings("unchecked")
	public List<Role> getRoleList(){
    
    	 List<Role> roles = getHibernateTemplate().find("from Role");
    	 return roles;
    }
    
    public void removeRole(Long id){
    	
    	StringBuffer delete = new StringBuffer();
    	delete.append(" delete from role_opcion_menu where co_role = ").append(id);
    	
    	jdbcTemplate.update(delete.toString());
    	
    	delete = new StringBuffer();
    	delete.append(" delete from role where id = ").append(id);
    	    	
    	jdbcTemplate.update(delete.toString());
    }
    
    public boolean esRoleConDependencias(Long id){
    	
    	StringBuffer query = new StringBuffer();
    	query.append("select count(*) ");
    	query.append("	from user_role ");
    	query.append("	where role_id = ").append(id);
    	
    	int count = jdbcTemplate.queryForInt(query.toString());
    	if(count > 0){
    		return true;
    	}
    	return false;
    }
    
    public boolean esRoleRepetido(Role role){
    	
    	StringBuffer query = new StringBuffer();
    	query.append("select count(*) ");
    	query.append("	from role where upper(trim(name)) = '").append(role.getName()).append("' ");
    	
    	if(role.getId() != null){
    		query.append("		and id != ").append(role.getId());
    	}
    	
    	int count = jdbcTemplate.queryForInt(query.toString());
    	if(count > 0){
    		return true;
    	}
    	return false;
    }

	@SuppressWarnings("rawtypes")
	public List<Role> getRolesByCoUser(Long id) {

		StringBuffer query = new StringBuffer();
		query.append(" select r.id,r.name from user_role ur ");
		query.append(" join role r on r.id = ur.role_id ");
		query.append(" where ur.user_id = ").append(id);
		
		List<Role> listFinal = new ArrayList<Role>();
		List list = jdbcTemplate.queryForList(query.toString());
		
		if(ValidationUtil.validateList(list)){
			for (int i = 0; i < list.size(); i++) {
				
				Map map = (Map)list.get(i);
				Role bnrol = new Role();
				bnrol.setId(new Long(map.get("ID").toString()));
				bnrol.setName(map.get("NAME").toString());
				listFinal.add(bnrol);
			}
			
		}
		
		return listFinal;
	}
}
