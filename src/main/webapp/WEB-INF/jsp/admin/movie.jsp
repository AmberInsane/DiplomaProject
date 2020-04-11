<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 11.04.2020
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <form:form method="POST" modelAttribute="movieForm">
        <h2>Фильм</h2>
        <div>
            <form:input type="text" path="title" placeholder="Title" autofocus="true"/>
            <form:label path="description">Enter description:</form:label>
        </div>
        <div>
            <form:input type="textarea" rows="10" cols="50" path="description"/>
        </div>
        <button type="submit">Добавть фильм</button>
    </form:form>
    <a href="${pageContext.request.contextPath}/">Назад</a>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
