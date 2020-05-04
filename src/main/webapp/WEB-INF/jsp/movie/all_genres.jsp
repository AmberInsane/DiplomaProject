<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 15.04.2020
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title><spring:message code="genre.form3"/></title>
    <spring:url value="/admin/genre/add" var="addGenreUrl"/>
    <spring:url value="/resources/core/css/index.css" var="indexCss"/>

    <link href="${indexCss}" rel="stylesheet"/>
</head>
<body>
<div class="wrapper">
    <div class="wrapper-inner">
        <jsp:include page="../parts/header.jsp"/>
        <div class="container mt-5">
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ADMIN')">
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
                    <h2><spring:message code="genre.form3"/></h2>
                    <div class="btn-link">
                        <a href="${addGenreUrl}"><spring:message
                                code="action.add"/> <spring:message code="genre.form1"/></a>
                    </div>
                    <table class="table table-hover">
                        <tr>
                            <th><spring:message code="text.name"/></th>
                            <th></th>
                        </tr>
                        <c:forEach items="${genres}" var="genre">
                            <tr>
                                <td>${genre.name}</td>
                                <td>
                                    <spring:url value="/admin/genre/update/${genre.id}" var="updateUrl"/>
                                    <spring:url value="/admin/genre/delete/${genre.id}" var="deleteUrl"/>

                                    <button class="btn btn-outline-primary" onclick="location.href='${updateUrl}'">
                                        <spring:message code="action.update"/></button>
                                    <button class="btn btn-outline-danger" onclick="this.disabled=true;post('${deleteUrl}')">
                                        <spring:message code="action.delete"/></button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </security:authorize>
            </security:authorize>
        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
