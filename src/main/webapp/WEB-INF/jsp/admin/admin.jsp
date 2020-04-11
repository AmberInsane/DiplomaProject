<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
</head>
<body>
<div class="component">
    <h4><spring:message code="admin.menu"/></h4>
    <a href="/admin/manage"><spring:message code="admin.menu.manage"/></a>
    <br>
    <a href="/admin/movies"><spring:message code="admin.menu.movies"/></a>
    <br>
    <a href="/admin/seance"><spring:message code="admin.menu.seance"/></a>
    <br>
    <a href="/admin/hall"><spring:message code="admin.menu.hall"/></a>
    <br>
    <a href="/admin/all_info"><spring:message code="admin.menu.all_info"/></a>
    <br>
</div>
</body>
</html>

