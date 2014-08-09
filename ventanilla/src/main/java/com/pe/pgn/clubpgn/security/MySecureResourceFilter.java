package com.pe.pgn.clubpgn.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

import com.pe.pgn.clubpgn.Constants;
import com.pe.pgn.clubpgn.dao.OpcionMenuDao;
import com.pe.pgn.clubpgn.model.OpcionMenu;
import com.pe.pgn.clubpgn.model.Role;
import com.pe.pgn.clubpgn.webapp.util.ValidationUtil;

public class MySecureResourceFilter implements FilterInvocationSecurityMetadataSource{

   private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
   private UrlMatcher urlMatcher = new AntUrlPathMatcher();
   private static Collection<ConfigAttribute>  allCfgattrs = new HashSet<ConfigAttribute>();
   static final Log log = LogFactory.getLog(MySecureResourceFilter.class);
   
   private OpcionMenuDao opcionMenuDao;
   
   public MySecureResourceFilter() {
       super();
   }

   public void initilize(){
       resourceMap = buildRequestMap();
   }

   public boolean isSingleton() {
       return true;
   }

   protected Map<String, Set<String>> findResources() {

       Map<String, Set<String>> resourceMap = new LinkedHashMap<String, Set<String>>();
       for (Resource resource : getResourceList()) {
           
           String url = resource.getUrl();
           String role = resource.getRole();
           Set<String> roleSet = resourceMap.get(url);
           if(roleSet==null){
               roleSet = new HashSet<String>();
               resourceMap.put(url, roleSet);
           }
           roleSet.add(role);
       }

       return resourceMap;
   }

   private List<Resource> getResourceList() {
    
       List<Resource> list = new ArrayList<Resource>();       
       List<OpcionMenu> opcionesMenu = opcionMenuDao.obtenerTodosRolesMenuOpcion();
       if(ValidationUtil.validateList(opcionesMenu)){
	       for (int i = 0; i < opcionesMenu.size(); i++) {			
	    	   OpcionMenu opc = (OpcionMenu)opcionesMenu.get(i);
	    	   String deMenuHtml = opc.getDeMenuHtml().trim();
	    	   String deRol = opc.getDeName().trim();
	    	   
	    	   if(!deMenuHtml.startsWith("/"))
	    	   deMenuHtml = "/"+deMenuHtml;    	   
	    	   list.add(new Resource(deMenuHtml,deRol));
	       }
       }
       
    return list;
   }

   protected Map<String, Collection<ConfigAttribute>> buildRequestMap() {
           
       Map<String, Collection<ConfigAttribute>> requestMap =
       new LinkedHashMap<String, Collection<ConfigAttribute>>();

       Map<String, Set<String>> resourceMap = this.findResources();
       for(String url : resourceMap.keySet()){
           Set<String> roleSet = resourceMap.get(url);
           Collection<ConfigAttribute> catts  = new HashSet<ConfigAttribute>();
           for(String role : roleSet){
                   catts.add(new SecurityConfig(role));
           }
           allCfgattrs.addAll(catts);
           requestMap.put(url, catts);
       }
       
       return requestMap;
   }

   protected UrlMatcher getUrlMatcher() {
           return this.urlMatcher;
   }

   @Override
   public Collection<ConfigAttribute> getAllConfigAttributes() {
           return allCfgattrs;
   }

   @Override
   public Collection<ConfigAttribute> getAttributes(Object object)
                   throws IllegalArgumentException {
           
       String url = ((FilterInvocation)object).getRequestUrl();
       Iterator<String> ite = resourceMap.keySet().iterator();
       
       while (ite.hasNext()) {
           String resURL = ite.next();
           if (urlMatcher.pathMatchesUrl(resURL,url)) {
               log.info("url = "+url+",  resURL = "+resURL);
               Collection<ConfigAttribute> catts = resourceMap.get(resURL);
               return catts;
           }else{
               log.info("url = "+url+",  resURL = "+resURL);
           }
       }
       
       return null;
   }
   
   @Override
   public boolean supports(Class<?> clazz) {
           return true;
   }
   
   public void addConfigAttribute(Role role) throws Exception{
	   
	   if(ValidationUtil.validateList(role.getOpcionesMenu())){
		   
		   Collection<ConfigAttribute> catts  = new HashSet<ConfigAttribute>();
		   catts.add(new SecurityConfig(role.getName()));
		   allCfgattrs.addAll(catts);	   
	   
	       Iterator<String> ite = resourceMap.keySet().iterator();	       
	       while (ite.hasNext()) {	    	   
	           String deMenu = ite.next();
	           Collection<ConfigAttribute> configAtt = resourceMap.get(deMenu);
	           if (configAtt.contains(new SecurityConfig(role.getName()))) {
	        	   configAtt.remove(new SecurityConfig(role.getName()));
	        	   resourceMap.put(deMenu, configAtt);
	           }
	       }
		   
		   for(Iterator<OpcionMenu> iter = role.getOpcionesMenu().iterator(); iter.hasNext(); ){
		   		
			   OpcionMenu opcionMenu = iter.next();
			   String deMenuHtml = opcionMenu.getDeMenuHtml();
			   
		   		if(deMenuHtml != null && (opcionMenu.isStElegido() || 
		   		   Constants.URL_LOGOUT.equalsIgnoreCase(deMenuHtml) || 
		   		   Constants.URL_MAIN_MENU.equalsIgnoreCase(deMenuHtml))){
		   			
		   			if(!deMenuHtml.startsWith("/"))
		   		    deMenuHtml = "/"+deMenuHtml;
		   			
		   			Collection<ConfigAttribute> roleConfig = resourceMap.get(deMenuHtml);
		   			if(roleConfig == null){
		   				resourceMap.put(deMenuHtml, catts);
		   			}else{	   				
		   				roleConfig.add(new SecurityConfig(role.getName()));
		   				resourceMap.put(deMenuHtml, roleConfig);
		   			}   				   			
		   		} 		
	   		}
	    }
	   
	   System.out.println("resourceMap: "+resourceMap);
   }
   
   public void setOpcionMenuDao(OpcionMenuDao opcionMenuDao) {
	  this.opcionMenuDao = opcionMenuDao;
   }
}