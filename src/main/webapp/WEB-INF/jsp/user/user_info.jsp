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
    <title>${user.username}</title>
    <security:authentication var="curUser" property="principal"/>
    <spring:url value="/user/friend/${user.id}/send" var="sendRequestUrl"/>
    <spring:url value="/user/friend/${user.id}/block" var="blockUrl"/>
    <spring:url value="/user/friend/${user.id}/unblock" var="unblockUrl"/>
    <spring:url value="/user/friend/${user.id}/delete" var="deleteUrl"/>

    <spring:url value="/user/request/accept/${nowResponse.id}" var="acceptUrl"/>
    <spring:url value="/user/request/refuse/${nowResponse.id}" var="refuseUrl"/>
    <spring:url value="/user/request/cancel/${nowRequest.id}" var="cancelUrl"/>

    <spring:url value="/user/my_tickets" var="myTicketsUrl"/>
    <spring:url value="/user/my_friends" var="myFriendsUrl"/>
    <spring:url value="/user/my_edit" var="editUrl"/>
    <spring:url value="/user/my_purse" var="purseUrl"/>

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
                    <c:if test="${not empty cause_code}">
                        <spring:message code="${cause_code}"/>
                    </c:if>
                </div>
            </c:if>
            <div class="row">
                <div class="user-info col-sm-8">
                    <h3>${user.username}</h3>

                    <div class="row">
                        <label class="col-sm-4"><spring:message code="user.email"/></label>
                        <div class="col-sm-8">${user.email}</div>
                    </div>

                    <div class="row">
                        <label class="col-sm-4"><spring:message code="user.info.about"/></label>
                        <div class="col-sm-8">${user.info.aboutUser}</div>
                    </div>

                    <div class="row">
                        <label class="col-sm-4"><spring:message code="user.info.genres"/></label>
                        <div class="col-sm-8">${user.info.favoriteGenres}</div>
                    </div>

                    <div class="row">
                        <label class="col-sm-4"><spring:message code="user.info.movies"/></label>
                        <div class="col-sm-8">${user.info.favoriteMovies}</div>
                    </div>

                    <c:if test="${user.equals(curUser)}">
                        <div class="row">
                            <label class="col-sm-2"><spring:message code="user.balance"/></label>
                            <div class="col-sm-2">${user.balance}</div>
                            <button class="btn btn-outline-info" onclick="location.href='${purseUrl}'"><spring:message
                                    code="user.action.balance"/></button>
                        </div>
                    </c:if>
                </div>
                <div class="buttons-column col-sm-4">
                    <security:authorize access="isAuthenticated()">
                        <c:choose>
                            <c:when test="${user.equals(curUser)}">
                                <button class="btn btn-info" onclick="location.href='${editUrl}'"><spring:message
                                        code="user.action.edit"/></button>
                                <button class="btn btn-info" onclick="location.href='${myTicketsUrl}'">
                                    <spring:message
                                            code="user.action.tickets.my"/></button>
                                <button class="btn btn-info" onclick="location.href='${myFriendsUrl}'">
                                    <spring:message
                                            code="user.action.friends.my"/>
                                    <c:if test="${requestsNum > 0}">
                                        <span>
                                            (+ ${requestsNum})
                                        </span>
                                    </c:if>
                                </button>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${not empty nowResponse}">
                                        <button class="btn btn-outline-success"
                                                onclick="this.disabled=true;post('${acceptUrl}')">
                                            <spring:message code="user.action.friends.accept"/>
                                        </button>
                                        <button class="btn btn-outline-danger"
                                                onclick="this.disabled=true;post('${refuseUrl}')">
                                            <spring:message code="user.action.friends.refuse"/>
                                        </button>
                                    </c:when>
                                    <c:when test="${not empty nowRequest}">
                                        <button class="btn btn-outline-warning"
                                                onclick="this.disabled=true;post('${cancelUrl}')">
                                            <spring:message code="user.action.friends.cancel"/>
                                        </button>
                                    </c:when>
                                    <c:when test="${isFriend}">
                                        <button class="btn btn-outline-danger"
                                                onclick="this.disabled=true;post('${deleteUrl}')">
                                            <spring:message code="user.action.friends.delete"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${!youBlocked && !blocked}">
                                            <button class="btn btn-outline-info"
                                                    onclick="this.disabled=true;post('${sendRequestUrl}')">
                                                <spring:message code="user.action.friends.request"/>
                                            </button>
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${blocked}">
                                        <button class="btn btn-outline-danger"
                                                onclick="this.disabled=true;post('${unblockUrl}')">
                                            <spring:message code="user.action.friends.unblock"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-outline-secondary"
                                                onclick="this.disabled=true;post('${blockUrl}')">
                                            <spring:message code="user.action.friends.block"/>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </security:authorize>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="../parts/footer.jsp"/>
</div>
</body>
</html>
