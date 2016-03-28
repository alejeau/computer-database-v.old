<%@ tag body-content="empty"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags"%>


<c:set var="range" scope="page"
	value="${ PAGE.maxPageNumber < 7 ? PAGE.maxPageNumber : 7 }" />

<c:set var="half" scope="page" value="${ range / 2 }" />
<c:set var="half" scope="page" value="${ half - (half % 1) }" />

<c:set var="start" scope="page" value="${ PAGE.currentPageNumber - half }" />
<c:set var="stop" scope="page" value="${ PAGE.currentPageNumber + half }" />

<c:set var="tmp" scope="page" value="${ start }" />

<c:set var="start" scope="page" value="${ tmp <= 0 ? 0 : start }" />
<c:set var="stop" scope="page" value="${ tmp <= 0 ? range-1 : stop }" />

<c:set var="tmp" scope="page" value="${ stop }" />
<c:set var="stop" scope="page" value="${ tmp > PAGE.maxPageNumber ? PAGE.maxPageNumber: stop }" />
<c:set var="stop" scope="page" value="${ stop < 0 ? 0 : stop }" />

<li><a href="<c:out value="${ currentPath }" />?pageNb=0<c:out value="${ searchModeActivated ? '&search='.concat(PAGE.search) : '' }" />"
	aria-label="Previous"> <span aria-hidden="true">First</span>
</a></li>
<li><a href="<c:out value="${ currentPath }" />?pageNb=<c:out value="${ PAGE.currentPageNumber-1 < 0 ? 0 : PAGE.currentPageNumber-1 }" />
<c:out value="${ searchModeActivated ? '&search='.concat(PAGE.search) : '' }" />"
	aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
</a></li>

<c:forEach var="i" begin="${ start }" end="${ stop }" step="1">
	<li><a href='<cst:links linkTo="page" pageNb="${ i }" search="${ searchModeActivated ? '&search='.concat(PAGE.search) : '' }" />'><c:out
				value="${ i + 1 }" /></a></li>
</c:forEach>

<li><a href='<cst:links linkTo="page" pageNb="${ PAGE.currentPageNumber+1 > PAGE.maxPageNumber ? PAGE.maxPageNumber : PAGE.currentPageNumber+1 }" search="${ searchModeActivated ? '&search='.concat(PAGE.search) : '' }"/>'
	aria-label="Next"> <span aria-hidden="true">&raquo;</span>
</a></li>
<li><a
	href='<cst:links linkTo="page" pageNb="${ PAGE.maxPageNumber < 0 ? stop : PAGE.maxPageNumber }" search="${ searchModeActivated ? '&search='.concat(PAGE.search) : '' }"/>'
	aria-label="Previous"> <span aria-hidden="true">Last</span>
</a></li>