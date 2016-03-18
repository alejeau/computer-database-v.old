<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags"%>

<c:set var="range" scope="page"
	value="${ maxPageNumber-1 < 7 ? maxPageNumber-1 : 7 }" />

<c:set var="half" scope="page" value="${ range / 2 }" />
<c:set var="half" scope="page" value="${ half - (half % 1) }" />

<c:set var="start" scope="page" value="${ currentPageNumber - half }" />
<c:set var="stop" scope="page" value="${ currentPageNumber + half }" />

<c:set var="tmp" scope="page" value="${ start }" />

<c:set var="start" scope="page" value="${ tmp <= 0 ? 0 : start }" />
<c:set var="stop" scope="page" value="${ tmp <= 0 ? range-1 : stop }" />

<c:set var="tmp" scope="page" value="${ stop }" />
<c:set var="stop" scope="page"
	value="${ tmp > maxPageNumber - 1 ? maxPageNumber  - 1 : stop }" />
<c:set var="stop" scope="page" value="${ stop < 0 ? 0 : stop }" />

<li><a href="<c:out value="${ currentPath }" />?pageNb=0"
	aria-label="Previous"> <span aria-hidden="true">First</span>
</a></li>
<li><a href="<c:out value="${ currentPath }" />?pageNb=<c:out value="${ currentPageNumber-1 < 0 ? 0 : currentPageNumber-1 }" />"
	aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
</a></li>

<c:forEach var="i" begin="${ start }" end="${ stop }" step="1">
	<li><a href='<cst:links linkTo="page" pageNb="${ i }"/>'><c:out
				value="${ i + 1 }" /></a></li>
</c:forEach>

<li><a href='<cst:links linkTo="page" pageNb="${ currentPageNumber+1 > maxPageNumber-1 ? maxPageNumber-1 : currentPageNumber+1 }" />'
	aria-label="Next"> <span aria-hidden="true">&raquo;</span>
</a></li>
<li><a
	href='<cst:links linkTo="page" pageNb="${ maxPageNumber-1 < 0 ? stop : maxPageNumber-1 }" />'
	aria-label="Previous"> <span aria-hidden="true">Last</span>
</a></li>