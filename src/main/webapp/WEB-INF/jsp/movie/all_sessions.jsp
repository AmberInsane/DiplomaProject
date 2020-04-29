<%--
  Created by IntelliJ IDEA.
  User: stankevich_m
  Date: 16.04.2020
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Сеансы</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <h2>Сеансы</h2>
    <table class="table table-striped">
        <tr>
            <th>Date</th>
            <th>movie</th>
            <th>price</th>
            <th>hall</th>
            <th></th>
        </tr>
        <c:forEach items="${sessions}" var="session">
            <tr>
                    <%--  <td>${session.startDate}</td>--%>
                <td>${session.dateFormatText}</td>
                <td>
                    <div class="hyperlink">
                        <spring:url value="/movie/${session.movie.id}" var="movieUrl"/>
                        <a href="${movieUrl}">${session.movie.title}</a>
                    </div>
                </td>
                <td>${session.price}</td>
                <td>
                    <div class="hyperlink">
                        <spring:url value="/movie/hall/${session.hall.id}" var="hallUrl"/>
                        <a href="${hallUrl}">${session.hall.name}</a>
                    </div>
                </td>
                <security:authorize access="isAuthenticated()">
                    <security:authorize access="hasRole('ADMIN')">
                        <td>
                            <spring:url value="/admin/session/update/${session.id}" var="updateUrl"/>
                            <spring:url value="/admin/session/delete/${session.id}" var="deleteUrl"/>

                            <button class="btn btn-primary" onclick="location.href='${updateUrl}'">Update</button>
                            <button class="btn btn-danger" onclick="this.disabled=true;post('${deleteUrl}')">Delete
                            </button>
                        </td>
                    </security:authorize>
                    <security:authorize access="hasRole('USER')">
                        <td>
                            <spring:url value="/ticket/${session.id}/buy" var="buyTicketUrl"/>
                            <button class="btn btn-primary" onclick="location.href=('${buyTicketUrl}')">Buy ticket
                            </button>
                        </td>
                    </security:authorize>
                </security:authorize>
            </tr>
        </c:forEach>
    </table>
    <security:authorize access="isAuthenticated()">
        <security:authorize access="hasRole('ADMIN')">
            <div class="btn-link">
                <spring:url value="/admin/add/session" var="addSessionUrl"/>
                <button class="btn btn-primary" onclick="location.href='${addSessionUrl}'">Add session</button>
            </div>
            <div class="btn-link">
                <spring:url value="/admin/hall" var="hallUrl"/>
                <button class="btn btn-primary" onclick="location.href='${hallUrl}'">Manage halls</button>
            </div>
        </security:authorize>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
