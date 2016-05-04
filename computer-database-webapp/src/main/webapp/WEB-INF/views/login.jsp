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
<body onload='document.loginForm.username.focus();'>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href='<cst:links linkTo="reset" />'> Application - Computer Database </a>
			<span style="float: right; color: white;"><spring:message code="lang.current" text="Current language" /> ${pageContext.response.locale} <a href='<cst:links linkTo="self" lang="en" />'><img src="${resourcesUrl}/img/thumbs/en.png"></a> | <a href='<cst:links linkTo="self" lang="fr" />'><img src="${resourcesUrl}/img/thumbs/fr.png"></a></span>
        </div>
    </header>
	<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="login.header" text="Login" /></h1>
						<c:url var="loginUrl" value="/login" />
						<form action="${loginUrl}" method="post" class="form-horizontal">
                        <fieldset>
                            <div class="form-group">
                                <label for="username"><spring:message code="login.name" text="Username" /></label>
                                <input type="text" class="form-control" id="username" name="username" placeholder="<spring:message code="login.name" text="" />">
                            </div>
                            <div class="form-group">
                                <label for="password"><spring:message code="login.pass" text="Password" /></label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="<spring:message code="login.pass" text="" />">
                            </div>              
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" name="submit" value="<spring:message code="login.header" text="Login" />" class="btn btn-primary">
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
                </div>
            </div>
        </div>
    </section>
	<script src="${ resourcesUrl }/js/jquery.min.js"></script>
</body>
</html>