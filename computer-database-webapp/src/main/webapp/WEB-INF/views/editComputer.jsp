<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cst" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<c:set var="resourcesUrl" value="${pageContext.request.contextPath}/resources" />
<link href="${ resourcesUrl }/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${ resourcesUrl }/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${ resourcesUrl }/css/main.css" rel="stylesheet" media="screen">
<link href="${ resourcesUrl }/css/errors.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href='<cst:links linkTo="reset" />'> Application - Computer Database </a>
			<span style="float: right; color: white;"><spring:message code="lang.current" text="Current language" /> ${pageContext.response.locale} <a href='<cst:links linkTo="self" lang="en" />'><img src="${resourcesUrl}/img/thumbs/en.png"></a> | <a href='<cst:links linkTo="self" lang="fr" />'><img src="${resourcesUrl}/img/thumbs/fr.png"></a></span>
        </div>
    </header>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: <c:out value="${ cdto.id }" />
                    </div>
                    <h1><spring:message code="header.edit" text="" /></h1>

                    <form action="<c:out value="${ pathEditComputer }" />" method="POST">
                        <input type="hidden" name="computerId" value="<c:out value="${ cdto.id }" />"/>
                        <fieldset>
                            <div class="form-group">
                                <input type="text" class="form-control" id="computerName" name="computerName" placeholder="<spring:message code="computer.name" text="" />"
                                	value="<c:out value='${ cdto.name }' />"
                                	data-validation="custom" data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-' \+]*$" >
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="computer.intro" text="" /> <spring:message code="date.format" text="" /></label> <span class="errmsg"><cst:errors error="intro" /> <cst:errors error="dates" /></span>
                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="<spring:message code="computer.intro" text="" />"
	                                value="<c:out value='${ cdto.intro }' />"
                                	data-validation="custom" data-validation-regexp="<spring:message code="date.regex" text="" />" >
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="computer.outro" text="" /> <spring:message code="date.format" text="" /></label> <span class="errmsg"><cst:errors error="outro" /> <cst:errors error="dates" /></span>
                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="<spring:message code="computer.outro" text="" />"
                                	value="<c:out value='${ cdto.outro }' />"
                                	data-validation="custom" data-validation-regexp="<spring:message code="date.regex" text="" />" >
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="computer.company" text="" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<cst:companies/>
                                </select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="misc.edit" text="" />" class="btn btn-primary">
                            or
                            <a href='<cst:links linkTo="dashboard" />' class="btn btn-default"><spring:message code="misc.cancel" text="" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
	<script src="${ resourcesUrl }/js/jquery.min.js"></script>
	<script src="${ resourcesUrl }/js/validator.min.js"></script>
	<script> $.validate(); </script>
</body>
</html>