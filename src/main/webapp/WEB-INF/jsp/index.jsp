<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML>
<html>
<head>
    <title><spring:message code="home.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <%--<link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">--%>
</head>
<body>
<jsp:include page="./language_bar.jsp"/>
<div>
    <h3><spring:message code="home.title"/></h3>
    <br>
    <sec:authorize access="!isAuthenticated()">
            <h2><spring:message code="home.info"/></h2>
            <a href="${pageContext.request.contextPath}/login"><spring:message code="home.log_in"/></a>

            <a href="${pageContext.request.contextPath}/registration"><spring:message code="home.registration"/></a>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        Welcome ${pageContext.request.userPrincipal.name}
        <security:authorize access="hasRole('USER')">
            <jsp:include page="user/user.jsp"/>
        </security:authorize>
        <security:authorize access="hasRole('ADMIN')">
            <jsp:include page="admin/admin.jsp"/>
        </security:authorize>
        <h4><a href="${pageContext.request.contextPath}/logout"><spring:message code="home.log_out"/></a></h4>
    </sec:authorize>
</div>
</body>
</html>
