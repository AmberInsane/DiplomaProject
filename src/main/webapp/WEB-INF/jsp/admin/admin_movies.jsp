<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 11.04.2020
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title>Movies</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <h2>Фильмы</h2>
    <table border="1">
        <tr>
            <th>title</th>
            <th>description</th>
            <th>year</th>
            <th>genre</th>
            <th>timeLength</th>
            <th>update</th>
            <th>delete</th>
        </tr>
        <c:forEach items="${movies}" var="movie">
            <tr>
                <td>${movie.title}</td>
                <td>${movie.description}</td>
                <td>${movie.year}</td>
                <td>${movie.genre}</td>
                <td>${movie.timeLength} min</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/update_movie/${movie.id}">update_movie</a>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/delete_movie/${movie.id}">delete_movie</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}/admin/add_movie"><spring:message code="order.add"/></a>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
