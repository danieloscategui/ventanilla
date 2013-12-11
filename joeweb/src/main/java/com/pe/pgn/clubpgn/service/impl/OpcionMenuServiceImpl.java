package com.pe.pgn.clubpgn.service.impl;


import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.navigator.menu.MenuComponent;
import net.sf.navigator.menu.MenuRepository;

import com.pe.pgn.clubpgn.dao.OpcionMenuDao;
import com.pe.pgn.clubpgn.model.OpcionMenu;
import com.pe.pgn.clubpgn.service.OpcionMenuService;

@Service("opcionMenuService")
public class OpcionMenuServiceImpl extends GenericManagerImpl<OpcionMenu, Long> implements
		OpcionMenuService {
	
	@Autowired
	private OpcionMenuDao opcionMenuDao;

	
	public void obtenerMenuPrincipal(HttpServletRequest request) {
				
		MenuRepository repository = new MenuRepository();
		ServletContext servletContext = request.getSession().getServletContext();
		MenuRepository defaultRepository = (MenuRepository)servletContext.getAttribute(MenuRepository.MENU_REPOSITORY_KEY);
		repository.setDisplayers(defaultRepository.getDisplayers());
		repository.setServletContext(defaultRepository.getServletContext());
			
		List<OpcionMenu> opcionesMenu = opcionMenuDao.obtenerOpcionesMenuPorUsuario();
		for (Iterator<OpcionMenu> iterOpcion = opcionesMenu.iterator(); iterOpcion.hasNext();) 
		{
			
			OpcionMenu opcionMenu = iterOpcion.next();				
		    MenuComponent mc = new MenuComponent();
		    
		    String id = opcionMenu.getId().toString();
		    String coOpcionMenuPadre = opcionMenu.getCoOpcionMenuPadre() == null ? null : opcionMenu.getCoOpcionMenuPadre().toString();
		    			    
		    mc.setName(id);
		    if (coOpcionMenuPadre != null) 
		    {
		        MenuComponent parentMenu = repository.getMenu(coOpcionMenuPadre);        
		        mc.setParent(parentMenu);
		    }
		    
		    String title = opcionMenu.getDeOpcionMenu().toString();
		    mc.setTitle(title);	
		    
		    String applicationName = servletContext.getServletContextName();
		    Object url = opcionMenu.getDeMenuHtml();			    
		    mc.setLocation(url != null ? "/" + applicationName + "/" + url.toString() : null);
		    mc.setWidth(opcionMenu.getDeAncho() == null ? null : opcionMenu.getDeAncho().toString());
		    	                   
		    repository.addMenu(mc);
		}		
		
		servletContext.setAttribute(MenuRepository.MENU_REPOSITORY_KEY, defaultRepository);
		request.getSession().setAttribute("repository", repository); 
	}

	
	public List<OpcionMenu> obtenerOpcionesMenuPorRol(Long coRole) {
		
		return opcionMenuDao.obtenerOpcionesMenuPorRol(coRole);
	}

	
	public List<OpcionMenu> obtenerOpcionesMenuNoEnRol(Long coRole) {
		
		return opcionMenuDao.obtenerOpcionesMenuNoEnRol(coRole);
	}

	
	public List<OpcionMenu> obtenerTodasOpcionesMenu() {
		return opcionMenuDao.obtenerTodasOpcionesMenu();
	}

}
