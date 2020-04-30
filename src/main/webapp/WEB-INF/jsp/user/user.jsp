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
    <spring:url value="/user/my_profile" var="profileUrl"/>
    <spring:url value="/user/my_tickets" var="ticketsUrl"/>
    <spring:url value="/user/find_sessions" var="sessionsUrl"/>
    <spring:url value="/user/find_movies" var="moviesUrl"/>
    <spring:url value="/user/find_friends" var="friendsUrl"/>
</head>
<body>
<div class="component">
    <a href="${profileUrl}"><spring:message code="user.menu.profile"/></a>
    <br>
    <a href="${ticketsUrl}"><spring:message code="user.menu.tickets"/></a>
    <br>
    <a href="${sessionsUrl}"><spring:message code="user.menu.sessions"/></a>
    <br>
    <a href="${moviesUrl}"><spring:message code="user.menu.movies"/></a>
    <br>
    <a href="${friendsUrl}"><spring:message code="user.menu.friends"/></a>
    <br>
</div>
</body>
</html>