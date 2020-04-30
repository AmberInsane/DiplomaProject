<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 15.04.2020
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Genres</title>
    <spring:url value="/movie" var="returnUrl"/>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <security:authorize access="isAuthenticated()">
        <security:authorize access="hasRole('ADMIN')">
            <button class="btn btn-primary" onclick="location.href='${returnUrl}'">Return to movies</button>
            <h2>Genres</h2>
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
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
                <c:forEach items="${genres}" var="genre">
                    <tr>
                        <td>${genre.name}</td>
                        <td>
                            <spring:url value="/admin/genre/update/${genre.id}" var="updateUrl"/>
                            <spring:url value="/admin/genre/delete/${genre.id}" var="deleteUrl"/>

                            <button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
                            <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </security:authorize>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
