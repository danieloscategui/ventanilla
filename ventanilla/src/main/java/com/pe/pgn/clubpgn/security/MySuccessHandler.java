package com.pe.pgn.clubpgn.security;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.util.Assert;

public class MySuccessHandler implements AuthenticationSuccessHandler {

	private Map<String, String> map;
	private static final Logger logger = Logger.getLogger(MySuccessHandler.class);

	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		Assert.notNull(map, "AuthInterceptMap is null!");
		String url = "";
		Collection<GrantedAuthority> autCollection = authentication.getAuthorities();

		if (autCollection.isEmpty()) {
			return;
		}
		
		GrantedAuthority[] ga = new GrantedAuthorityImpl[] {};
		url = map.get(autCollection.toArray(ga)[0].toString());

		if (logger.isInfoEnabled()) {
			logger.info("url " + url);
		}
		response.sendRedirect(request.getContextPath() + url);
	}

	public void setAuthDispatcherMap(Map<String, String> map) {
		this.map = map;
	}

}
