<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="error" required="true" %>

<%-- 	private static final String FIELD_NAME = "name"; --%>
<%-- 	private static final String FIELD_DATES = "dates"; --%>
<%-- 	private static final String FIELD_DATE_INTRO = "intro"; --%>
<%-- 	private static final String FIELD_DATE_OUTRO = "outro"; --%>

<c:choose>
    <c:when test="${not empty error}">
	    <c:choose>
	    	<c:when test="${ error.equals('name') }">
    			<c:out value="${ mapErrors['name'] }" />
	    	</c:when>
	    	<c:when test="${ error.equals('intro') }">
    			<c:out value="${ mapErrors['intro'] }" />
	    	</c:when>
	    	<c:when test="${ error.equals('outro') }">
    			<c:out value="${ mapErrors['outro'] }" />
	    	</c:when>
	    	<c:when test="${ error.equals('dates') }">
    			<c:out value="${ mapErrors['dates'] }" />
	    	</c:when>
	    	<c:otherwise>
	    		<c:out value="${ '' }" />
	    	</c:otherwise>
	    </c:choose>
    </c:when>
    <c:otherwise>
    	<c:out value="${ '' }" />
    </c:otherwise>
</c:choose>
