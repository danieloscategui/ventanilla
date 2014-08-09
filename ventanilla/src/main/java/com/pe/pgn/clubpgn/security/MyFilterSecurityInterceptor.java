package com.pe.pgn.clubpgn.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

public class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {

	private static final Logger logger = Logger.getLogger(FilterSecurityInterceptor.class);
	
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	public void init(FilterConfig arg0) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (logger.isInfoEnabled()) {
			logger.info("doFilter~start");
		}
		FilterInvocation invocation = new FilterInvocation(request, response, chain);
		invoke(invocation);
		if (logger.isInfoEnabled()) {
			logger.info("doFilter~end");
		}
	}

	public void invoke(FilterInvocation invocation) throws IOException,
			ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object user = auth.getPrincipal();
		
		InterceptorStatusToken token = super.beforeInvocation(invocation);

		try {
			invocation.getChain().doFilter(invocation.getRequest(), invocation.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public void destroy() {
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	@Override
	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
}
