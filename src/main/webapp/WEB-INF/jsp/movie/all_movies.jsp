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
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Movies</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <h2>Фильмы</h2>
    <table class="table table-striped">
        <tr>
            <th>title</th>
            <th>description</th>
            <th>year</th>
            <th>genre</th>
            <th>timeLength</th>
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN')">
                    <th>update_movie</th>
                    <th>delete_movie</th>
                </security:authorize>
                <security:authorize access="hasRole('USER')">
                    <th>rate_movie</th>
                </security:authorize>
            </security:authorize>
        </tr>
        <c:forEach items="${movies}" var="movie">
            <tr>
                <td>${movie.title}</td>
                <td>${movie.description}</td>
                <td>${movie.year}</td>
                <td>${movie.genre}</td>
                <td>${movie.timeLength} min</td>
                <security:authorize access="isAuthenticated()">
                    <security:authorize access="hasRole('ADMIN')">
                        <td>
                            <a href="${pageContext.request.contextPath}/movie/update_movie/${movie.id}">update_movie</a>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/movie/delete_movie/${movie.id}">delete_movie</a>
                        </td>
                    </security:authorize>
                    <security:authorize access="hasRole('USER')">
                        <td>
                            <a href="${pageContext.request.contextPath}/movie/rate_movie/${movie.id}">rate_movie</a>
                        </td>
                    </security:authorize>
                </security:authorize>
            </tr>
        </c:forEach>
    </table>
    <security:authorize access="isAuthenticated()">
        <security:authorize access="hasRole('ADMIN')">
            <div class="btn-link">
                <a href="${pageContext.request.contextPath}/movie/add_movie"><spring:message code="movie.add"/></a>
            </div>
        </security:authorize>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
