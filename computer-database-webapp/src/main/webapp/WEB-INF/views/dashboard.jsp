<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<c:set var="resourcesUrl" value="${pageContext.request.contextPath}/resources" />
<link href="${resourcesUrl}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${resourcesUrl}/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${resourcesUrl}/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href='<cst:links linkTo="reset" />'> Application - Computer Database </a>
			<span style="float: right; color: white;"><spring:message code="lang.current" text="Current language" /> ${pageContext.response.locale} <a href='<cst:links linkTo="self" lang="en" />'><img src="${resourcesUrl}/img/thumbs/en.png"></a> | <a href='<cst:links linkTo="self" lang="fr" />'><img src="${resourcesUrl}/img/thumbs/fr.png"></a>
			<a href="<c:url value="/logout" />" > Logout</a></span>
		</div>
	</header>
	
	<section id="main">
		<div class="container">
			<h1 id="homeTitle"><c:out value="${ PAGE.nbEntries }" /> <spring:message code="header.compFound" text="Computers found" /></h1>
			
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="<c:out value="${ pathSearchComputer }" />" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="<spring:message code="header.search" text="Search name" />" value='<c:out value="${ param.search != null ? param.search : '' }" />' /> <input
							type="submit" id="searchsubmit" name="searchSubmit" value="<spring:message code="header.filter" text="Filter by name" />"
							class="btn btn-primary" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="_csrf" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="<c:out value="${ pathAddComputer }" />" ><spring:message code="header.add" text="Add computer" /></a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();"><spring:message code="misc.edit" text="Edit" /></a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action='<c:out value="${ pathComputerDelete }"/>' method="POST">
			<input type="hidden" name="selection" value="">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" id="_csrf" />
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->
						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="${ pathComputerDelete }"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href='<cst:links linkTo="self" field="name" />'><spring:message code="computer.name" text="Computer name" /></a></th>
						<th><a href='<cst:links linkTo="self" field="introduced" />'><spring:message code="computer.intro" text="Introduction date" /></a></th>
						<th><a href='<cst:links linkTo="self" field="discontinued" />'><spring:message code="computer.outro" text="Discontinuation date" /></a></th>
						<th><a href='<cst:links linkTo="self" field="company.name" />'><spring:message code="computer.company" text="Manufacturer" /></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
				<c:forEach items="${ PAGE.page }" var="computer" >
					<tr>
						<td class="editMode"><input type="checkbox" name="cb" class="cb" id='<c:out value="${ computer.name }_name" />' value="<c:out value="${ computer.id }" />"></td>
						<td><a href="<c:out value="${ pathEditComputer }?computer=${ computer.name }" />" onclick="" id="computer_id" ><c:out value="${ computer.name }" /></a></td>
						<td><c:out value="${ computer.intro }" /></td>
						<td><c:out value="${ computer.outro }" /></td>
						<td><c:out value="${ computer.company }" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<c:set var="PAGE_NB" value="?pageNb=" />
		<c:set var="DISPLAY_BY" value="?displayBy=" />
		<div class="container text-center">
			<ul class="pagination">
				<cst:pager/>
			</ul>

 			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href='<cst:links linkTo="self" displayBy="10" />' class="btn btn-default">10</a>
 				<a href='<cst:links linkTo="self" displayBy="15" />' class="btn btn-default">15</a>
 				<a href='<cst:links linkTo="self" displayBy="50" />' class="btn btn-default">50</a>
 				<a href='<cst:links linkTo="self" displayBy="100" />' class="btn btn-default">100</a>
 			</div> 
		</div>
	</footer>
	<script src="${resourcesUrl}/js/jquery.min.js"></script>
	<script src="${resourcesUrl}/js/bootstrap.min.js"></script>
	<script src="${resourcesUrl}/js/dashboard.js"></script>
</body>
</html>