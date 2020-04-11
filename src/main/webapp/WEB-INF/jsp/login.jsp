<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 05.04.2020
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
    .error {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #a94442;
        background-color: #f2dede;
        border-color: #ebccd1;
    }

    .msg {
        padding: 15px;
        margin-bottom: 20px;
        border: 1px solid transparent;
        border-radius: 4px;
        color: #31708f;
        background-color: #d9edf7;
        border-color: #bce8f1;
    }

    #login-box {
        width: 300px;
        padding: 20px;
        margin: 100px auto;
        background: #fff;
        -webkit-border-radius: 2px;
        -moz-border-radius: 2px;
        border: 1px solid #000;
    }
</style>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Log in with your account</title>
</head>

<body>
<jsp:include page="parts/header.jsp"/>
<div class="container">
    <div id="login-box">
        <form name="loginForm" method="POST" action="/login">
            <h2>Вход в систему</h2>
            <table>
                <tr>
                    <input name="username" type="text" placeholder="Username"
                           autofocus="true"/>
                </tr>
                <tr>
                    <input name="password" type="password" placeholder="Password"/>
                </tr>
                <tr>
                    <button type="submit">Log In</button>
                </tr>
                <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
                    <div class="error">
                        Your login attempt was not successful due to <br/><br/>
                        <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>.
                    </div>
                </c:if>
            </table>
            <h4><a href="/registration">Зарегистрироваться</a></h4>
        </form>
    </div>
</div>
<jsp:include page="parts/footer.jsp"/>
</body>
</html>
