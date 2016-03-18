<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="linkTo" required="true" %>
<%@attribute name="pageNb" required="false" %>
<%@attribute name="displayBy" required="false" %>

<c:set var="pathDash" value="/computer-database/access" />
<c:set var="pathAdd" value="/computer-database/access/add" />
<c:set var="pathEdit" value="/computer-database/access/edit" />
<c:set var="pathSearch" value="/computer-database/access/search" />
<c:set var="pathReset " value="/computer-database/access?reset=true" />

<c:choose>
    <c:when test="${not empty linkTo}">
	    <c:choose>
	    	<c:when test="${ linkTo.equals('dashboard') }">
		    	<c:out value="${ '/computer-database/access' }" escapeXml="false" />
	    	</c:when>
	    	<c:when test="${ linkTo.equals('add') }">
		    	<c:out value="${ '/computer-database/access/add' }" escapeXml="false" />
	    	</c:when>
	    	<c:when test="${ linkTo.equals('edit') }">
		    	<c:out value="${ '/computer-database/access/edit' }" escapeXml="false" />
	    	</c:when>
	    	<c:when test="${ linkTo.equals('search') }">
		    	<c:out value="${ '/computer-database/access/search' }" escapeXml="false" />
	    	</c:when>
	    	<c:when test="${ linkTo.equals('reset') }">
		    	<c:out value="${ '/computer-database/access?reset=true' }" escapeXml="false" />
	    	</c:when>
	    	<c:when test="${ linkTo.equals('page') }">
	    		<c:if test="${ not empty pageNb and pageNb.matches('[0-9]+') }">
		    		<c:out value="${ currentPath.concat('?pageNb=').concat(pageNb) }" escapeXml="false" />
	    		</c:if>
	    	</c:when>
	    	<c:when test="${ linkTo.equals('display') }">
	    		<c:if test="${ not empty displayBy and displayBy.matches('[0-9]+') }">
		    		<c:out value="${ currentPath.concat('?displayBy=').concat(displayBy) }" escapeXml="false" />
	    		</c:if>
	    	</c:when>
	    	<c:otherwise>
		    	<c:out value="${ pathReset }" />
	    	</c:otherwise>
	    </c:choose>
    </c:when>
    <c:otherwise>
    	<c:out value="${ pathReset }" />
    </c:otherwise>
</c:choose>



<%-- <%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%><%@  --%>
<%-- taglib prefix="s"       uri="http://www.springframework.org/tags" %> --%>

<%-- <jsp:directive.attribute name="someInput" type="java.lang.Object" required="true" rtexprvalue="true" description="Input object" /> --%>
<%-- <jsp:directive.attribute name="var" type="java.lang.String" required="false" rtexprvalue="false" description="Optional return var name" /> --%>

<%-- <s:eval expression="@someService.someMethod(someInput)" var="someOutput" /> --%>

<%-- <c:choose> --%>
<%--     <c:when test="${not empty var}"> --%>
<%--         ${pageContext.request.setAttribute(var, someOutput)} --%>
<%--     </c:when> --%>
<%--     <c:otherwise> --%>
<%--         ${someOutput} --%>
<%--     </c:otherwi --%>