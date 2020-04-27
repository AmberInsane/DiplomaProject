<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 27.04.2020
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Tickets</title>
</head>
<body>
<jsp:include page="../parts/header.jsp"/>
<div class="container">
    <c:if test="${not empty msg}">
        <div class="alert alert-${css} alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <h2>My tickets</h2>

    <div>
        <label class="text-primary">My current tickets</label>
        <div>
            <table class="table table-striped">
                <tr>
                    <th>Bought by me</th>
                </tr>
                <c:choose>
                    <c:when test="${tickets.size() > 0}">
                        <c:forEach var="ticket" items="${tickets}">
                            <tr>
                                <td>${ticket.session.dateFormatText}</td>
                                <td>
                                    <spring:url value="/movie/${ticket.session.movie.id}" var="movieUrl"/>
                                    <a href="${movieUrl}">${ticket.session.movie.title}</a>
                                </td>
                                <td></td>
                                <td>
                                    <spring:url value="/ticket/return/${ticket.id}" var="returnTicketUrl"/>
                                    <button class="btn btn-danger" onclick="location.href=('${returnTicketUrl}')">
                                        Return ticket
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>Not found</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <th>Bought by friends</th>
                </tr>
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
                                    <button class="btn btn-danger" onclick="location.href=('${returnTicketUrl}')">
                                        Return ticket
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>Not found</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
    <div>
        <label class="text-primary">My old tickets</label>
        <div>
            <table class="table table-striped">
                <tr>
                    <th>Bought by me</th>
                </tr>
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
                                    <button class="btn btn-warning" onclick="location.href=('${rateMovieUrl}')">
                                        Rate movie
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>Not found</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                <tr>
                    <th>Bought by friends</th>
                </tr>
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
                                    <button class="btn btn-warning" onclick="location.href=('${rateMovieUrl}')">
                                        Rate movie
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td>Not found</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </div>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
