<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 15.04.2020
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Genres</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <security:authorize access="isAuthenticated()">
        <security:authorize access="hasRole('ADMIN')">
            <h2>genres</h2>
            <c:if test="${not empty msg}">
                <div class="alert alert-${css} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong>${msg}</strong>
                </div>
            </c:if>

            <table class="table table-striped">
                <tr>
                    <th>Name</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${genres}" var="genre">
                    <tr>
                        <td>${genre.name}</td>
                        <td>
                            <spring:url value="movie/delete_genre/${genre.id}" var="deleteUrl"/>
                            <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete
                            </button>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <div class="container">
                <form:form method="POST" modelAttribute="genreForm">
                    <h3>Добавить жанр</h3>
                    <form:input type="text" path="name" placeholder="Name"/>
                    <div class="btn-default">
                        <button type="submit">Добавить</button>
                    </div>
                </form:form>
            </div>
        </security:authorize>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
