<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="roleform.title"/></title>
    <meta name="heading" content="<fmt:message key='roleform.heading'/>"/>
    <script type="text/javascript" src="<c:url value='/scripts/selectbox.js'/>"></script>
</head>

<spring:bind path="role.*">
    <c:if test="${not empty status.errorMessages}">
    <div class="error">
        <c:forEach var="error" items="${status.errorMessages}">
            <img src="<c:url value="/images/iconWarning.gif"/>"
                alt="<fmt:message key="icon.warning"/>" class="icon"/>
            <c:out value="${error}" escapeXml="false"/><br />
        </c:forEach>
    </div>
    </c:if>
</spring:bind>

<form:form commandName="role" method="post" action="roleform.html" onsubmit="return onFormSubmit(this)" id="roleForm">
<form:hidden path="id"/>

<input type="hidden" name="from" value="<c:out value="${param.from}"/>"/>

<ul>
    <li class="buttonBar right">
        <%-- So the buttons can be used at the bottom of the form --%>
        <c:set var="buttons">
            <input type="submit" class="button" name="save" onclick="bCancel=false" value="<fmt:message key="button.save"/>"/>

        <c:if test="${param.from == 'list' and param.method != 'Add' and esEditable eq true}">

			<input type="button" class="button" name="delete"	
					onclick="confirmarEliminarRole('Rol', ${role.id})"
					value="<fmt:message key="button.delete"/>"/>                
        </c:if>

		    <input type="button"  class="button"  onclick="location.href='<c:url value="/admin/rolelist.html"/>'"
		        value="<fmt:message key="button.cancel"/>"/>  
        </c:set>
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
    <li>
        <appfuse:label styleClass="desc" key="roleform.role"/>        
        <c:choose>
        	<c:when test="${esEditable eq true || not role.name eq 'ROLE_ADMIN'}">
		        <form:errors path="name" cssClass="fieldError"/>
		        <form:input path="name" id="name" maxlength="20" cssClass="text medium" cssErrorClass="text medium error"/>        	
        	</c:when>
        	<c:otherwise>
        		${role.name}
        		<form:hidden path="name"/>
        	</c:otherwise>        	
        </c:choose>

    </li>
    <li>
        <appfuse:label styleClass="desc" key="label.descripcion"/>
        <form:input path="description" id="description" maxlength="60" cssClass="text large" cssErrorClass="text large error"/>
    </li>
    
	<li>	
		<table id="opcionesmenu" cellpadding="0" class="table" cellspacing="0" style="width: 400px" > 
			<thead> 
				<tr> 
		            <th scope="col" width="70%"><fmt:message key="opcion.menu.etiqueta"/></th>
		            <th scope="col" width="30%"><fmt:message key="label.habilitado"/></th>
				</tr>
			</thead> 
			<tbody id="opcionmenubody">
				<c:forEach var="opcionMenu" items="${role.opcionesMenu}" varStatus="roleStatus">	
					<c:choose>
						  <c:when test="${roleStatus.index % 2 == 0}"><tr class="odd" ></c:when>
						  <c:otherwise><tr class="even" ></c:otherwise>
					</c:choose>
		            	<td>
							${opcionMenu.deOpcionMenu}
							<form:hidden path="opcionesMenu[${roleStatus.index}].deOpcionMenu"/>
							<form:hidden path="opcionesMenu[${roleStatus.index}].id"/>
							<form:hidden path="opcionesMenu[${roleStatus.index}].deMenuHtml"/>           	
						</td>
		                <td>
		                	<c:choose>
		                		<c:when test="${opcionMenu.deMenuHtml eq 'mainMenu.html' || opcionMenu.deMenuHtml eq 'logout.jsp' }">
		                			<form:checkbox path="opcionesMenu[${roleStatus.index}].stElegido" value="true" disabled="true" checked="checked"/>
		                		</c:when>
		                		<c:otherwise>
		                			<form:checkbox path="opcionesMenu[${roleStatus.index}].stElegido"/>
		                		</c:otherwise>		                	
		                	</c:choose>
		                	
		                </td>       		                         
		           </tr>
		        </c:forEach>				
			</tbody>
		</table>		
	</li>    

    <li class="buttonBar bottom">
        <c:out value="${buttons}" escapeXml="false"/>
    </li>
</ul>
</form:form>

<script type="text/javascript">
    Form.focusFirstElement($('roleForm'));
    highlightFormElements();

	// This is here so we can exclude the selectAll call when roles is hidden
	function onFormSubmit(theForm) {
		return validateRole(theForm);
	}

    function confirmarEliminarRole(obj, id) {   
        var msg = "¿Está seguro que desea eliminar este " + obj + "?";
        ans = confirm(msg);
        if (ans) {
        	window.location.href= "<c:url value="/admin/eliminarrole.html?id="/>"+id;
        } 
    }  	
</script>

<v:javascript formName="role" staticJavascript="false"/>
<script type="text/javascript" src="<c:url value="/scripts/validator.jsp"/>"></script>

