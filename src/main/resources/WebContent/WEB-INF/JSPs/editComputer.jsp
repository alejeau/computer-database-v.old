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
<link href="../css/errors.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href='<cst:links linkTo="reset" />'> Application - Computer Database </a>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${ cdto.id }" />
                    </div>
                    <h1>Edit Computer</h1>

                    <form action="<c:out value="${ pathEditComputer }" />" method="POST">
                        <input type="hidden" name="computerId" value="<c:out value="${ cdto.id }" />"/>
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label> <span class="errmsg"><cst:errors error="name" /></span>
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="Computer name"
                                	value="<c:out value='${ cdto.name }' />"
                                	data-validation="custom" data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-' \+]*$" >
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label> <span class="errmsg"><cst:errors error="intro" /> <cst:errors error="dates" /></span>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date"
	                                value="<c:out value='${ cdto.intro }' />"
                                	data-validation="custom" data-validation-regexp="^^(19[7-9]{1}[0-9]{1}|20[0-2]{1}[0-9]{1}|203[0-7]{1})-(1[0-2]{1}|0[1-9]{1})-(0[1-9]{1}|[1-2]{1}[0-9]{1}|3[0-1]{1})$|^$" >
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label> <span class="errmsg"><cst:errors error="outro" /> <cst:errors error="dates" /></span>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date"
                                	value="<c:out value='${ cdto.outro }' />"
                                	data-validation="custom" data-validation-regexp="^^(19[7-9]{1}[0-9]{1}|20[0-2]{1}[0-9]{1}|203[0-7]{1})-(1[0-2]{1}|0[1-9]{1})-(0[1-9]{1}|[1-2]{1}[0-9]{1}|3[0-1]{1})$|^$" >
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<cst:companies/>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href='<cst:links linkTo="dashboard" />' class="btn btn-default">Cancel</a>
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