<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<a class="navbar-brand" href="<c:out value="${ pathDashboardReset }" />"> Application -
				Computer Database </a>
		</div>
	</header>
	
	<section id="main">
		<div class="container">
			<h1 id="homeTitle"><c:out value="${ nbComputers }" /> Computers found</h1>
			
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="<c:out value="${ pathSearchComputer }" />" method="GET" class="form-inline">

						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
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

		<form id="deleteForm" action="#" method="POST">
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
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th>Computer name</th>
						<th>Introduced date</th>
						<!-- Table header for Discontinued Date -->
						<th>Discontinued date</th>
						<!-- Table header for Company -->
						<th>Company</th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
				<c:forEach items="${ computers }" var="computer" >
					<tr>
						<td class="editMode"><input type="checkbox" name="cb" class="cb" value="<c:out value="${ computer.id }" />"></td>
						<td><a href="<c:out value="${ pathEditComputer }" />" onclick=""><c:out value="${ computer.name }" /></a></td>
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
		<div class="container text-center">
			<ul class="pagination">
				<li><a href="<c:out value="${ currentPath }" />?pageNb=0" aria-label="Previous"> <span
						aria-hidden="true">First</span>
				</a></li>
				<li><a href="<c:out value="${ currentPath }" />?page=prev" aria-label="Previous"> <span
						aria-hidden="true">&laquo;</span>
				</a></li>
				<cst:pager/>
				<li><a href="<c:out value="${ currentPath }" />?page=next" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				<li><a href="<c:out value="${ currentPath }" />?pageNb=<c:out value="${ maxPageNumber }" />" aria-label="Previous"> <span
						aria-hidden="true">Last</span>
				</a></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<a href="<c:out value="${ currentPath }" />?displayBy=10" class="btn btn-default">10</a>
				<a href="<c:out value="${ currentPath }" />?displayBy=15" class="btn btn-default">15</a>
				<a href="<c:out value="${ currentPath }" />?displayBy=50" class="btn btn-default">50</a>
				<a href="<c:out value="${ currentPath }" />?displayBy=100" class="btn btn-default">100</a>
			</div>
			<!-- 
			<div class="btn-group btn-group-sm pull-right" role="group">
				<button type="button" class="btn btn-default">10</button>
				<button type="button" class="btn btn-default">50</button>
				<button type="button" class="btn btn-default">100</button>
			</div>
			-->
		</div>
	</footer>
	<script src="<c:out value="${ pathSource }" />js/jquery.min.js"></script>
	<script src="<c:out value="${ pathSource }" />js/bootstrap.min.js"></script>
	<script src="<c:out value="${ pathSource }" />js/dashboard.js"></script>
</body>
</html>