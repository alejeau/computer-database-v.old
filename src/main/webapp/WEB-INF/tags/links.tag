<jsp:directive.tag pageEncoding="UTF-8" body-content="empty" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<%@attribute name="linkTo" required="true"%>
<%@attribute name="search" required="false"%>
<%@attribute name="pageNb" required="false"%>
<%@attribute name="displayBy" required="false"%>
<%@attribute name="field" required="false"%>
<%@attribute name="ascending" required="false"%>

<c:set var="pathDash" value="/computer-database/access" />
<c:set var="pathAdd" value="/computer-database/access/add" />
<c:set var="pathEdit" value="/computer-database/access/edit" />
<c:set var="pathSearch" value="/computer-database/access/search" />
<c:set var="pathReset " value="/computer-database/access?reset=true" />

<c:set var="emptyText" value="" />
<c:set var="tmpPath" value="" />
<c:set var="tmpSearchMode" value="${ false }" />
<c:set var="tmpPageNb"
	value="${ emptyText.concat('?pageNb=').concat(PAGE.currentPageNumber) }" />
<c:set var="tmpDisplayBy"
	value="${ emptyText.concat('&displayBy=').concat(PAGE.objectsPerPages) }" />
<c:set var="tmpField"
	value="${ emptyText.concat('&field=').concat(PAGE.field) }" />
<c:set var="oldField"
	value="${ emptyText.concat(PAGE.field) }" />
<c:set var="tmpAscending"
	value="${ emptyText.concat('&ascending=true') }" />
<c:set var="tmpSearch"
	value="${ searchModeActivated ? emptyText.concat('&search=').concat(PAGE.search) : '' }" />

<%-- <c:out value="field=${ field } and PAGE.field=${ PAGE.field } and field.equals(PAGE.field)=${ field.equals(PAGE.field) }" /> --%>
<%-- <c:out value="ascending=${ param['ascending'] }" /> --%>

<%-- --%>
<c:choose>
	<c:when test="${not empty linkTo}">
		<c:choose>
			<c:when
				test="${ linkTo.equals('dashboard') }">
				<c:set var="tmpPath"
					value="${ tmpPath.concat('/computer-database/access') }" />
			</c:when>
			<c:when test="${ linkTo.equals('add') }">
				<c:set var="tmpPath"
					value="${ tmpPath.concat('/computer-database/access/add') }" />
			</c:when>
			<c:when test="${ linkTo.equals('edit') }">
				<c:set var="tmpPath"
					value="${ tmpPath.concat('/computer-database/access/edit') }" />
			</c:when>
			<c:when test="${ linkTo.equals('search') }">
				<c:set var="tmpPath"
					value="${ tmpPath.concat('/computer-database/access/search') }" />
			</c:when>
			<c:when test="${ linkTo.equals('reset') }">
				<c:set var="tmpPath"
					value="${ tmpPath.concat('/computer-database/access?') }" />
				<c:set var="tmpPageNb"
					value="${ emptyText.concat('?pageNb=0') }" />
				<c:set var="tmpDisplayBy"
					value="${ emptyText.concat('&displayBy=10') }" />
				<c:set var="tmpField"
					value="${ emptyText.concat('&field=name') }" />
				<c:set var="tmpAscending"
					value="${ emptyText.concat('&ascending=true') }" />
			</c:when>
			<c:when test="${ linkTo.equals('self') }">
				<c:set var="tmpPath" value="${ tmpPath.concat(currentPath) }" />
				<c:if test="${ searchModeActivated }">
					<c:set var="tmpSearchMode" value="${ true }" />
				</c:if>
			</c:when>
			<c:otherwise>
				<c:out value="${ tmpPath.concat(pathDash) }" />
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:out value="${ tmpPath.concat(pathDash) }" />
	</c:otherwise>
</c:choose>

<c:if test="${ not empty pageNb and pageNb.matches('[0-9]+') }">
	<c:set var="tmpPageNb"
		value="${ emptyText.concat('?pageNb=').concat(pageNb) }" />
</c:if>

<c:if test="${ not empty displayBy and displayBy.matches('[0-9]+') }">
	<c:set var="tmpDisplayBy" value="${ emptyText.concat('&displayBy=').concat(displayBy) }" />
</c:if>

<c:if
	test="${ not empty param['ascending'] and param['ascending'].matches('[true|false]') }">
	<c:set var="tmpAscending" value="${ emptyText.concat('&ascending=true') }" />
</c:if>

<c:if
	test="${ not empty field }">
	<c:if test="${ field.equals('name')
		or field.equals('introduced')
		or field.equals('discontinued')
		or field.equals('company.name') }">
		<c:set var="tmpField"
			value="${ emptyText.concat('&field=').concat(field) }" />
	</c:if>
	<c:if test="${ field.equals(PAGE.field) }">
		<c:set var="tmpAscending" value="${ emptyText.concat('&ascending=').concat(!PAGE.ascending) }" />
	</c:if>
</c:if>

<c:set var="tmpPath" value="${ tmpPath.concat(tmpPageNb) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpDisplayBy) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpField) }" />
<c:set var="tmpPath" value="${ tmpPath.concat(tmpAscending) }" />
<c:if test="${ tmpSearchMode }">
	<c:set var="tmpPath" value="${ tmpPath.concat(tmpSearch) }" />
</c:if>

<c:out value="${ tmpPath }" escapeXml="false" />
<%-- --%>