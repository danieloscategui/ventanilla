<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="rolelist.title"/></title>
    <meta name="heading" content="<fmt:message key='rolelist.heading'/>"/>
</head>

<form:form id="roleList">
	
<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/admin/roleform.html?method=Add&from=list"/>'"
        value="<fmt:message key="button.add"/>"/>

    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" />

<table id="roles" cellpadding="0" class="table" cellspacing="0" style="width: 300px" > 
	<thead> 
		<tr> 
            <th scope="col"><fmt:message key="roleform.role"/></th>
		</tr>
	</thead> 
	<tbody id="rolebody">
		<c:forEach var="role" items="${roles}" varStatus="status">	
			<c:choose>
				  <c:when test="${status.index % 2 == 0}"><tr class="odd" ></c:when>
				  <c:otherwise><tr class="even" ></c:otherwise>
			</c:choose>
            	<td>
					<a href="<c:url value="/admin/roleform.html?id=${role.id}&amp;from=list"/>">					
						${role.name}
					</a>            	
				</td>               
           </tr>
        </c:forEach>				
	</tbody>
</table>

<c:out value="${buttons}" escapeXml="false" />
</form:form>
<script type="text/javascript">
    highlightTableRows("roles");  
</script>
