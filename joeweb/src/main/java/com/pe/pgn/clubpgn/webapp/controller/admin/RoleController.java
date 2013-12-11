package com.pe.pgn.clubpgn.webapp.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.pe.pgn.clubpgn.model.OpcionMenu;
import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.security.MySecureResourceFilter;
import com.pe.pgn.clubpgn.service.OpcionMenuService;
import com.pe.pgn.clubpgn.service.RoleManager;
import com.pe.pgn.clubpgn.webapp.controller.BaseFormController;

@Controller
@RequestMapping("/admin/**")
public class RoleController extends BaseFormController {

	@Autowired
	private RoleManager roleManager;
	
	@Autowired
	private OpcionMenuService opcionMenuService;
	
	@Autowired
	private MySecureResourceFilter mySecureResourceFilter;
	
	private static final String ES_EDITABLE = "esEditable";
	
	@RequestMapping(value = "/rolelist.html")
    public void listarCategorias(Model model) {
		
		List<Role> roles = roleManager.getRoleList();
		model.addAttribute("roles", roles);
	}
	
	@RequestMapping(value = "/roleform.html", method = RequestMethod.GET)
	public @ModelAttribute("role") Role verRole(
				@RequestParam(value = "id", required = false) Long id, Model model) {

		Role role = new Role();
		if (id != null) {			
			
			role = roleManager.getRole(id);		
			
			List<OpcionMenu> opcionesMenu = opcionMenuService.obtenerOpcionesMenuPorRol(role.getId());
			role.setOpcionesMenu(opcionesMenu);
			
			List<OpcionMenu> opcionesMenuNoElegidas = opcionMenuService.obtenerOpcionesMenuNoEnRol(role.getId());
			role.getOpcionesMenu().addAll(opcionesMenuNoElegidas);
			
			boolean esRoleConDependencias = roleManager.esRoleConDependencias(id);
			if(esRoleConDependencias){
				model.addAttribute(ES_EDITABLE, Boolean.FALSE);	
			}else{
				model.addAttribute(ES_EDITABLE, Boolean.TRUE);	
			}
			
			return role;
		}
		
		List<OpcionMenu> opcionesMenu = opcionMenuService.obtenerTodasOpcionesMenu();
		role.setOpcionesMenu(opcionesMenu);	
		model.addAttribute(ES_EDITABLE, Boolean.TRUE);	
		
		return role;
	}
	
	@RequestMapping(value = "/roleform.html", method = RequestMethod.POST)
	public String guardarRole(@ModelAttribute("role") Role role,
			BindingResult result, SessionStatus status, HttpServletRequest request, Model model) {
		
		try{
	
			if (result.hasErrors()) {
				model.addAttribute("role", role);
				return "/admin/roleform";
			}
			
			roleManager.saveRole(role);
			updateRoleSecurity(role);
			saveMessage(request, getText("roleform.added", role.getName(), request.getLocale()));
			return "redirect:rolelist.html";
			
		}catch (Exception e) {
			
			if(role.getId() != null){
				return "redirect:roleform.html?from=list&id="+role.getId();
			}
			model.addAttribute("role", role);
			saveError(request, getText("errors.general", request.getLocale()));
			return "redirect:roleform.html";
		}		
	}
	
	private void updateRoleSecurity(Role role) {
		
		try{
			mySecureResourceFilter.addConfigAttribute(role);			
		}catch (Exception e){
			System.out.println();
		}
	}

	@RequestMapping(value = "/eliminarrole.html")
	public String eliminarRole(@RequestParam(value = "id", required = false) Long id, 
			HttpServletRequest request, Model model) {
		
		try {			
			
			roleManager.removeRole(id);
			saveMessage(request, getText("roleform.deleted", request.getLocale()));
			return "redirect:rolelist.html";
			
		} catch (Exception e) {
			saveError(request, getText("errors.general", request.getLocale()));
			return "redirect:roleform.html?from=list&id="+id;
		}
	}
}
