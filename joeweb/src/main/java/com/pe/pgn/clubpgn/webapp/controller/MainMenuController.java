package com.pe.pgn.clubpgn.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pe.pgn.clubpgn.service.OpcionMenuService;

@Controller
@RequestMapping("/mainMenu")
public class MainMenuController extends BaseFormController {

	
	@Autowired
	private OpcionMenuService opcionMenuService;
	
	@RequestMapping(method = RequestMethod.GET)
	public void init(HttpServletRequest request){
		opcionMenuService.obtenerMenuPrincipal(request);
		
	}
}
