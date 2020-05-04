<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 11.04.2020
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title><spring:message code="movie.form3"/></title>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="../parts/header.jsp"/>
        <div class="container mt-5">
            <c:if test="${not empty msg_code}">
                <div class="alert alert-${css} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong><spring:message code="${msg_code}"/></strong>
                    <c:if test="${not empty count}">
                        <spring:message code="messages.found"/> ${count} <spring:message
                            code="${count_type_code}"/>
                    </c:if>
                </div>
            </c:if>
            <h2><spring:message code="movie.form3"/></h2>
            <c:if test="${not empty genre}">
                <h4><spring:message code="genre.form"/>: ${genre.name}</h4>
            </c:if>
            <c:if test="${not empty year}">
                <h4><spring:message code="movie.year"/>: ${year}</h4>
            </c:if>
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN')">
                    <div class="btn-link">
                        <a href="${pageContext.request.contextPath}/admin/movie/add"><spring:message code="action.add"/>
                            <spring:message code="movie.form"/></a>
                    </div>
                    <div class="btn-link">
                        <a href="${pageContext.request.contextPath}/admin/genre"><spring:message code="action.manage"/>
                            <spring:message code="genre.form4"/></a>
                    </div>
                </security:authorize>
            </security:authorize>

            <table class="table table-hover">
                <tr>
                    <th><spring:message code="movie.title"/></th>
                    <th><spring:message code="movie.description"/></th>
                    <th><spring:message code="movie.year"/></th>
                    <th><spring:message code="genre.form"/></th>
                    <th><spring:message code="movie.time"/></th>
                    <th></th>
                </tr>
                <c:forEach items="${movies}" var="movie">
                    <tr>
                        <td>
                            <div class="hyperlink">
                                <spring:url value="/movie/${movie.id}" var="movieUrl"/>
                                <a href="${movieUrl}">${movie.title}</a>
                            </div>
                        </td>
                        <td>${movie.description}</td>
                        <td><spring:url value="/movie/year/${movie.year}" var="yearUrl"/>
                            <a href="${yearUrl}">${movie.year}</a>
                        </td>
                        <td>
                            <c:forEach var="genre" items="${movie.genre}" varStatus="loop">
                                <spring:url value="/movie/genre/${genre.id}" var="genreUrl"/>
                                <a href="${genreUrl}">${genre.name}</a>
                                <c:if test="${not loop.last}">,</c:if>
                            </c:forEach>
                        </td>
                        <td>${movie.timeLength} <spring:message code="movie.time.minutes"/></td>
                        <security:authorize access="isAuthenticated()">
                            <td class="buttons-column">
                                <security:authorize access="hasRole('ADMIN')">
                                    <spring:url value="admin/movie/update/${movie.id}" var="updateUrl"/>
                                    <spring:url value="admin/movie/delete/${movie.id}" var="deleteUrl"/>

                                    <button class="btn btn-sm btn-primary" onclick="location.href='${updateUrl}'">
                                        <spring:message
                                                code="action.update"/></button>
                                    <button class="btn btn-sm btn-danger"
                                            onclick="this.disabled=true;post('${deleteUrl}')">
                                        <spring:message code="action.delete"/>
                                    </button>
                                </security:authorize>
                                <spring:url value="/movie/${movie.id}" var="findSessionsUrl"/>
                                <button class="btn btn-sm btn-primary" onclick="location.href=('${findSessionsUrl}')">
                                    <spring:message
                                            code="action.find"/> <spring:message code="session.form3"/>
                                </button>
                            </td>
                        </security:authorize>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
