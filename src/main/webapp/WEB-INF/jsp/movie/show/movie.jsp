<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 20.04.2020
  Time: 18:40
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>${movie.title}</title>
    <spring:url value="/movie/rate/${movie.id}" var="rateUrl"/>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="../../parts/header.jsp"/>
        <div class="container mt-5">
            <c:if test="${not empty msg_code}">
                <div class="alert alert-${css} alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <strong><spring:message code="${msg_code}"/></strong>
                </div>
            </c:if>

            <h2>${movie.title}</h2>
            <div class="row">
                <div class="col-sm-10">
                    <div class="row">
                        <label class="col-sm-2"><spring:message code="movie.description"/></label>
                        <div class="col-sm-10">${movie.description}</div>
                    </div>

                    <div class="row">
                        <label class="col-sm-2"><spring:message code="movie.year"/></label>
                        <div class="col-sm-10">
                            <div>
                                <spring:url value="/movie/year/${movie.year}" var="yearUrl"/>
                                <a href="${yearUrl}">${movie.year}</a>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <label class="col-sm-2"><spring:message code="genre.form"/></label>
                        <div class="col-sm-10">
                            <c:forEach var="genre" items="${movie.genre}" varStatus="loop">
                                <spring:url value="/movie/genre/${genre.id}" var="genreUrl"/>
                                <a href="${genreUrl}">${genre.name}</a>
                                <c:if test="${not loop.last}">,</c:if>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="row">
                        <label class="col-sm-2"><spring:message code="movie.time"/></label>
                        <div class="col-sm-10">${movie.timeLength} <spring:message code="movie.time.minutes"/></div>
                    </div>

                    <div class="row">
                        <label class="col-sm-2"><spring:message code="movie.rate.all"/></label>
                        <div class="col-sm-10">
                            <c:choose>
                                <c:when test="${movie.rateCount == 0}">
                                    <spring:message code="movie.rate.no"/>
                                </c:when>
                                <c:otherwise>
                                    ${movie.rate} (${movie.rateCount})
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <c:if test="${not empty user_rate_value}">
                        <div class="row">
                            <label class="font-weight-bold text-success"><spring:message
                                    code="movie.rate.me"/>: ${user_rate_value}</label>
                        </div>
                    </c:if>

                    <c:if test="${not empty rate_user}">
                        <div>
                            <form class="form" name="loginForm" method="POST" action="${rateUrl}">
                                <div class="form-group">
                                    <label class="form-control-label"><spring:message code="movie.rate"/></label>
                                    <select name="rate">
                                        <c:forEach items="${rates}" var="rate">
                                            <option value="${rate}">${rate}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div>
                                    <button class="btn-success btn" type="submit"><spring:message
                                            code="action.add"/></button>
                                </div>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
            <div>
                <c:choose>
                    <c:when test="${sessions.size() > 0}">
                        <h4><spring:message code="session.form3"/></h4>
                        <table class="table table-hover">
                            <thead class="thead-blue">
                            <tr>
                                <th><spring:message code="session.time"/></th>
                                <th><spring:message code="session.price"/></th>
                                <th><spring:message code="hall.form"/></th>
                                <th><spring:message code="session.sold"/></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${sessions}" var="session">
                                <tr>
                                    <td>${session.dateFormatText}</td>
                                    <td>${session.price}</td>
                                    <td>
                                        <spring:url value="/movie/hall/${session.hall.id}" var="hallUrl"/>
                                        <a class="hyperlink" href="${hallUrl}">${session.hall.name}</a>
                                    </td>
                                    <td>${session.ticketsSold}/${session.hall.capacity}</td>
                                    <security:authorize access="isAuthenticated()">
                                        <security:authorize access="hasRole('ADMIN')">
                                            <td>
                                                <spring:url value="/movie/session/update_session/${session.id}"
                                                            var="updateUrl"/>
                                                <spring:url value="/movie/session/delete_session/${session.id}"
                                                            var="deleteUrl"/>

                                                <button class="btn btn-outline-primary"
                                                        onclick="location.href='${updateUrl}'">
                                                    <spring:message code="action.update"/>
                                                </button>
                                                <button class="btn btn-outline-danger"
                                                        onclick="this.disabled=true;post('${deleteUrl}')">
                                                    <spring:message code="action.delete"/>
                                                </button>
                                            </td>
                                        </security:authorize>
                                        <security:authorize access="hasRole('USER')">
                                            <td>
                                                <spring:url value="/ticket/${session.id}/buy" var="buyTicketUrl"/>
                                                <button class="btn btn-outline-primary"
                                                        onclick="location.href=('${buyTicketUrl}')">
                                                    <spring:message code="action.buy"/> <spring:message
                                                        code="ticket.form2"/>
                                                </button>
                                            </td>
                                        </security:authorize>
                                    </security:authorize>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <label class="text-info"><spring:message code="text.not.found.session"/></label>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <jsp:include page="../../parts/footer.jsp"/>
</div>
</body>
</html>
