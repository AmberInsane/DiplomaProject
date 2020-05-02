<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><spring:message code="login.title"/></title>
    <spring:url value="/login" var="loginUrl"/>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>

<body>

<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="parts/header.jsp"/>
        <div class="container mt-5">
            <form class="form col-sm-4 mx-auto" name="loginForm" method="POST" action="${loginUrl}">
                <h2><spring:message code="login.title.long"/></h2>
                <div class="form-group">
                    <label class="form-control-label"><spring:message code="user.username"/></label>
                    <div><input name="username" type="text"/></div>
                </div>
                <div class="form-group">
                    <label class="form-control-label"><spring:message code="user.password"/></label>
                    <div><input name="password" type="password"/></div>
                </div>
                <button type="submit" class="btn center-block btn-primary"><spring:message code="login.title"/></button>
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                    <div class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <strong><spring:message code="login.error"/></strong>
                    </div>
                </c:if>
            </form>
        </div>
    </div>
    <jsp:include page="parts/footer.jsp"/>
</div>
</body>
</html>
