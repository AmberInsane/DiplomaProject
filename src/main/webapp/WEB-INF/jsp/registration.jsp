<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Регистрация</title>
</head>

<body>
<jsp:include page="parts/header.jsp"/>
<div class="container">
    <form:form method="POST" modelAttribute="userForm">
        <h2>Регистрация</h2>
        <div class="col-sm-5">
            <form:input type="text" path="username" placeholder="Username" autofocus="true"/>
            <form:errors path="username"/>
                ${usernameError}
        </div>
        <div class="col-sm-5">
            <form:input type="password" path="password" placeholder="Password"/>
        </div>
        <div>
            <form:input type="password" path="passwordConfirm" placeholder="Confirm your password"/>
            <form:errors path="password"/>
                ${passwordError}
        </div>
        <button type="submit">Зарегистрироваться</button>
    </form:form>
    <a href="${pageContext.request.contextPath}/">Главная</a>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>

