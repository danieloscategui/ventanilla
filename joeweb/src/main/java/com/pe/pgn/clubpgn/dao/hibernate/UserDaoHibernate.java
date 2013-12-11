package com.pe.pgn.clubpgn.dao.hibernate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.pe.pgn.clubpgn.dao.UserDao;
import com.pe.pgn.clubpgn.model.User;
import com.pe.pgn.clubpgn.webapp.util.ValidationUtil;

import java.util.List;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *   Modified by <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 *   Extended to implement Acegi UserDetailsService interface by David Carter david@carter.net
 *   Modified by <a href="mailto:bwnoll@gmail.com">Bryan Noll</a> to work with 
 *   the new BaseDaoHibernate implementation that uses generics.
*/
@Repository("userDao")
public class UserDaoHibernate extends GenericDaoHibernate<User, Long> implements UserDao, UserDetailsService {

    /**
     * Constructor that sets the entity to User.class.
     */
    public UserDaoHibernate() {
        super(User.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<User> getUsers() {
        return getHibernateTemplate().find("from User u order by upper(u.username)");
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) {
        if (log.isDebugEnabled())
            log.debug("user's id: " + user.getId());
        getHibernateTemplate().saveOrUpdate(user);
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
        return user;
    }

    /**
     * Overridden simply to call the saveUser method. This is happenening 
     * because saveUser flushes the session and saveObject of BaseDaoHibernate 
     * does not.
     *
     * @param user the user to save
     * @return the modified user (with a primary key set if they're new)
     */
    @Override
    public User save(User user) {
        return this.saveUser(user);
    }

    /** 
     * {@inheritDoc}
    */
	@SuppressWarnings("rawtypes")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List users = getHibernateTemplate().find("from User where username=?", username);
        if (users == null || users.isEmpty()) {
            throw new UsernameNotFoundException("user '" + username + "' not found...");
        } else {
            return (UserDetails) users.get(0);
        }
    }

    /** 
     * {@inheritDoc}
    */
	@SuppressWarnings("rawtypes")
	public String getUserPassword(String username) {

        List list = getHibernateTemplate().find(" select password from User where username=?", username);
        if(!list.isEmpty()){
        	return list.get(0).toString();
        }
        return "";
    }
    
	@SuppressWarnings("rawtypes")
	public String getUsernameById(Long id){
    	
    	 List list = getHibernateTemplate().find(" select username from User where id=?", id);
         if(!list.isEmpty()){
         	return list.get(0).toString();
         }
         return "";
    }

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		
		List<User> users = getHibernateTemplate().find("from User where username=?", username);
        if (ValidationUtil.validateList(users)) {
            return users.get(0);
        }
        
        return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getUserByUsernameAndPassword(String username, String password) {
		List<User> users = getHibernateTemplate().find("from User where username=? and password=?", username, password);
        if (ValidationUtil.validateList(users)) {
            return users.get(0);
        }        
        return null;
	}
}
