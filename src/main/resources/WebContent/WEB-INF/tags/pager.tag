<%@ tag body-content="empty" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="range" scope="page" value="${ maxPageNumber-1 < 7 ? maxPageNumber-1 : 7 }" />

<c:set var="half"  scope="page" value="${ range / 2 }" />
<c:set var="half"  scope="page" value="${ half - (half % 1) }" />

<c:set var="start" scope="page" value="${ currentPageNumber - half }" />
<c:set var="stop"  scope="page" value="${ currentPageNumber + half }" />

<c:set var="tmp"   scope="page" value="${ start }" />

<c:set var="start" scope="page" value="${ tmp <= 0 ? 0 : start }" />
<c:set var="stop"  scope="page" value="${ tmp <= 0 ? range-1 : stop }" />
<c:set var="stop"  scope="page" value="${ stop < 0 ? 0 : stop }" />

<c:forEach var="i" begin="${ start }" end="${ stop }" step="1" >
	<li><a href="<c:out value="${ currentPath }" />?pageNb=<c:out value="${ i }" />"><c:out value="${ i + 1 }" /></a></li>
</c:forEach>
