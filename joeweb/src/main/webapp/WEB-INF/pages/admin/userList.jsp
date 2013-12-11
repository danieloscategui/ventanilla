<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userList.title"/></title>
    <meta name="heading" content="<fmt:message key='userList.heading'/>"/>
</head>

<form:form id="userList">
	
<c:set var="buttons">
    <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="/userform.html?method=Add&from=list"/>'"
        value="<fmt:message key="button.add"/>"/>

    <input type="button" onclick="location.href='<c:url value="/mainMenu.html"/>'"
        value="<fmt:message key="button.done"/>"/>
</c:set>

<c:out value="${buttons}" escapeXml="false" /><br/><br/>

<table id="users" cellpadding="0" class="table" cellspacing="0" style="width: 700px" > 
	<thead> 
		<tr> 
            <th scope="col"><fmt:message key="user.username"/></th>
            <th scope="col"><fmt:message key="common.nombre"/></th>	
            <th scope="col"><fmt:message key="common.estacion"/></th>
            <th scope="col"><fmt:message key="user.email"/></th>
            <th scope="col"><fmt:message key="user.enabled"/></th>
		</tr>
	</thead> 
	<tbody id="userbody">
		<c:forEach var="user" items="${userList}" varStatus="status">	
			<c:choose>
				  <c:when test="${status.index % 2 == 0}"><tr class="odd" ></c:when>
				  <c:otherwise><tr class="even" ></c:otherwise>
			</c:choose>
            	<td>
					<a href="<c:url value="/userform.html?id=${user.id}&amp;from=list"/>">					
						${user.username}
					</a>            	
				</td>
                <td>
                    ${user.firstName} ${user.lastName}
                </td>
                <td>
                    ${user.clpbEstacion.deEstacion}
                </td>
                <td>
                    ${user.email}
                </td>
                <td>
					<input type="checkbox" disabled="disabled" <c:if test="${user.enabled}">checked="checked"</c:if>/>
                </td>                
           </tr>
        </c:forEach>				
	</tbody>
</table>

<c:out value="${buttons}" escapeXml="false" />
</form:form>
<script type="text/javascript">
    highlightTableRows("users");
</script>
