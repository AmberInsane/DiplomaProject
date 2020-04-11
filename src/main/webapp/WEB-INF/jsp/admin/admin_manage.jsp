<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 07.04.2020
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <h1>Администаторы</h1>
    <table class="table table-striped">
        <tr>
            <th>User Name</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${adminUsers}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>
                        <c:if test="${!pageContext.request.userPrincipal.name.equals(user.username)}">
                            <a href="/admin/manage/delete_admin/${user.id}">Снять полномочия администратора</a>
                        </c:if>
                        <c:if test="${pageContext.request.userPrincipal.name.equals(user.username)}">
                            <label>Это Вы</label>
                        </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h1>Пользователи</h1>
    <table class="table table-striped">
        <tr>
            <th>User Name</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${users}" var="user">
            <tr>
                <td>${user.username}</td>
                <td>
                    <a href="/admin/manage/add_admin/${user.id}">Добавить как администратора</a>
                    <%--<spring:url value="/admin/manage/add_admin/${user.id}" var="Добавить как администратора" />--%>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/">Назад</a>
</div>

<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
