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
            <h2><spring:message code="sessions.today"/></h2>
            <h4>${today}</h4>
            <br>
            <c:forEach items="${movies}" var="entry">
                <h5>${entry.key.title}</h5>
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
                    <c:forEach items="${entry.value}" var="session">
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
            </c:forEach>


        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
