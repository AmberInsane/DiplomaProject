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
    <spring:url value="/user/friend/${user.id}/block" var="blockUrl"/>
    <spring:url value="/user/friend/${user.id}/unblock" var="unblockUrl"/>
    <spring:url value="/user/friend/${user.id}/delete" var="deleteUrl"/>

    <spring:url value="/user/request/accept/${nowResponse.id}" var="acceptUrl"/>
    <spring:url value="/user/request/deny/${nowResponse.id}" var="denyUrl"/>
    <spring:url value="/user/request/cancel/${nowRequest.id}" var="cancelUrl"/>


    <spring:url value="/user/my_tickets" var="myTicketsUrl"/>
    <spring:url value="/user/my_friends" var="myFriendsUrl"/>
    <spring:url value="/user/my_edit" var="editUrl"/>
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
        <label class="col-sm-2">About user</label>
        <div class="col-sm-10">${user.info.aboutUser}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Favorite Genres</label>
        <div class="col-sm-10">${user.info.favoriteGenres}</div>
    </div>

    <div class="row">
        <label class="col-sm-2">Favorite Movies</label>
        <div class="col-sm-10">${user.info.favoriteMovies}</div>
    </div>

    <security:authorize access="isAuthenticated()">
        <c:choose>
            <c:when test="${user.equals(curUser)}">
                <div>
                    <button class="btn btn-primary" onclick="location.href='${editUrl}'">Edit my info</button>
                </div>
                <div>
                    <button class="btn btn-primary" onclick="location.href='${myTicketsUrl}'">Show my tickets</button>
                </div>
                <div>
                    <button class="btn btn-primary" onclick="location.href='${myFriendsUrl}'">Show my friends</button>
                    <c:if test="${requestsNum > 0}">
                        <div class="text-info">
                            (+ ${requestsNum})
                        </div>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <c:choose>
                        <c:when test="${not empty nowResponse}">
                            <button class="btn btn-primary" onclick="this.disabled=true;post('${acceptUrl}')">
                                Принять
                            </button>
                            <button class="btn btn-warning" onclick="this.disabled=true;post('${denyUrl}')">Отклонить
                            </button>
                        </c:when>
                        <c:when test="${not empty nowRequest}">
                            <button class="btn btn-warning" onclick="this.disabled=true;post('${cancelUrl}')">
                                Отменить запрос
                            </button>
                        </c:when>
                        <c:when test="${not empty isFriend}">
                            <button class="btn btn-warning" onclick="this.disabled=true;post('${deleteUrl}')">
                                Удалить из друзей
                            </button>
                        </c:when>
                        <c:otherwise>
                            <c:when test="${!youBlocked}">
                                <button class="btn btn-primary" onclick="this.disabled=true;post('${sendRequestUrl}')">
                                    Send
                                    Friend Request
                                </button>
                            </c:when>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${blocked}">
                            <button class="btn btn-primary" onclick="this.disabled=true;post('${unblockUrl}')">Unblock
                                User
                            </button>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-danger" onclick="this.disabled=true;post('${blockUrl}')">Block User
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:otherwise>
        </c:choose>
    </security:authorize>
</div>
<jsp:include page="../parts/footer.jsp"/>
</body>
</html>
