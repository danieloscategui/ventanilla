package com.pe.pgn.clubpgn.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

public class MyInvocationSecurityMetadataSourceService implements
		FilterInvocationSecurityMetadataSource {

	private static final Logger logger = Logger.getLogger(MyInvocationSecurityMetadataSourceService.class);
		private UrlMatcher urlMatcher = new AntUrlPathMatcher();
		private static Map<String, Collection<ConfigAttribute>> resourceMap;
		private Map<String, String> interceptUrl;

		@SuppressWarnings("unused")
		private void loadResourceDefine() {
			
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			if(interceptUrl==null) {
				if(logger.isInfoEnabled()) {
					logger.info("un mensajillo");
				}
				return;
			}

			for(Map.Entry<String, String> entry : interceptUrl.entrySet()) {
				Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>(); 
		        ConfigAttribute ca = null;
		        if(entry.getValue()==null) {
		        	continue;
		        }
		        for(String configAttribute : entry.getValue().split(",")) {
		        	ca = new SecurityConfig(configAttribute);
		        	atts.add(ca);
		        }
		        resourceMap.put(entry.getKey(), atts);
			}
						
			if(logger.isInfoEnabled()) {
				logger.info("algun mensajillo!");
			}
		}
		
		public Collection<ConfigAttribute> getAllConfigAttributes() {
			return null;
		}

		public Collection<ConfigAttribute> getAttributes(Object object)
				throws IllegalArgumentException {
			String resUrl = ((FilterInvocation) object).getRequestUrl();
			Iterator<String> iterator = resourceMap.keySet().iterator();
			while (iterator.hasNext()) {
				String url = iterator.next();
				
				if(urlMatcher.pathMatchesUrl(url, resUrl)) {
					Collection<ConfigAttribute> col = resourceMap.get(url);
					if(logger.isInfoEnabled()) {
						logger.info("algun otro mensajillo");
					}
					return col;
				}
			}
			return null;
		}

		public boolean supports(Class<?> clazz) {
			return true;
		}

		public void setInterceptUrl(Map<String, String> interceptUrl) {
			this.interceptUrl = interceptUrl;
		}

}
