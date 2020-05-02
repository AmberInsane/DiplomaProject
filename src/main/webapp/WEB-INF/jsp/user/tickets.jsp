<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 27.04.2020
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Tickets</title>
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
                    <strong>${msg}</strong>
                </div>
            </c:if>
            <h2><spring:message code="user.action.tickets.my"/></h2>
            <div>
                <label class="text-info"><spring:message code="user.action.tickets.valid"/></label>
                <div>
                    <table class="table table-hover">
                        <thead class="thead-green">
                        <tr>
                            <th colspan="3"><spring:message code="user.action.tickets.me"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${tickets.size() > 0}">
                                <c:forEach var="ticket" items="${tickets}">
                                    <tr>
                                        <td>${ticket.session.dateFormatText}</td>
                                        <td>
                                            <spring:url value="/movie/${ticket.session.movie.id}" var="movieUrl"/>
                                            <a href="${movieUrl}">${ticket.session.movie.title}</a>
                                        </td>
                                        <td>
                                            <spring:url value="/ticket/return/${ticket.id}" var="returnTicketUrl"/>
                                            <button class="btn btn-outline-danger pull-right"
                                                    onclick="location.href=('${returnTicketUrl}')">
                                                <spring:message code="action.return"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="3"><spring:message code="text.not.found"/></td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>

                    <table class="table table-hover">
                        <thead class="thead-green">
                        <tr>
                            <th colspan="4"><spring:message code="user.action.tickets.friends"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${ticketsFriends.size() > 0}">
                                <c:forEach var="ticket" items="${ticketsFriends}">
                                    <tr>
                                        <td>${ticket.session.dateFormatText}</td>
                                        <td>
                                            <spring:url value="/movie/${ticket.session.movie.id}" var="movieUrl"/>
                                            <a href="${movieUrl}">${ticket.session.movie.title}</a>
                                        </td>
                                        <td>
                                            <spring:url value="/user/${ticket.userBy.id}" var="userUrl"/>
                                            <a href="${userUrl}">${ticket.userBy.username}</a>
                                        </td>
                                        <td>
                                            <spring:url value="/ticket/return/${ticket.id}" var="returnTicketUrl"/>
                                            <button class="btn btn-outline-danger pull-right"
                                                    onclick="location.href=('${returnTicketUrl}')">
                                                <spring:message code="action.return"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="4"><spring:message code="text.not.found"/></td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
            <div>
                <label class="text-info" class="thead-green"><spring:message code="user.action.tickets.invalid"/></label>
                <div>
                    <table class="table table-hover">
                        <thead class="thead-red">
                        <tr>
                            <th colspan="4"><spring:message code="user.action.tickets.me"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${ticketsOld.size() > 0}">
                                <c:forEach var="ticket" items="${ticketsOld}">
                                    <tr>
                                        <td>${ticket.session.dateFormatText}</td>
                                        <td>
                                            <spring:url value="/movie/${ticket.session.movie.id}" var="movieUrl"/>
                                            <a href="${movieUrl}">${ticket.session.movie.title}</a>
                                        </td>
                                        <td></td>
                                        <td>
                                            <spring:url value="/ticket/rate_movie/${ticket.id}" var="rateMovieUrl"/>
                                            <button class="btn btn-outline-info pull-right" onclick="location.href=('${rateMovieUrl}')">
                                                <spring:message code="user.action.movie.rate"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr colspan="4">
                                    <td><spring:message code="text.not.found"/></td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                    <table class="table table-hover">
                        <thead class="thead-red">
                        <tr>
                            <th colspan="4"><spring:message code="user.action.tickets.friends"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:choose>
                            <c:when test="${ticketsFriendsOld.size() > 0}">
                                <c:forEach var="ticket" items="${ticketsFriendsOld}">
                                    <tr>
                                        <td>${ticket.session.dateFormatText}</td>
                                        <td>
                                            <spring:url value="/movie/${ticket.session.movie.id}" var="movieUrl"/>
                                            <a href="${movieUrl}">${ticket.session.movie.title}</a>
                                        </td>
                                        <td>
                                            <spring:url value="/user/${ticket.userBy.id}" var="userUrl"/>
                                            <a href="${userUrl}">${ticket.userBy.username}</a>
                                        </td>
                                        <td>
                                            <spring:url value="/ticket/rate_movie/${ticket.id}" var="rateMovieUrl"/>
                                            <button class="btn btn-outline-info pull-right" onclick="location.href=('${rateMovieUrl}')">
                                                <spring:message code="user.action.movie.rate"/>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td><spring:message code="text.not.found"/></td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
