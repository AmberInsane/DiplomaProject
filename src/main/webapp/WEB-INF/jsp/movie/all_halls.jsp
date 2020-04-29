<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 19.04.2020
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Halls</title>
    <spring:url value="/movie/session" var="returnUrl"/>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <security:authorize access="isAuthenticated()">
        <security:authorize access="hasRole('ADMIN')">
            <button class="btn btn-primary" onclick="location.href='${returnUrl}'">Return to sessions</button>
                <h2>Halls</h2>
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
                        <th>Capacity</th>
                        <th>Update</th>
                        <th>Delete</th>
                    </tr>
                    <c:forEach items="${halls}" var="hall">
                        <tr>
                            <td>${hall.name}</td>
                            <td>${hall.capacity}</td>
                            <td>
                                <spring:url value="/admin/hall/update/${hall.id}" var="updateUrl"/>
                                <button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
                            </td>
                            <td>
                                <spring:url value="/admin/hall/delete/${hall.id}" var="deleteUrl"/>
                                <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

                <div class="btn-default">
                    <spring:url value="/admin/add/hall" var="addHallUrl"/>
                    <button class="btn btn-primary" onclick="location.href='${addHallUrl}'">Add hall</button>
                </div>
        </security:authorize>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>