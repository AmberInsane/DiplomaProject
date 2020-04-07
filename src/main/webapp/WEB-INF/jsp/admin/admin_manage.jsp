<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 07.04.2020
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h2>Администраторы</h2>
<table bgcolor="#ff85f0" border="1">
    <tr>
        <th>ID</th>
        <th>UserName</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${adminUsers}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>
                <form action="manage" method="post">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="hidden" name="action" value="delete"/>
                    <button type="submit">Исключить из администраторов</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<h2>Пользователи</h2>
<table bgcolor="#ff85f0" border="1">
    <tr>
        <th>ID</th>
        <th>UserName</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>
                <form action="manage" method="post">
                    <input type="hidden" name="userId" value="${user.id}"/>
                    <input type="hidden" name="action" value="add"/>
                    <button type="submit">Добавить как администратора</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="/">Назад</a>
</body>
</html>
