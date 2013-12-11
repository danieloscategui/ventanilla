package com.pe.pgn.clubpgn.webapp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pe.pgn.clubpgn.Constants;
import com.pe.pgn.clubpgn.common.CLPConstantes;
import com.pe.pgn.clubpgn.dao.UserDao;
import com.pe.pgn.clubpgn.domain.ClpbEstacion;
import com.pe.pgn.clubpgn.domain.beans.BNEmpresaAfiliadora;
import com.pe.pgn.clubpgn.model.LabelValue;
import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.model.User;
import com.pe.pgn.clubpgn.service.EmpresaAfiliadoraService;
import com.pe.pgn.clubpgn.service.LookupManager;
import com.pe.pgn.clubpgn.service.RoleManager;
import com.pe.pgn.clubpgn.service.UserExistsException;
import com.pe.pgn.clubpgn.service.UserManager;
import com.pe.pgn.clubpgn.util.AeSimpleSHA1;

/**
 * Implementation of <strong>SimpleFormController</strong> that interacts with
 * the {@link UserManager} to retrieve/persist values to the database.
 *
 * <p><a href="UserFormController.java.html"><i>View Source</i></a>
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */

@Controller
@RequestMapping("/userform.*")
public class UserFormController extends BaseFormController {
    private RoleManager roleManager;

    @Autowired
    public void setRoleManager(RoleManager roleManager) {
        this.roleManager = roleManager;
    }
    
    @Autowired
    private LookupManager lookupManager;

    @Autowired
    private EmpresaAfiliadoraService empresaAfiliadoraService;

    public UserFormController() {
        setCancelView("redirect:mainMenu.html");
        setSuccessView("redirect:admin/users.html");
    }

    @ModelAttribute
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    protected User showForm(HttpServletRequest request, HttpServletResponse response)
    throws Exception {
        // If not an administrator, make sure user is not trying to add or edit another user
        if (!request.isUserInRole(Constants.ADMIN_ROLE) && !isFormSubmission(request)) {
            if (isAdd(request) || request.getParameter("id") != null) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                log.warn("User '" + request.getRemoteUser() + "' is trying to edit user with id '" +
                         request.getParameter("id") + "'");

                throw new AccessDeniedException("You do not have permission to modify other users.");
            }
        }

        if (!isFormSubmission(request)) {
            String userId = request.getParameter("id");

            // if user logged in with remember me, display a warning that they can't change passwords
            log.debug("checking for remember me login...");

            AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
            SecurityContext ctx = SecurityContextHolder.getContext();

            if (ctx.getAuthentication() != null) {
                Authentication auth = ctx.getAuthentication();

                if (resolver.isRememberMe(auth)) {
                    request.getSession().setAttribute("cookieLogin", "true");

                    // add warning message
                    saveMessage(request, getText("userProfile.cookieLogin", request.getLocale()));
                }
            }

            User user;
            if (userId == null && !isAdd(request)) {
                user = getUserManager().getUserByUsername(request.getRemoteUser());
            } else if (!StringUtils.isBlank(userId) && !"".equals(request.getParameter("version"))) {
                user = getUserManager().getUser(userId);
                
            } else {
                user = new User();
                user.addRole(new Role(Constants.USER_ROLE));
            }
            
            List<ClpbEstacion> estaciones = getUserManager().obtenerEstaciones();
            request.getSession().setAttribute("estaciones", estaciones);
            
            List<LabelValue> roles = lookupManager.getAllRoles();
            request.getSession().setAttribute(Constants.AVAILABLE_ROLES, roles);

            List<BNEmpresaAfiliadora> empresas = empresaAfiliadoraService.listarTodasLasEmpresasAfiliadoras();
    		request.getSession().setAttribute("empresas", empresas);

            return user;
        } else {
            // populate user object from database, so all fields don't need to be hidden fields in form
            return getUserManager().getUser(request.getParameter("id"));
        }
    }


    @RequestMapping(method = RequestMethod.POST)
    public String onSubmit(User user, BindingResult errors, HttpServletRequest request,
                                 HttpServletResponse response)
    throws Exception {
        if (request.getParameter("cancel") != null) {
            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                return getCancelView();
            } else {
                return getSuccessView();
            }
        }

        if (validator != null) { // validator is null during testing
            validator.validate(user, errors);

            if (errors.hasErrors() && request.getParameter("delete") == null) { // don't validate when deleting
                return "/userform";
            }
        }

        log.debug("entering 'onSubmit' method...");

        Locale locale = request.getLocale();

        if (request.getParameter("delete") != null) {
            getUserManager().removeUser(user.getId().toString());
            saveMessage(request, getText("user.deleted", user.getFullName(), locale));
            return getSuccessView();
        } else {

            // only attempt to change roles if user is admin for other users,
            // showForm() method will handle populating
            if (request.isUserInRole(Constants.ADMIN_ROLE)) {
                String[] userRoles = request.getParameterValues("userRoles");

                if (userRoles != null) {
                    user.getRoles().clear();
                    for (String roleName : userRoles) {
                        user.addRole(roleManager.getRole(roleName));
                    }
                }
            }

            Integer originalVersion = user.getVersion();

            try {
            	User userx = roleManager.getUserByUsername(user.getUsername());
            	if(userx!=null){
            		if(!userx.getPassword().equals(user.getPassword())){
                		user.setPassword(AeSimpleSHA1.SHA1(user.getPassword()));
                	}
            	}else{
            		user.setPassword(AeSimpleSHA1.SHA1(user.getPassword()));
            	}            	            	
            	//user.setOriginalPassword(user.getOriginalPassword());//new
            	//String pass = AeSimpleSHA1.SHA1(user.getOriginalPassword()); //new            	
            	//user.setPassword(pass);//new            	
                getUserManager().saveUser(user);
            } catch (AccessDeniedException ade) {
                // thrown by UserSecurityAdvice configured in aop:advisor userManagerSecurity
                log.warn(ade.getMessage());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return null;
            } catch (UserExistsException e) {
                errors.rejectValue("username", "errors.existing.user",
                                   new Object[] {user.getUsername(), user.getEmail()}, "duplicate user");

                // reset the version # to what was passed in
                user.setVersion(originalVersion);

                return "/userform";
            }

            if (!StringUtils.equals(request.getParameter("from"), "list")) {
                saveMessage(request, getText("user.saved", user.getFullName(), locale));

                // return to main Menu
                return getCancelView();
            } else {
                if (StringUtils.isBlank(request.getParameter("version"))) {
                    saveMessage(request, getText("user.added", user.getFullName(), locale));

                    // Send an account information e-mail
                    // Aun no podemos enviar correos :(
//                    message.setSubject(getText("signup.email.subject", locale));

//                    try {
//                        sendUserMessage(user, getText("newuser.email.message", user.getFullName(), locale),
//                                        RequestUtil.getAppURL(request));
//                    } catch (MailException me) {
//                        saveError(request, me.getCause().getLocalizedMessage());
//                    }

                    return getSuccessView();
                } else {
                    saveMessage(request, getText("user.updated.byAdmin", user.getFullName(), locale));
                }
            }
        }

        return getSuccessView();
    }
    
    
//	private void setEmpresasAfiliadoras(HttpServletRequest request) {
//		
//		List<BNEmpresaAfiliadora> empresasAfiliadoras = new ArrayList<BNEmpresaAfiliadora>();
//		BNEmpresaAfiliadora empresaAfiliadora = new BNEmpresaAfiliadora();
//		empresaAfiliadora.setCoEmpresaAfiliadora(BigDecimal.ZERO);
//		empresaAfiliadora.setDeEmpresaAfiliadora(CLPConstantes.ITEM_DEFAULT);
//		empresasAfiliadoras.add(empresaAfiliadora);
//	}

    private boolean isFormSubmission(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("post");
    }

    protected boolean isAdd(HttpServletRequest request) {
        String method = request.getParameter("method");
        return (method != null && method.equalsIgnoreCase("add"));
    }
}
