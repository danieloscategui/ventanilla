<%@ include file="/common/taglibs.jsp"%>

	<%Object obj = request.getSession().getAttribute("repository"); 
	
	if(obj != null) {	
		
	%>	
	<menu:useMenuDisplayer name="Velocity" config="cssHorizontalMenu.vm" repository="repository" permissions="rolesAdapter">
	<ul id="primary-nav" class="menuList">
	    <li class="pad">&nbsp;</li>
	    <c:forEach var="menu" items="${repository.topMenus}">
	        <menu:displayMenu name="${menu.name}"/>
	    </c:forEach>
	</ul>
	</menu:useMenuDisplayer>
	<%}%>