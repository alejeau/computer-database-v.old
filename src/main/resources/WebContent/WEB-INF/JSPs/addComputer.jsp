<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="../css/font-awesome.css" rel="stylesheet" media="screen">
<link href="../css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="<c:out value="${ pathDashboardReset }" />"> Application - Computer Database </a>
        </div>
    </header>
	
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="<c:out value="${ pathAddComputer }" />" method="POST">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name"
                                	data-validation="length alphanumeric" data-validation-length="min1" >
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date"
                                	data-validation="date" data-validation-format="yyyy-mm-dd" >
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date"
                                	data-validation="date" data-validation-format="yyyy-mm-dd" >
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<cst:companies/>
                                </select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="<c:out value="${ pathDashboard }" />" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	<script src="../js/jquery.min.js"></script>
	<script src="../js/validator.min.js"></script>
	<script> $.validate(); </script>
</body>
</html>