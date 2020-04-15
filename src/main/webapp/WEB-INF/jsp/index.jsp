<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
    <title><spring:message code="home.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

</head>
<body>

<jsp:include page="parts/header.jsp"/>
<div class="container">
    <security:authorize access="!isAuthenticated()">
        <div class="container small">
            <h3><spring:message code="home.title"/></h3>
            <h2><spring:message code="home.info"/></h2>
            <spring:url value="/login" var="loginUrl"/>
            <button class="btn left" onclick="location.href='${loginUrl}'"><spring:message code="home.log_in"/></button>
            <spring:url value="/registration" var="registrationUrl"/>
            <button class="btn left" onclick="location.href='${registrationUrl}'"><spring:message code="home.registration"/></button>
        </div>
    </security:authorize>
    <security:authorize access="isAuthenticated()">
        <h3>Welcome ${pageContext.request.userPrincipal.name}</h3>
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
