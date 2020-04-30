<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 07.04.2020
  Time: 19:36
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
    <a href="${pageContext.request.contextPath}/user/my_profile">My profile</a>
    <br>
    <a href="${pageContext.request.contextPath}/user/my_tickets">My tickets</a>
    <br>
    <a href="${pageContext.request.contextPath}/user/find_sessions">Find sessions</a>
    <br>
    <a href="${pageContext.request.contextPath}/user/find_movies">Find movies</a>
    <br>
    <a href="${pageContext.request.contextPath}/user/find_friends">Find friends</a>
    <br>
</div>
</body>
</html>