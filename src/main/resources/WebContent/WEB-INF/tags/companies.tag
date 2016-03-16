<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:forEach items="${ companies }" var="company">
	<option value="<c:out value="${ company.id }" />"><c:out value="${ company.name }" /></option>" />
</c:forEach>