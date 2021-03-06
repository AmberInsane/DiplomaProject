<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
</head>
<body>
<div class="component">
    <a href="${pageContext.request.contextPath}/admin/manage"><spring:message code="admin.menu.manage"/></a>
    <br>
    <a href="${pageContext.request.contextPath}/admin/movies"><spring:message code="admin.menu.movies"/></a>
    <br>
    <a href="${pageContext.request.contextPath}/admin/session"><spring:message code="admin.menu.session"/></a>
    <br>
    <a href="${pageContext.request.contextPath}/admin/load"><spring:message code="admin.menu.load"/></a>
    <br>
</div>
</body>
</html>

