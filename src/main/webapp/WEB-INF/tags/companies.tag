<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<option value="-1" ><spring:message code="misc.noCompany" text="" /></option>
<c:forEach items="${ companies }" var="company">
	<option value="<c:out value="${ company.id }" />" <c:out value="${ (selectedId == company.id) ? 'selected' : '' }" /> ><c:out value="${ company.name }" /></option>
</c:forEach>
