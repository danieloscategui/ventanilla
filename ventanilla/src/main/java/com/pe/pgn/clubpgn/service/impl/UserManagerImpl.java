package com.pe.pgn.clubpgn.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pe.pgn.clubpgn.dao.EstacionDao;
import com.pe.pgn.clubpgn.dao.UserDao;
import com.pe.pgn.clubpgn.domain.ClpbEstacion;
import com.pe.pgn.clubpgn.model.User;
import com.pe.pgn.clubpgn.service.UserExistsException;
import com.pe.pgn.clubpgn.service.UserManager;
import com.pe.pgn.clubpgn.service.UserService;


/**
 * Implementation of UserManager interface.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */

@Service("userManager")
public class UserManagerImpl extends GenericManagerImpl<User, Long> implements UserManager, UserService {
    
	private PasswordEncoder passwordEncoder;
    private UserDao userDao;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.dao = userDao;
        this.userDao = userDao;
    }
    
    @Autowired
    private EstacionDao estacionDao;

    /**
     * {@inheritDoc}
     */
    public User getUser(String userId) {
        return userDao.get(new Long(userId));
    }

    /**
     * {@inheritDoc}
     */
    public List<User> getUsers() {
        return userDao.getAllDistinct();
    }

    /**
     * {@inheritDoc}
     */
    public User saveUser(User user) throws UserExistsException {

        if (user.getVersion() == null) {
            // if new user, lowercase userId
            user.setUsername(user.getUsername().toLowerCase());
        }
        
        if(user.getClpbEstacion().getId().longValue() < 0){
        	user.setClpbEstacion(null);
        }

        if(user.getClpbEmpresaAfiliadora().getId().equals(BigDecimal.ONE.negate())){
        	user.setClpbEmpresaAfiliadora(null);
        }

        try {
            return userDao.saveUser(user);
        } catch (DataIntegrityViolationException e) {
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        } catch (JpaSystemException e) { // needed for JPA
            //e.printStackTrace();
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' already exists!");
        }catch (Exception e) { 
            log.warn(e.getMessage());
            throw new UserExistsException("User '" + user.getUsername() + "' present problems to save!");
        }
    }

    /**
     * {@inheritDoc}
     */
    public void removeUser(String userId) {
        log.debug("removing user: " + userId);
        userDao.remove(new Long(userId));
    }

    /**
     * {@inheritDoc}
     *
     * @param username the login name of the human
     * @return User the populated user object
     * @throws UsernameNotFoundException thrown when username not found
     */
    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return (User) userDao.loadUserByUsername(username);
    }
    
    public List<ClpbEstacion> obtenerEstaciones(){
    	return estacionDao.obtenerEstaciones();
    }
}
