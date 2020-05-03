<%--
  Created by IntelliJ IDEA.
  User: stankevich_m
  Date: 16.04.2020
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title><spring:message code="session.form3"/></title>
    <spring:url value="/admin/hall" var="hallUrl"/>
    <spring:url value="/admin/session/add" var="addSessionUrl"/>
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
            <h2><spring:message code="session.form3"/></h2>
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN')">
                    <div class="btn-link">
                        <a href="${addSessionUrl}"><spring:message code="action.add"/>
                            <spring:message code="session.form4"/></a>
                    </div>
                    <div class="btn-link">
                        <a href="${hallUrl}"><spring:message
                                code="action.manage"/> <spring:message code="hall.form4"/></a>
                    </div>
                </security:authorize>
            </security:authorize>
            <table class="table table-hover">
                <tr>
                    <th><spring:message code="session.time"/></th>
                    <th><spring:message code="movie.time"/></th>
                    <th><spring:message code="movie.form"/></th>
                    <th><spring:message code="session.price"/></th>
                    <th><spring:message code="hall.form"/></th>
                    <th><spring:message code="session.sold"/></th>
                    <th></th>
                </tr>
                <c:forEach items="${sessions}" var="session">
                    <tr>
                        <td>${session.dateFormatText}</td>
                        <td>${session.movie.timeLength} <spring:message code="movie.time.minutes"/></td>
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
                        <td>${session.ticketsSold}/${session.hall.capacity}</td>
                        <security:authorize access="isAuthenticated()">
                            <security:authorize access="hasRole('ADMIN')">
                                <td>
                                    <spring:url value="/admin/session/update/${session.id}" var="updateUrl"/>
                                    <spring:url value="/admin/session/delete/${session.id}" var="deleteUrl"/>

                                    <button class="btn btn-outline-primary" onclick="location.href='${updateUrl}'">
                                        <spring:message
                                                code="action.update"/></button>
                                    <button class="btn btn-outline-danger"
                                            onclick="this.disabled=true;post('${deleteUrl}')">
                                        <spring:message code="action.delete"/>
                                    </button>
                                </td>
                            </security:authorize>
                            <security:authorize access="hasRole('USER')">
                                <td>
                                    <c:choose>
                                        <c:when test="${session.ticketsSold.compareTo(session.hall.capacity.intValue()) == 0}">
                                            <label class="text-danger"><spring:message code="text.sold.out"/></label>
                                        </c:when>
                                        <c:otherwise>
                                            <spring:url value="/ticket/${session.id}/buy" var="buyTicketUrl"/>
                                            <button class="btn btn-outline-primary"
                                                    onclick="location.href=('${buyTicketUrl}')">
                                                <spring:message code="action.buy"/> <spring:message
                                                    code="ticket.form2"/>
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </security:authorize>
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
