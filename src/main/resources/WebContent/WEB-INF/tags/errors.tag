<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="error" required="true" %>

<%-- 	private static final String FIELD_NAME = "Computer.name"; --%>
<%-- 	private static final String FIELD_DATES = "Dates"; --%>
<%-- 	private static final String FIELD_DATE_INTRO = "Date.intro"; --%>
<%-- 	private static final String FIELD_DATE_OUTRO = "Date.outro"; --%>

<c:choose>
    <c:when test="${not empty error}">
	    <c:choose>
	    	<c:when test="${ error.equals('name') }">
	    		<c:forEach var="item" items="${ listErrors.field }">
	    			<c:if test="${ item.equals('Computer.name') }">
		    			<c:out value="${ item.message }" />
	    			</c:if>	    		
	    		</c:forEach>
	    	</c:when>
	    	<c:when test="${ error.equals('intro') }">
	    		<c:forEach var="item" items="${ listErrors.field }">
	    			<c:if test="${ item.equals('Date.intro') }">
		    			<c:out value="${ item.message }" />
	    			</c:if>	    		
	    		</c:forEach>
	    	</c:when>
	    	<c:when test="${ error.equals('outro') }">
	    		<c:forEach var="item" items="${ listErrors.field }">
	    			<c:if test="${ item.equals('Date.outro') }">
		    			<c:out value="${ item.message }" />
	    			</c:if>	    		
	    		</c:forEach>
	    	</c:when>
	    	<c:when test="${ error.equals('dates') }">
	    		<c:forEach var="item" items="${ listErrors.field }">
	    			<c:if test="${ item.equals('Dates') }">
		    			<c:out value="${ item.message }" />
	    			</c:if>	    		
	    		</c:forEach>
	    	</c:when>
	    	<c:otherwise>
	    		<c:out value="${ item.message }" />
	    	</c:otherwise>
	    </c:choose>
    </c:when>
    <c:otherwise>
    	<c:out value="${ pathReset }" />
    </c:otherwise>
</c:choose>
