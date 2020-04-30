<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML>
<html>
<head>
    <title><spring:message code="home.title"/></title>
   <%-- <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>--%>
    <spring:url value="/login" var="loginUrl"/>
    <spring:url value="/registration" var="registrationUrl"/>
</head>
<body>

<jsp:include page="parts/header.jsp"/>
<div class="container">
    <security:authorize access="!isAuthenticated()">
        <h3><spring:message code="home.title"/></h3>
        <h2><spring:message code="home.info"/></h2>
        <button class="btn left btn-primary" onclick="location.href='${loginUrl}'"><spring:message code="login.title"/></button>
        <button class="btn right btn-primary" onclick="location.href='${registrationUrl}'"><spring:message code="registration.title"/></button>
    </security:authorize>
    <security:authorize access="isAuthenticated()">
        <h3><spring:message code="home.welcome"/> ${pageContext.request.userPrincipal.name}</h3>
        <security:authorize access="hasRole('USER')">
            <jsp:include page="user/user.jsp"/>
        </security:authorize>
        <security:authorize access="hasRole('ADMIN')">
            <jsp:include page="admin/admin.jsp"/>
        </security:authorize>
    </security:authorize>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>
