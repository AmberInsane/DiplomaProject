<%--
  Created by IntelliJ IDEA.
  User: Пользователь
  Date: 22.04.2020
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>UserInfo</title>
    <security:authentication var="curUser" property="principal"/>
    <spring:url value="/user/friend/${user.id}/send" var="sendRequestUrl"/>
    <spring:url value="/user/friend/${user.id}/black" var="sendToBlackListUrl"/>
    <spring:url value="/user/my_tickets" var="myTicketsUrl"/>
    <spring:url value="/user/my_friends" var="myFriendsUrl"/>
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

    <h3>
        <c:choose>
            <c:when test="${user.equals(curUser)}">
                Your Details
            </c:when>
            <c:otherwise>
                ${user.username} Details
            </c:otherwise>
        </c:choose>
    </h3>


    <div class="row">
        <label class="col-sm-2">Name</label>
        <div class="col-sm-10">${user.username}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Email</label>
        <div class="col-sm-10">${user.email}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Birthday</label>
        <div class="col-sm-10">${user.birthday}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Phone</label>
        <div class="col-sm-10">${user.phone}</div>
    </div>

    <security:authorize access="isAuthenticated()">
        <c:choose>
            <c:when test="${user.equals(curUser)}">
                <div>
                    <button class="btn btn-primary" onclick="${myTicketsUrl}">Show my tickets</button>
                </div>
                <div>
                    <button class="btn btn-primary" onclick="${myTicketsUrl}">Show my friends</button>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <button class="btn btn-primary" onclick="this.disabled=true;post('${sendRequestUrl}')">Send Friend
                        Request
                    </button>
                </div>
                <div>
                    <button class="btn btn-danger" onclick="this.disabled=true;post('${sendToBlackListUrl}')">Send to
                        black list
                    </button>
                </div>
            </c:otherwise>
        </c:choose>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
