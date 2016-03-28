<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:out value="${ pathSource }" />css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="<c:out value="${ pathSource }" />css/font-awesome.css" rel="stylesheet" media="screen">
<link href="<c:out value="${ pathSource }" />css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href='<cst:links linkTo="reset" />'> Application -
				Computer Database </a>
		</div>
	</header>
	
	<section id="main">
		<div class="container">
			<h1 id="homeTitle"><c:out value="${ PAGE.nbEntries }" /> Computers found</h1>
			
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="<c:out value="${ pathSearchComputer }" />" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" value='<c:out value="${ param.search != null ? param.search : '' }" />' /> <input
							type="submit" id="searchsubmit" name="searchSubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="<c:out value="${ pathAddComputer }" />" >Add
						Computer</a> <a class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action='<c:out value="${ currentUrl }"/>' method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->
						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="${ currentUrl }"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href='<cst:links linkTo="self" field="name" />'>Computer name</a></th>
						<th><a href='<cst:links linkTo="self" field="introduced" />'>Introduction date</a></th>
						<th><a href='<cst:links linkTo="self" field="discontinued" />'>Discontinuation date</a></th>
						<th><a href='<cst:links linkTo="self" field="company.name" />'>Manufacturer</a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
				<c:forEach items="${ PAGE.page }" var="computer" >
					<tr>
						<td class="editMode"><input type="checkbox" name="cb" class="cb" value="<c:out value="${ computer.id }" />"></td>
						<td><a href="<c:out value="${ pathEditComputer }?computer=${ computer.name }" />" onclick="" ><c:out value="${ computer.name }" /></a></td>
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
<c:set var="PAGE_NB " value="?pageNb=" />
<c:set var="DISPLAY_BY " value="?displayBy=" />
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
	<script src="<c:out value="${ pathSource }" />js/jquery.min.js"></script>
	<script src="<c:out value="${ pathSource }" />js/bootstrap.min.js"></script>
	<script src="<c:out value="${ pathSource }" />js/dashboard.js"></script>
</body>
</html>